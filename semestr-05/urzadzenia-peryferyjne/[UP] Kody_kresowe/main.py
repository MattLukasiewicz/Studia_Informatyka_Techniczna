import customtkinter
from barcode import EAN13
from barcode.writer import ImageWriter, SVGWriter
from PIL import Image


class EANCodeGenerator:

    def __init__(self):
        self.scan_button = None
        self.create_button = None
        self.app = self.create_window()
        self.base_code = None
        self.file_path = None
        self.create_frame = None
        self.scan_result_textbox = None
        self.scan_label = None
        self.scan_window = None
        self.image_label = None
        self.image = None
        self.create_window = None

        self.app.mainloop()


    def create_window(self):
        app = customtkinter.CTk()
        app.geometry('600x200')
        self.create_button = customtkinter.CTkButton(app, text="Create EAN code", command=lambda: self.get_input())
        self.scan_button = customtkinter.CTkButton(app, text="Scan EAN code", command=lambda: self.scan_code())
        self.create_button.place(relx=0.3, rely=0.5, anchor="center")
        self.scan_button.place(relx=0.7, rely=0.5, anchor="center")
        return app

    def scan_finished(self, event):
        full_code = self.scan_result_textbox.get()
        self.scan_result_textbox.delete(0, "end")
        file_path = self.generate_ean13(full_code)
        self.show_image(file_path)

    def scan_code(self):
        self.scan_window = customtkinter.CTkToplevel(self.app)
        self.scan_window.title('Scan your EAN code')
        self.scan_window.geometry('800x400')
        self.scan_window.grab_set()
        self.scan_window.lift()
        self.scan_window.focus()
        self.scan_label = customtkinter.CTkLabel(self.scan_window, text="Scan your EAN code now", font=("Arial", 25))
        self.scan_label.place(relx=0.5, rely=0.1, anchor="center")
        self.scan_result_textbox = customtkinter.CTkEntry(self.scan_window, width=200, height=30, placeholder_text="Waiting for scan...")
        self.scan_result_textbox.place(relx=0.5, rely=0.4, anchor="center")
        self.scan_window.after(200, lambda: self.scan_result_textbox.focus())
        self.scan_result_textbox.bind("<Return>", self.scan_finished)

    def show_image(self, path):
        self.create_window = customtkinter.CTkToplevel(self.app)
        self.create_window.title('Generated EAN code')
        self.create_window.geometry('800x400')
        self.create_window.grab_set()
        self.create_window.lift()
        self.create_window.focus()
        self.image = customtkinter.CTkImage(dark_image = Image.open(path), size=(700, 350))
        self.image_label = customtkinter.CTkLabel(self.create_window, text="", image=self.image)
        self.image_label.pack(padx=20, pady=20)

    def get_input(self):
        dialog = customtkinter.CTkInputDialog(text="Podaj 12 cyfr kodu:", title="Podaj kod")
        self.base_code = dialog.get_input()
        self.file_path = self.generate_ean13(self.base_code)
        self.show_image(self.file_path)

    def calculate_control_digit(self, base_code):
        sum_number = 0

        for i, digit in enumerate(base_code):
            number = int(digit)

            if i % 2 == 0:
                weight = 1
            else:
                weight = 3
            sum_number += number * weight

        remainder = sum_number % 10

        if remainder == 0:
            control_digit = 0
        else:
            control_digit = 10 - remainder

        return str(control_digit)


    def generate_ean13(self, base_code):
        if len(base_code) == 13:
            base_code = base_code[:12]

        if len(base_code) != 12 or not base_code.isdigit():
            return

        control_digit = self.calculate_control_digit(base_code)
        full_code = base_code + control_digit

        path = None
        try:
            file_name = f"ean13_{full_code}"
            path = EAN13(full_code, writer=ImageWriter()).save(file_name)
            EAN13(full_code, writer=SVGWriter()).save(file_name)

        except Exception as e:
            print(f"Błąd podczas zapisu pliku: {e}")

        return path


if __name__ == "__main__":
    try:
        ean_generator = EANCodeGenerator()
    except Exception as e:
        print(f"Krytyczny błąd: {e}")