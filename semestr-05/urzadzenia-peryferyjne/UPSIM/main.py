import sys
import math
from smartcard.System import readers
from smartcard.util import toBytes, toHexString, hexarray2string

# --- Komendy APDU (Application Protocol Data Unit) ---
# APDU jest formatem wymiany informacji, kluczowym dla komunikacji z kartą SIM[cite: 40].
# Komendy służą do nawigacji po hierarchicznej strukturze zbiorów danych[cite: 52].

# Master File (Korzeń systemu plików) [cite: 53]
SELECT_MF = "A0 A4 00 00 02 3F 00"

# Dedicated File - Telekomunikacja (Usługi GSM) [cite: 54]
SELECT_DF_TELECOM = "A0 A4 00 00 02 7F 10"

# Elementary File - SMS (Wiadomości) [cite: 55]
SELECT_EF_SMS = "A0 A4 00 00 02 6F 3C"

# Elementary File - ADN (Książka telefoniczna) [cite: 55]
SELECT_EF_ADN = "A0 A4 00 00 02 6F 3A"


# Typowa komenda GET RESPONSE (używana po SELECT, gdy SW1 = 9F) [cite: 47]
# Wymaga wstawienia długości danych (SW2) w ostatnim bajcie: A0 C0 00 00 [LE]


class SimCardManager:
    """
    Klasa zarządza komunikacją z kartą SIM przez interfejs PC/SC.
    Realizuje odczyt kontaktów, SMS-ów oraz testowanie komend APDU.
    """

    def __init__(self):
        self.connection = None  # Aktywne połączenie

    def connect_reader(self):
        """
        Wykrywa, wyświetla i łączy się z wybranym czytnikiem kart (II.2a, II.2b).
        """
        try:
            dostepne_czytniki = readers()  #

            if not dostepne_czytniki:
                print("! Nie wykryto czytnika.")
                return False

            print("\n--- Wybór Czytnika ---")
            for i, reader in enumerate(dostepne_czytniki):
                print(f"[{i}] {reader}")

            reader = None
            while True:
                try:
                    wybor = input("Wybierz numer czytnika: ").strip()
                    index = int(wybor)
                    if 0 <= index < len(dostepne_czytniki):
                        reader = dostepne_czytniki[index]
                        break
                    else:
                        print("Nieprawidłowy numer. Spróbuj ponownie.")
                except ValueError:
                    print("Proszę wprowadzić cyfrę.")

            print(f"Używany czytnik: {reader}")
            # Tworzymy połączenie i aktywujemy kartę (ATR)
            self.connection = reader.createConnection()
            self.connection.connect()
            atr = toHexString(self.connection.getATR())
            print(">> Połączono z kartą pomyślnie.")
            print(f"   ATR (Answer To Reset): {atr}")
            return True
        except Exception as e:
            print(f"!! Błąd połączenia z kartą. Upewnij się, że karta jest włożona. Szczegóły: {e}")
            return False

    def send_apdu(self, command_hex):
        """
        Wysyła komendę APDU HEX do karty i zwraca odpowiedź (data, sw1, sw2).
        Funkcja wewnętrzna, używana przez inne metody.
        """
        if self.connection is None:
            return [], 0, 0

        try:
            # Konwersja stringa HEX na listę bajtów
            apdu_bytes = toBytes(command_hex)

            # Fizyczne wysłanie do karty
            data, sw1, sw2 = self.connection.transmit(apdu_bytes)
            return data, sw1, sw2
        except Exception as e:
            print(f"Błąd wysyłania APDU: {e}")
            return [], 0, 0

    def send_custom_apdu(self):
        """
        Umożliwia użytkownikowi ręczne wysłanie komendy APDU i wyświetla wynik (II.2c).
        """
        if self.connection is None:
            print("! Brak połączenia z kartą.")
            return

        command_input = input("Wprowadź komendę APDU (HEX, np. A0 C0 00 00 10): ").strip().upper()
        command_hex = "".join(command_input.split())

        if not command_hex or len(command_hex) % 2 != 0:
            print("! Nieprawidłowy format HEX lub nieparzysta liczba znaków.")
            return

        try:
            data, sw1, sw2 = self.send_apdu(command_hex)

            # Wyświetlanie w formacie HEX i status
            response_hex = toHexString(data) + f" {sw1:02X}{sw2:02X}"

            # Próba wyświetlenia znakowego
            try:
                response_chars = bytes(data).decode('ascii', errors='replace')
            except Exception:
                response_chars = "Nie można zdekodować jako ASCII."

            print(f"\n--- WYNIK APDU ---")
            print(f"Komenda: {command_hex}")
            print(f"Odpowiedź (HEX i SW): {response_hex}")
            print(f"Odpowiedź (Znakowo): '{response_chars}'")

        except Exception as e:
            print(f"Błąd podczas ręcznej transmisji APDU: {e}")

    # --- FUNKCJE DEKODUJĄCE ---

    def decode_semi_octet(self, bytes_list):
        """
        Dekoduje numery telefoniczne lub daty zakodowane w formacie Semi-Octet.
        Każda cyfra to 4 bity, bajty są odwrócone[cite: 57].
        """
        res = ""
        for b in bytes_list:
            s = f"{b:02x}"
            # Zamiana miejscami: 42 -> 24
            res += s[1] + s[0]
            # Usuwamy 'F' (wypełniacz dla nieparzystej liczby cyfr)
        return res.strip('F')

    def decode_gsm7bit(self, data, num_chars):
        """
        Dekoduje treść wiadomości lub nazwę kontaktu z kodowania GSM 7-bit[cite: 56].
        """
        decoded_text = ""
        bit_stream = ""

        # 1. Tworzenie strumienia bitów (z odwróceniem kolejności w bajcie)
        for byte in data:
            bits_msb = f"{byte:08b}"
            bits_lsb = bits_msb[::-1]
            bit_stream += bits_lsb

            # 2. Cięcie strumienia na 7-bitowe 'septety'
        for i in range(num_chars):
            start = i * 7
            end = start + 7

            if end > len(bit_stream):
                break

            septet_lsb = bit_stream[start:end]
            septet_msb = septet_lsb[::-1]  # Odwrócenie dla kolejności MSB (czytelnej)

            char_code = int(septet_msb, 2)

            # Pomijamy znaki kontrolne/puste lub zastępujemy
            if char_code == 0x00:
                decoded_text += ' '
            elif char_code == 0x0D:
                decoded_text += '\n'
            else:
                decoded_text += chr(char_code)

        return decoded_text.replace('\x00', '').replace('\xff', '').strip()

    # --- LOGIKA ODCZYTU DANYCH ---

    def read_contacts_logic(self):
        """
        Odczytuje całą książkę telefoniczną (EF_ADN). Realizuje punkt II.2d.

        """
        print("\n--- ODCZYT KONTAKTÓW (ADN) ---")

        # 1. Wybór ścieżki
        self.send_apdu(SELECT_MF)
        self.send_apdu(SELECT_DF_TELECOM)
        data, sw1, sw2 = self.send_apdu(SELECT_EF_ADN)

        if sw1 != 0x90 and sw1 != 0x9F:
            print("! Nie udało się wybrać pliku kontaktów.")
            return

        # 2. Pobranie nagłówka pliku (FCP - File Control Parameter)
        resp_len = sw2 if sw1 == 0x9F else 15
        header_data, sw1_h, sw2_h = self.send_apdu(f"A0 C0 00 00 {resp_len:02X}")

        record_len = 28  # Fallback
        try:
            # W standardzie GSM bajt nr 14 w odpowiedzi to długość rekordu
            record_len = header_data[14]
        except IndexError:
            pass  # Użycie fallbacku

        print(f"Długość rekordu kontaktu: {record_len} bajtów")
        footer_len = 14  # Stała długość metadanych (numer, TON/NPI, itp.)

        # 3. Iteracja po rekordach
        for i in range(1, 255):
            # READ RECORD: A0 B2 [index] 04 [length] [cite: 48]
            cmd = f"A0 B2 {i:02X} 04 {record_len:02X}"
            rec_data, sw1, sw2 = self.send_apdu(cmd)

            # 6A 83 (Record not found), 6B 00 (Wrong P1 P2) - koniec listy
            if sw1 != 0x90 and sw1 != 0x9F:
                break

                # Sprawdzenie pustego rekordu (cały wypełniony 0xFF)
            if rec_data[0] == 0xFF:
                continue

                # --- PARSOWANIE REKORDU ADN ---

            # A. Nazwa (Alpha Identifier)
            alpha_len = record_len - footer_len
            name_bytes = rec_data[:alpha_len]

            try:
                # Dekodowanie GSM 7-bit (bardziej poprawne dla nazw)
                name = self.decode_gsm7bit(name_bytes, alpha_len)
            except Exception:
                name = toHexString(name_bytes).replace(' FF', '').strip()

                # B. Numer (Sekcja stopki)
            number_part = rec_data[alpha_len:]
            len_num = number_part[0]  # Długość w cyfrach dziesiętnych

            if len_num == 0 or len_num == 0xFF:
                continue

            ton_npi = number_part[1]  # Typ numeru

            num_bytes_len = math.ceil(len_num / 2)
            number_bcd = number_part[2: 2 + num_bytes_len]

            raw_num = self.decode_semi_octet(number_bcd)

            # Obsługa dla numerów międzynarodowych (TON = 0x91)
            if ton_npi == 0x91:
                raw_num = "+" + raw_num

            print(f"Slot {i:02X}: {name} | Tel: {raw_num}")

    def read_sms_logic(self):
        """
        Odczytuje listę SMS-ów i wyświetla ich treść. Realizuje punkt II.2e.
        """
        print("\n--- ODCZYT WIADOMOŚCI SMS (EF_SMS) ---")

        # Nawigacja
        self.send_apdu(SELECT_MF)
        self.send_apdu(SELECT_DF_TELECOM)
        data, sw1, sw2 = self.send_apdu(SELECT_EF_SMS)

        if sw1 != 0x90 and sw1 != 0x9F:
            print("! Błąd przy SELECT_EF_SMS.")
            return

        # Pobranie nagłówka (GET RESPONSE)
        dl = sw2
        data_2, sw1_2, sw2_2 = self.send_apdu(f"A0 C0 00 00 {dl:02X}")

        # Długość rekordu SMS jest często stała (176 bajtów)
        sms_len = 176

        print(f"Długość rekordu SMS: {sms_len} bajtów")

        # Iteracja po slotach
        for i in range(1, 11):  # Zwykle karty mają 10-50 slotów dla SMS
            # READ RECORD: A0 B2 [index] 04 [length]
            sms_data, sw1_3, sw2_3 = self.send_apdu(f"A0 B2 {i:02X} 04 {sms_len:02X}")

            if sw1_3 != 0x90:
                continue

            status = sms_data[0]  # Status SMS-a (Pierwszy bajt rekordu)

            # 0x00 - Slot wolny, 0xFF - Slot wyczyszczony [cite: 316, 321]
            if status == 0x00 or status == 0xFF:
                continue

            print(f"\n--- Wiadomość nr {i} (Status: {hex(status)}) ---")
            self.decode_and_print_sms(sms_data)

    def decode_and_print_sms(self, record_bytes):
        """
        Dekoduje całą strukturę PDU SMS.
        """
        try:
            cursor = 1  # Pomijamy [0] (status)

            # 1. SCA (Service Center Address)
            sca_len = record_bytes[cursor]
            cursor += 1 + sca_len

            # 2. PDU-Type (flagi)
            cursor += 1

            # 3. Numer nadawcy (cyfry w semi-octet)
            number_len = record_bytes[cursor]
            number_type = record_bytes[cursor + 1]
            cursor += 2

            number_byte_len = math.ceil(number_len / 2)
            number_bytes = record_bytes[cursor: cursor + number_byte_len]
            sender_num = self.decode_semi_octet(number_bytes)

            if number_type == 0x91:
                sender_num = "+" + sender_num

            cursor += number_byte_len

            # 4. PID i DCS
            cursor += 2

            # 5. Data i czas (7 bajtów)
            time_bytes = record_bytes[cursor: cursor + 7]
            time_raw = self.decode_semi_octet(time_bytes)

            # Formatowanie: 20RR-MM-DD HH:MM:SS
            timestamp = f"20{time_raw[0:2]}-{time_raw[2:4]}-{time_raw[4:6]} {time_raw[6:8]}:{time_raw[8:10]}:{time_raw[10:12]}"
            cursor += 7

            # 6. Treść wiadomości
            udl = record_bytes[cursor]  # User Data Length (Liczba znaków)
            cursor += 1

            # Obliczanie długości w bajtach (ceil(udl * 7 / 8))
            septets_in_bytes = math.ceil(udl * 7 / 8)
            ud_bytes = record_bytes[cursor: cursor + septets_in_bytes]

            # Dekodowanie GSM 7-bit
            message_text = self.decode_gsm7bit(ud_bytes, udl)

            # --- WYŚWIETLANIE ---
            print(f"  Nadawca: {sender_num}")
            print(f"  Data: {timestamp}")
            print(f"  Treść: {message_text}")
            print("-" * 30)

        except Exception as e:
            print(f"!! BŁĄD PARSOWANIA PDU SMS: {e}")

    def menu(self):
        """
        Wyświetla główne menu i obsługuje wybór użytkownika.
        """
        while True:
            print("\n--- MENU GŁÓWNE ---")
            print("1. Odczytaj SMS")
            print("2. Odczytaj Kontakty (Książka telefoniczna)")
            print("3. Wyślij własną komendę APDU (Testowanie)")
            print("0. Wyjście")

            wybor = input("\nTwój wybór: ").strip()

            if wybor == '1':
                self.read_sms_logic()
            elif wybor == '2':
                self.read_contacts_logic()
            elif wybor == '3':
                self.send_custom_apdu()
            elif wybor == '0':
                print("Zamykanie programu...")
                break
            else:
                print("! Nieprawidłowa opcja.")


if __name__ == "__main__":
    # Sprawdzenie, czy biblioteka pyscard jest zainstalowana
    try:
        app = SimCardManager()
        if app.connect_reader():
            app.menu()
        else:
            print("Koniec programu (brak połączenia).")
    except ImportError:
        print("Błąd: Nie znaleziono biblioteki 'pyscard'. Upewnij się, że została zainstalowana komendą:")
        print("pip install pyscard")
    except Exception as e:
        print(f"Wystąpił nieoczekiwany błąd: {e}")