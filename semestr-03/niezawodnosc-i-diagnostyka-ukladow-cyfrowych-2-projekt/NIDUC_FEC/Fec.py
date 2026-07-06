def zakoduj_fec_krok_po_kroku(bity_wejsciowe):
    # Definicja generatorów FEC
    G0 = [1, 1, 1, 1]  # G_0(x) = 1 + x + x^2 + x^3
    G1 = [1, 0, 1, 1]  # G_1(x) = 1 + x^2 + x^3

    # Rejestr o długości 4 bitów, początkowo ustawiony na [0, 0, 0, 0]
    rejestr = [0, 0, 0, 0]

    # Wynikowe bity kodowania
    bity_wynikowe = []

    # Kodowanie wejściowych bitów
    for i, b in enumerate(bity_wejsciowe):
        # Wyświetlamy krok i stan rejestru przed dodaniem bitu
        print(f"\nKrok {i + 1}:")
        print(f"Rejestr przed dodaniem bitu: {rejestr}")
        print(f"Dodawany bit: {b}")

        # Przesuwamy rejestr w lewo, dodajemy bit wejściowy na początek
        rejestr.insert(0, b)
        rejestr.pop()  # Usuwamy ostatni bit, aby rejestr miał długość 4

        # Obliczamy wynik dla G_0(x) i G_1(x)
        G0_obliczenia = [rejestr[j] * G0[j] for j in range(4)]
        G1_obliczenia = [rejestr[j] * G1[j] for j in range(4)]
        G0_wynik = sum(G0_obliczenia) % 2
        G1_wynik = sum(G1_obliczenia) % 2

        # Wyświetlamy szczegółowe obliczenia dla G_0 i G_1
        print(f"Obliczenia G_0(x): {G0_obliczenia} -> XOR -> {G0_wynik}")
        print(f"Obliczenia G_1(x): {G1_obliczenia} -> XOR -> {G1_wynik}")

        # Dodajemy obliczone bity do wyniku
        bity_wynikowe.append(G0_wynik)
        bity_wynikowe.append(G1_wynik)

        # Wyświetlamy stan rejestru po przetworzeniu bitu
        print(f"Rejestr po dodaniu bitu: {rejestr}")
        print(f"Aktualny wynik kodowania: {''.join(map(str, bity_wynikowe))}")

    # Zwracamy wynikowe bity
    return bity_wynikowe


# Dane wejściowe z artykułu
bity_wejsciowe = [0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0]
bity_zakodowane = zakoduj_fec_krok_po_kroku(bity_wejsciowe)

# Zamiana listy bitów na ciąg tekstowy
bity_zakodowane_str = ''.join(map(str, bity_zakodowane))
print("\nFinalny wynik kodowania:", bity_zakodowane_str)
