def dekoduj_fec(bity_zakodowane: str) -> str:
    # Definicja generatorów FEC
    G0 = [1, 1, 1, 1]  # G_0(x) = 1 + x + x^2 + x^3
    G1 = [1, 0, 1, 1]  # G_1(x) = 1 + x^2 + x^3

    # Rejestr o długości 4 bitów, początkowo ustawiony na [0, 0, 0, 0]
    rejestr = [0, 0, 0, 0]

    # Wynikowe bity dekodowania
    bity_wynikowe = []

    # Liczba par (każda para to G0 + G1)
    liczba_par = len(bity_zakodowane) // 2

    for i in range(liczba_par):
        # Pobierz zakodowaną parę bitów (G0 i G1) jako liczby
        G0_bit = int(bity_zakodowane[2 * i])
        G1_bit = int(bity_zakodowane[2 * i + 1])

        # Próbujemy znaleźć bit wejściowy, który pasuje do tej pary
        for b in [0, 1]:
            # Symulujemy rejestr z dodanym bitem b
            testowy_rejestr = [b] + rejestr[:-1]

            # Obliczamy bity G0 i G1 dla testowego rejestru
            G0_test = sum([testowy_rejestr[j] * G0[j] for j in range(4)]) % 2
            G1_test = sum([testowy_rejestr[j] * G1[j] for j in range(4)]) % 2

            # Jeśli testowe bity zgadzają się z zakodowanymi, to znaleźliśmy bit wejściowy
            if G0_test == G0_bit and G1_test == G1_bit:
                bity_wynikowe.append(b)
                rejestr = testowy_rejestr  # Aktualizujemy rejestr
                break

    # Usuwamy 3 końcowe bity, które są "tail bits"
    # Dodaj [:-3] jeśli chcesz pominąć końcowe "000"
    return ''.join(map(str, bity_wynikowe))


# Przykład zakodowanego ciągu (jako string)
# przyklad z artykulu "0011010111101001101101001001001100"
#przykład 1 mój z kartki 111000010010010011" przykład 2 "1110000111110000"
bity_zakodowane = "0011010111101001101101001001001100"

# Dekodowanie
bity_odzyskane = dekoduj_fec(bity_zakodowane)

# Wyświetlenie wyniku
print("Odzyskany ciąg:", bity_odzyskane)
