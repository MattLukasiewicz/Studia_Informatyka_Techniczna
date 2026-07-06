def kodowanie_fec(bity_wejsciowe: str) -> str:
    # Definicja generatorów FEC
    G0 = [1, 1, 1, 1]  # G_0(x) = 1 + x + x^2 + x^3
    G1 = [1, 0, 1, 1]  # G_1(x) = 1 + x^2 + x^3

    # Rejestr o długości 4 bitów, początkowo ustawiony na [0, 0, 0, 0]
    rejestr = [0, 0, 0, 0]

    # Wynikowe bity kodowania jako string
    bity_wynikowe = ""

    # Kodowanie wejściowych bitów
    for i, b in enumerate(bity_wejsciowe):
        # Przekształcamy znak na liczbę
        bit = int(b)

        # Wyświetlamy krok i stan rejestru przed dodaniem bitu
        print(f"\nKrok {i + 1}:")
        print(f"Rejestr przed dodaniem bitu: {rejestr}")
        print(f"Dodawany bit: {bit}")

        # Przesuwamy rejestr w lewo, dodajemy bit wejściowy na początek
        rejestr.insert(0, bit)
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
        bity_wynikowe += f"{G0_wynik}{G1_wynik}"

        # Wyświetlamy stan rejestru po przetworzeniu bitu
        print(f"Rejestr po dodaniu bitu: {rejestr}")
        print(f"Aktualny wynik kodowania: {bity_wynikowe}")

    # Zwracamy wynikowe bity jako string
    return bity_wynikowe


# Dane wejściowe z artykułu (jako string)
#przyklad z artykulu "01101111010110000"
#moj orzyklad 1 z kartki "101011000" przyklad 2 "10100000"

bity_wejsciowe =  "01101111010110000"
bity_zakodowane = kodowanie_fec(bity_wejsciowe)

# Wyświetlenie wynikowego ciągu
#print(len(bity_zakodowane))
print("\nFinalny wynik kodowania:", bity_zakodowane)
