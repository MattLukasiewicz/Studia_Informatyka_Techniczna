import time

import datetime


def Czy_dobry_format_danych(czasik):

    while True:
        try:
            czas = input(czasik)
            czy_dobry_czas = datetime.strptime(czas, "%H:%M:%S").time()#funkcja chce zamienic ciag znakow na obiekt czasu jak git to zrwaca ten format """
            return czy_dobry_czas
        except ValueError:
            print("Zly format wporwadz godzine w formacie HH:MM:SS")


def minutnik():

    while True:
        try:
            minuty = int(input("Podaj liczbę minut: "))
            sekundy = int(input("Podaj liczbę sekund: "))
            if sekundy >= 60:
                raise ValueError("Maks 59 sekund")
            if minuty < 0 or sekundy < 0:
                raise ValueError("Czas nie moze być ujemny ")
            ile_sekund = minuty * 60 + sekundy
            break
        except ValueError as e:
            print(f"Błąd: {e}. Spróbuj ponownie.")

    print("Minutnik uruchomiony!")
    while ile_sekund > 0:
        mins, secs = divmod(ile_sekund, 60)
        print(f"{mins:02d}:{secs:02d}")
        time.sleep(1)
        ile_sekund -= 1

    print("Alarm")


def zegar_tradycyjny(h, m, s):

    try:
        while True:

            aktualny_czas = datetime.time(h, m, s)


            print(aktualny_czas.strftime("%H:%M:%S"), end="\r", flush=True)

            czas = datetime.datetime(100, 1, 1, h, m, s) + datetime.timedelta(seconds=1)
            h, m, s = czas.hour, czas.minute, czas.second
            #print(f"{h:02d}:{m:02d}:{s:02d}")
    except KeyboardInterrupt:
        print("\nZegar zatrzymany.")


if __name__ == "__main__":
    print("Wybierz tryb działania:")
    print("1.Zegar tradycyjny")
    print("2.Minutnik")

    while True:
        wyborek = input("Wybierz madrze (1/2): ")
        if wyborek == '1':
            h = int(input("Podaj godziny (0-23): "))
            m = int(input("Podaj minuty (0-59): "))
            s = int(input("Podaj sekundy (0-59): "))
            if h < 0 or h > 23 or m < 0 or m > 59 or s < 0 or s > 59:
                print("Wprowadzono złe dane.")
            else:
                zegar_tradycyjny(h, m, s)
            break
        elif wyborek == '2':
            minutnik()
            break
        else:
            print("Nieprawidlowy wybor. Wybierz 1(zegar tradycyjny) lub 2(tryb minutnik)")
