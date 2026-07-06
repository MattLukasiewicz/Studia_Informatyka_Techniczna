import subprocess
import sys
import time


def otworz_okno_odbierania():

    print("Otwieram okno ODBIERANIA ")
    try:
        subprocess.Popen(["fsquirt", "-receive"])
        print("Gotowe. Spójrz na nowe okno.")
    except FileNotFoundError:
        print("Błąd: Nie znaleziono systemowego pliku fsquirt.exe")


def otworz_okno_wysylania():

    print("Otwieram kreator WYSYŁANIA.")
    try:
        subprocess.Popen(["fsquirt"])
        print("Gotowe. Wybierz 'Wyślij plik' w oknie, które się pojawiło.")
    except FileNotFoundError:
        print("Błąd: Nie znaleziono systemowego pliku fsquirt.exe")


def menu():
    print("===TRANSFER BLUETOOTH ===")
    print("----------------------------------------------")
    print("1. [ODBIORCA] Otwórz okno 'Oczekiwanie na połączenie'")
    print("2. [NADAWCA]  Otwórz kreator wysyłania")
    print("3. Zamknij")

    wybor = input("\nWybierz tryb (1 lub 2): ")

    if wybor == '1':
        otworz_okno_odbierania()
    elif wybor == '2':
        otworz_okno_wysylania()
    elif wybor == '3':
        sys.exit()
    else:
        print("Niepoprawny wybór.")


    time.sleep(2)


if __name__ == "__main__":
    menu()