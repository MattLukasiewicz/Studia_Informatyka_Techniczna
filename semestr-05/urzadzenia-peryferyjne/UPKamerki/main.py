import cv2
import tkinter as tk
from tkinter import ttk, messagebox, Toplevel, Scale, HORIZONTAL
from PIL import Image, ImageTk
import datetime
import os
import numpy as np  # Potrzebne do zaawansowanej matematyki

# Sprawdzenie dostępności biblioteki do nazw kamer
try:
    from pygrabber.dshow_graph import FilterGraph

    PYGRABBER_AVAILABLE = True
except ImportError:
    PYGRABBER_AVAILABLE = False


class CameraApp:
    def __init__(self, window, window_title):
        self.window = window
        self.window.title(window_title)

        # --- Zmienne stanu ---
        self.cap = None
        self.is_recording = False
        self.out = None
        self.motion_detection_enabled = False

        # Zmienne do algorytmów ruchu
        self.prev_frame = None  # Do metody 1 (Prostej)
        self.avg_frame = None  # Do metody 2 (Zaawansowanej)

        if not os.path.exists("galeria"):
            os.makedirs("galeria")

        # --- Interfejs Graficzny ---

        # 1. Panel sterowania (GÓRA)
        self.control_frame = tk.Frame(window)
        self.control_frame.pack(side=tk.TOP, fill=tk.X, padx=5, pady=5)

        tk.Label(self.control_frame, text="Kamera:").pack(side=tk.LEFT)
        self.camera_names = self.get_camera_names()
        self.camera_selector = ttk.Combobox(self.control_frame, values=self.camera_names, width=25)
        if self.camera_names: self.camera_selector.current(0)
        self.camera_selector.pack(side=tk.LEFT, padx=5)

        self.btn_connect = tk.Button(self.control_frame, text="Połącz", command=self.connect_camera)
        self.btn_connect.pack(side=tk.LEFT)

        tk.Label(self.control_frame, text="Rozdz:").pack(side=tk.LEFT, padx=(10, 0))
        self.res_selector = ttk.Combobox(self.control_frame, values=["640x480", "1280x720"], width=10)
        self.res_selector.current(0)
        self.res_selector.pack(side=tk.LEFT, padx=5)
        self.btn_set_res = tk.Button(self.control_frame, text="Ustaw", command=self.set_resolution)
        self.btn_set_res.pack(side=tk.LEFT)

        # Formaty plików
        tk.Label(self.control_frame, text="Foto:").pack(side=tk.LEFT, padx=(10, 0))
        self.photo_format = ttk.Combobox(self.control_frame, values=[".jpg", ".png"], width=5)
        self.photo_format.current(0)
        self.photo_format.pack(side=tk.LEFT)

        tk.Label(self.control_frame, text="Video:").pack(side=tk.LEFT, padx=(5, 0))
        self.video_format = ttk.Combobox(self.control_frame, values=[".avi", ".mp4"], width=5)
        self.video_format.current(0)
        self.video_format.pack(side=tk.LEFT)

        # 2. Canvas (ŚRODEK)
        self.canvas_frame = tk.Frame(window)
        self.canvas_frame.pack()
        self.canvas = tk.Canvas(self.canvas_frame, width=640, height=480, bg="black")
        self.canvas.pack()

        # 3. Panel akcji (DÓŁ)
        self.action_frame = tk.Frame(window)
        self.action_frame.pack(side=tk.BOTTOM, fill=tk.X, padx=5, pady=5)

        self.btn_snapshot = tk.Button(self.action_frame, text="Zrób Zdjęcie", command=self.take_snapshot,
                                      state=tk.DISABLED, bg="#dddddd")
        self.btn_snapshot.pack(side=tk.LEFT, expand=True, fill=tk.X)

        self.btn_record = tk.Button(self.action_frame, text="Nagraj Wideo", command=self.toggle_recording,
                                    state=tk.DISABLED, bg="#dddddd")
        self.btn_record.pack(side=tk.LEFT, expand=True, fill=tk.X)

        # --- SEKCJA WYKRYWANIA RUCHU ---
        # Wybór metody
        tk.Label(self.action_frame, text="Metoda:").pack(side=tk.LEFT, padx=(10, 2))
        self.motion_method_selector = ttk.Combobox(self.action_frame,
                                                   values=["Różnica Klatek (Prosta)", "Średnia Ważona (Adaptacyjna)"],
                                                   width=25, state="readonly")
        self.motion_method_selector.current(0)  # Domyślnie prosta
        self.motion_method_selector.pack(side=tk.LEFT, padx=2)

        self.btn_motion = tk.Button(self.action_frame, text="Start Detekcji", command=self.toggle_motion,
                                    state=tk.DISABLED, bg="#ffcccc")
        self.btn_motion.pack(side=tk.LEFT, expand=True, fill=tk.X, padx=5)

        self.status_label = tk.Label(window, text="Status: Gotowy", bd=1, relief=tk.SUNKEN, anchor=tk.W)
        self.status_label.pack(side=tk.BOTTOM, fill=tk.X)

        self.delay = 15
        self.update()
        self.window.protocol("WM_DELETE_WINDOW", self.on_closing)

    def get_camera_names(self):
        if PYGRABBER_AVAILABLE:
            try:
                graph = FilterGraph()
                devices = graph.get_input_devices()
                return devices if devices else ["Brak kamer"]
            except:
                return ["Kamera 0 (Błąd)"]
        return ["Kamera 0", "Kamera 1"]

    def connect_camera(self):
        try:
            cam_id = self.camera_selector.current()
            cam_name = self.camera_selector.get()
            if cam_id < 0: return

            if self.cap: self.cap.release()
            self.cap = cv2.VideoCapture(cam_id, cv2.CAP_DSHOW)

            if not self.cap.isOpened():
                messagebox.showerror("Błąd", f"Nie można otworzyć: {cam_name}")
                return

            self.btn_snapshot.config(state=tk.NORMAL)
            self.btn_record.config(state=tk.NORMAL)
            self.btn_motion.config(state=tk.NORMAL)
            self.status_label.config(text=f"Połączono z: {cam_name}")
            self.set_resolution()
        except Exception as e:
            messagebox.showerror("Błąd", str(e))

    def set_resolution(self):
        if self.cap is None: return
        res = self.res_selector.get().split('x')
        self.cap.set(cv2.CAP_PROP_FRAME_WIDTH, int(res[0]))
        self.cap.set(cv2.CAP_PROP_FRAME_HEIGHT, int(res[1]))

    def take_snapshot(self):
        if self.cap and self.cap.isOpened():
            ret, frame = self.cap.read()
            if ret:
                ts = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
                ext = self.photo_format.get()
                filename = f"galeria/zdjecie_{ts}{ext}"
                cv2.imwrite(filename, frame)
                self.status_label.config(text=f"Zapisano: {filename}")
                if messagebox.askyesno("Edycja", "Zdjęcie zapisane. Edytować?"):
                    self.open_editor(filename)

    def open_editor(self, filepath):
        editor = Toplevel(self.window)
        editor.title("Edytor Parametrów")
        original_img = cv2.imread(filepath)
        if original_img is None: return
        self.edit_img = original_img.copy()

        # Interfejs
        frame_controls = tk.Frame(editor)
        frame_controls.pack(pady=10)

        # Suwaki
        Scale(frame_controls, from_=-100, to=100, orient=HORIZONTAL, label="Jasność",
              variable=tk.DoubleVar(value=0)).pack(side=tk.LEFT)
        self.br_scale = frame_controls.winfo_children()[-1]  # Uchwyt do suwaka

        Scale(frame_controls, from_=0.5, to=3.0, resolution=0.1, orient=HORIZONTAL, label="Kontrast",
              variable=tk.DoubleVar(value=1.0)).pack(side=tk.LEFT)
        self.cn_scale = frame_controls.winfo_children()[-1]

        Scale(frame_controls, from_=0.0, to=2.0, resolution=0.1, orient=HORIZONTAL, label="Nasycenie",
              variable=tk.DoubleVar(value=1.0)).pack(side=tk.LEFT)
        self.sa_scale = frame_controls.winfo_children()[-1]

        def update_preview(*args):
            b, c, s = self.br_scale.get(), self.cn_scale.get(), self.sa_scale.get()
            # 1. Jasność/Kontrast
            img_bc = cv2.convertScaleAbs(original_img, alpha=c, beta=b)
            # 2. Nasycenie
            if s != 1.0:
                hsv = cv2.cvtColor(img_bc, cv2.COLOR_BGR2HSV)
                h, sat, v = cv2.split(hsv)
                sat = cv2.multiply(sat.astype(float), s)
                sat = np.clip(sat, 0, 255).astype(np.uint8)
                img_bc = cv2.cvtColor(cv2.merge([h, sat, v]), cv2.COLOR_HSV2BGR)

            self.edit_img = img_bc
            rgb = cv2.cvtColor(self.edit_img, cv2.COLOR_BGR2RGB)
            h, w, _ = rgb.shape
            if w > 800: rgb = cv2.resize(rgb, (800, int(h * (800 / w))))
            img = ImageTk.PhotoImage(image=Image.fromarray(rgb))
            lbl.config(image=img);
            lbl.image = img

        tk.Button(frame_controls, text="Zapisz",
                  command=lambda: [cv2.imwrite(filepath, self.edit_img), editor.destroy()], bg="#90ee90").pack(
            side=tk.LEFT, padx=10)
        lbl = tk.Label(editor);
        lbl.pack()

        self.br_scale.config(command=update_preview)
        self.cn_scale.config(command=update_preview)
        self.sa_scale.config(command=update_preview)
        update_preview()

    def toggle_recording(self):
        if not self.is_recording:
            self.is_recording = True
            self.btn_record.config(text="STOP", bg="#ff5555")
            ts = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
            ext = self.video_format.get()
            filename = f"galeria/wideo_{ts}{ext}"
            w, h = int(self.cap.get(3)), int(self.cap.get(4))
            fourcc = cv2.VideoWriter_fourcc(*'mp4v') if ext == ".mp4" else cv2.VideoWriter_fourcc(*'XVID')
            self.out = cv2.VideoWriter(filename, fourcc, 20.0, (w, h))
            self.status_label.config(text=f"Nagrywanie: {filename}")
        else:
            self.is_recording = False
            self.btn_record.config(text="Nagraj Wideo", bg="#dddddd")
            if self.out: self.out.release()
            self.status_label.config(text="Koniec nagrywania")

    def toggle_motion(self):
        self.motion_detection_enabled = not self.motion_detection_enabled
        if self.motion_detection_enabled:
            self.btn_motion.config(text="STOP Detekcji", bg="#55ff55")
            # Resetujemy zmienne pomocnicze, żeby algorytm startował "na czysto"
            self.prev_frame = None
            self.avg_frame = None
        else:
            self.btn_motion.config(text="Start Detekcji", bg="#ffcccc")

    def detect_motion(self, frame):
        """Główna funkcja sterująca wyborem metody wykrywania"""
        method = self.motion_method_selector.get()

        # Wspólne przygotowanie obrazu (szarość + rozmycie)
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        gray = cv2.GaussianBlur(gray, (21, 21), 0)

        thresh = None

        # --- METODA 1: Prosta Różnica (Frame Difference) ---
        if "Różnica" in method:
            if self.prev_frame is None:
                self.prev_frame = gray
                return frame

            # Różnica: Obecna - Poprzednia
            frame_delta = cv2.absdiff(self.prev_frame, gray)
            thresh = cv2.threshold(frame_delta, 25, 255, cv2.THRESH_BINARY)[1]
            self.prev_frame = gray  # Aktualizuj poprzednią klatkę

        # --- METODA 2: Średnia Ważona (Running Average) ---
        else:
            if self.avg_frame is None:
                self.avg_frame = gray.copy().astype("float")
                return frame

            # Aktualizacja tła (uczenie się)
            cv2.accumulateWeighted(gray, self.avg_frame, 0.05)
            # Różnica: Obecna - Średnie Tło
            frame_delta = cv2.absdiff(gray, cv2.convertScaleAbs(self.avg_frame))
            thresh = cv2.threshold(frame_delta, 25, 255, cv2.THRESH_BINARY)[1]

        # --- Wspólne rysowanie konturów ---
        if thresh is not None:
            thresh = cv2.dilate(thresh, None, iterations=2)
            cnts, _ = cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

            motion_detected = False
            for c in cnts:
                if cv2.contourArea(c) < 500: continue  # Ignoruj małe
                (x, y, w, h) = cv2.boundingRect(c)
                cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)
                motion_detected = True

            if motion_detected:
                label = "RUCH (Prosty)" if "Różnica" in method else "RUCH (Adaptacyjny)"
                cv2.putText(frame, label, (10, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 255), 2)

        return frame

    def update(self):
        if self.cap and self.cap.isOpened():
            ret, frame = self.cap.read()
            if ret:
                if self.is_recording and self.out: self.out.write(frame)

                disp = frame.copy()
                # Jeśli włączone wykrywanie, uruchom funkcję wyboru metody
                if self.motion_detection_enabled:
                    disp = self.detect_motion(disp)

                rgb = cv2.cvtColor(disp, cv2.COLOR_BGR2RGB)
                img = ImageTk.PhotoImage(image=Image.fromarray(rgb))
                self.canvas.create_image(0, 0, image=img, anchor=tk.NW)
                self.canvas.image = img
        self.window.after(self.delay, self.update)

    def on_closing(self):
        if self.cap: self.cap.release()
        if self.out: self.out.release()
        self.window.destroy()


if __name__ == "__main__":
    root = tk.Tk()
    app = CameraApp(root, "Kamery Cyfrowe")
    root.mainloop()