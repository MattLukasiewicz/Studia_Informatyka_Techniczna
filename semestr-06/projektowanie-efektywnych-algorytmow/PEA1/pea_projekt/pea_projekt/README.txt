PROJEKT PEA - INSTRUKCJA KOMPILACJI I URUCHAMIANIA

1) WYMAGANIA
- g++ z obsluga C++17

2) KOMPILACJA

g++ -std=c++17 src/main.cpp src/WczytywanieKonfiguracji.cpp src/generator.cpp src/brute_force.cpp src/stoper.cpp src/brute_force_testy.cpp src/rand_algorytm.cpp src/rand_testy.cpp src/nn_rnn_algorytm.cpp src/nn_rnn_testy.cpp -o pea_projekt.exe

3) URUCHOMIENIE

./pea_projekt.exe

Program zawsze czyta ustawienia z pliku config.txt.

4) CO ROBIA ZMIENNE W config.txt
TRYB
- 0 = pojedynczy test na jednej macierzy (BF, NN, RNN lub RAND)
- 1 = testy masowe BF
- 2 = test RAND
- 3 = testy NN/RNN

TYP_DANYCH
- 1 = asymetryczne
- 2 = symetryczne
- Uzywane glownie w TRYB=1.

ALGORYTM
- 1 = Brute Force Asymetryczny
- 2 = Brute Force Symetryczny
- 3 = NN
- 4 = RNN
- 5 = RAND (tylko w TRYB=0)
- Powiazanie z trybami:
	- TRYB=0: dozwolone 1, 2, 3, 4, 5
	- TRYB=1: dozwolone 1 albo 2
	- TRYB=2: uruchamiany jest RAND (wartosc ALGORYTM nie jest kluczowa)
	- TRYB=3: dozwolone 3 albo 4

SCIEZKA_MACIERZY
- Sciezka relatywna do jednej instancji testowej.
- Uzywana w TRYB=0, TRYB=2, TRYB=3.

ITERACJE
- Liczba iteracji algorytmu RAND w jednej probie.
- Uzywane w TRYB=2.

POWTORZENIA
- Ile razy powtarzac test dla tej samej instancji.
- Uzywane w TRYB=2 i TRYB=3.

FOLDER_DANYCH
- Katalog z danymi wejsciowymi i podfolderami symetryczne/asymetryczne.

FOLDER_WYNIKOW
- Katalog, do ktorego trafiaja raporty CSV.

PLIK_RAPORT_BF
- Nazwa pliku CSV dla testow BF (TRYB=1).

PLIK_RAPORT_RAND
- Nazwa pliku CSV dla testow RAND (TRYB=2).

PLIK_RAPORT_HEURYSTYKI
- Nazwa pliku CSV dla testow NN/RNN (TRYB=3).

WSKAZNIK_POSTEPU
- 0 = bez paska postepu.
- 1 = pokazuje pasek postepu "Postep: [###...]".
- Pozostale logi testowe sa wyswietlane zawsze.

GENERUJ_MACIERZE
- 0 = nie generuj nowych macierzy przy starcie.
- 1 = generuj macierze przed testami.

ROZMIAR_MIN
- Minimalny rozmiar macierzy generowanej i testowanej masowo.

ROZMIAR_MAX
- Maksymalny rozmiar macierzy generowanej i testowanej masowo.

GRANICA_ROZMIARU
- Dla rozmiarow <= granicy uzywane ILE_MALYCH.
- Dla rozmiarow > granicy uzywane ILE_DUZYCH.

ILE_MALYCH
- Liczba instancji na rozmiar dla mniejszych rozmiarow.

ILE_DUZYCH
- Liczba instancji na rozmiar dla wiekszych rozmiarow.

WAGA_MIN
- Minimalna wartosc krawedzi przy generowaniu macierzy.

WAGA_MAX
- Maksymalna wartosc krawedzi przy generowaniu macierzy.

5) JAK WYKONYWANE SA TESTY DLA KAZDEGO ALGORYTMU

Brute Force Asymetryczny (ALGORYTM=1)
- Tryb pojedynczy: TRYB=0.
- Tryb testow masowych: TRYB=1 oraz TYP_DANYCH=1.
- Dla kazdego rozmiaru od ROZMIAR_MIN do ROZMIAR_MAX program wczytuje instancje z folderu dane/asymetryczne.
- Dla kazdej instancji mierzony jest czas wykonania w ms.
- Na ekranie: numer instancji, rozmiar, koszt, czas.
- Dodatkowo przy WSKAZNIK_POSTEPU=1: pasek postepu.
- Do CSV: sredni czas dla kazdego rozmiaru.

Brute Force Symetryczny (ALGORYTM=2)
- Tryb pojedynczy: TRYB=0.
- Tryb testow masowych: TRYB=1 oraz TYP_DANYCH=2.
- Dla kazdego rozmiaru od ROZMIAR_MIN do ROZMIAR_MAX program wczytuje instancje z folderu dane/symetryczne.
- Dla kazdej instancji mierzony jest czas wykonania w ms.
- Na ekranie: numer instancji, rozmiar, koszt, czas.
- Dodatkowo przy WSKAZNIK_POSTEPU=1: pasek postepu.
- Do CSV: sredni czas dla kazdego rozmiaru.

RAND (TRYB=2)
- Program wczytuje jedna instancje z SCIEZKA_MACIERZY.
- Rozmiar instancji jest wyswietlany na ekranie.
- W kazdej probie wykonywane jest ITERACJE losowych tasowan trasy, a koszt minimalny z tej proby jest zapamietywany.
- Liczba prob = POWTORZENIA.
- Mierzone sa: czas kazdej proby (ms), koszt kazdej proby, sredni czas, sredni blad wzgledem wartosci optymalnej z pliku.
- Na ekranie: numer proby, rozmiar, koszt, czas.
- Dodatkowo przy WSKAZNIK_POSTEPU=1: pasek postepu.
- Do CSV: rozmiar, sredni czas, sredni blad.
- W TRYB=0 mozna uruchomic pojedynczy RAND ustawiajac ALGORYTM=5.

NN (ALGORYTM=3, TRYB=3)
- Program wczytuje jedna instancje z SCIEZKA_MACIERZY.
- Rozmiar instancji jest wyswietlany na ekranie.
- Liczba przebiegow = POWTORZENIA.
- Mierzone sa: czas kazdego przebiegu (ms), koszt kazdego przebiegu, sredni czas, sredni blad.
- Na ekranie: numer przejscia, rozmiar, koszt, czas.
- Dodatkowo przy WSKAZNIK_POSTEPU=1: pasek postepu.
- Do CSV: rozmiar, sredni czas, sredni blad.
- W TRYB=0 mozna uruchomic pojedynczy NN ustawiajac ALGORYTM=3.

RNN (ALGORYTM=4, TRYB=3)
- Program dziala analogicznie do NN, ale uruchamia algorytm RNN.
- Liczba przebiegow = POWTORZENIA.
- Mierzone sa: czas kazdego przebiegu (ms), koszt kazdego przebiegu, sredni czas, sredni blad.
- Na ekranie: numer przejscia, rozmiar, koszt, czas.
- Dodatkowo przy WSKAZNIK_POSTEPU=1: pasek postepu.
- Do CSV: rozmiar, sredni czas, sredni blad.
- W TRYB=0 mozna uruchomic pojedynczy RNN ustawiajac ALGORYTM=4.

6) GOTOWE USTAWIENIA DO PRZEKOPIOWANIA
Ponizej sa kompletne bloki. Skopiuj wybrany blok do pliku config.txt.

# SPRAWDZENIE POPRAWNOSCI NA matrix_11x11.txt (TRYB POJEDYNCZY)

TRYB=0
TYP_DANYCH=2
ALGORYTM=2
SCIEZKA_MACIERZY=dane/matrix_11x11.txt
ITERACJE=100000
POWTORZENIA=1
FOLDER_DANYCH=dane/
FOLDER_WYNIKOW=wyniki/
PLIK_RAPORT_BF=raport.csv
PLIK_RAPORT_RAND=raport_rand.csv
PLIK_RAPORT_HEURYSTYKI=raport_heurystyki.csv
WSKAZNIK_POSTEPU=1
GENERUJ_MACIERZE=0
ROZMIAR_MIN=6
ROZMIAR_MAX=15
GRANICA_ROZMIARU=10
ILE_MALYCH=5
ILE_DUZYCH=2
WAGA_MIN=1
WAGA_MAX=99


# TESTY MASOWE BRUTE FORCE - SYMETRYCZNE
TRYB=1
TYP_DANYCH=2
ALGORYTM=2
SCIEZKA_MACIERZY=dane/matrix_11x11.txt
ITERACJE=100000
POWTORZENIA=1
FOLDER_DANYCH=dane/
FOLDER_WYNIKOW=wyniki/
PLIK_RAPORT_BF=raport.csv
PLIK_RAPORT_RAND=raport_rand.csv
PLIK_RAPORT_HEURYSTYKI=raport_heurystyki.csv
WSKAZNIK_POSTEPU=1
GENERUJ_MACIERZE=0
ROZMIAR_MIN=6
ROZMIAR_MAX=10
GRANICA_ROZMIARU=10
ILE_MALYCH=5
ILE_DUZYCH=2
WAGA_MIN=1
WAGA_MAX=99

# TESTY MASOWE BRUTE FORCE - ASYMETRYCZNE
TRYB=1
TYP_DANYCH=1
ALGORYTM=1
SCIEZKA_MACIERZY=dane/matrix_11x11.txt
ITERACJE=100000
POWTORZENIA=1
FOLDER_DANYCH=dane/
FOLDER_WYNIKOW=wyniki/
PLIK_RAPORT_BF=raport.csv
PLIK_RAPORT_RAND=raport_rand.csv
PLIK_RAPORT_HEURYSTYKI=raport_heurystyki.csv
WSKAZNIK_POSTEPU=1
GENERUJ_MACIERZE=0
ROZMIAR_MIN=6
ROZMIAR_MAX=10
GRANICA_ROZMIARU=10
ILE_MALYCH=5
ILE_DUZYCH=2
WAGA_MIN=1
WAGA_MAX=99


#TEST ALGORYTMU RAND
TRYB=2
TYP_DANYCH=2
ALGORYTM=2
SCIEZKA_MACIERZY=dane/tsplib/sym/dantzig42.tsp_prosty.txt
ITERACJE=100000
POWTORZENIA=10
FOLDER_DANYCH=dane/
FOLDER_WYNIKOW=wyniki/
PLIK_RAPORT_BF=raport.csv
PLIK_RAPORT_RAND=raport_rand.csv
PLIK_RAPORT_HEURYSTYKI=raport_heurystyki.csv
WSKAZNIK_POSTEPU=1
GENERUJ_MACIERZE=0
ROZMIAR_MIN=6
ROZMIAR_MAX=15
GRANICA_ROZMIARU=10
ILE_MALYCH=5
ILE_DUZYCH=2
WAGA_MIN=1
WAGA_MAX=99


#TEST HEURYSTYKI NN
TRYB=3
TYP_DANYCH=2
ALGORYTM=3
SCIEZKA_MACIERZY=dane/tsplib/sym/dantzig42.tsp_prosty.txt
ITERACJE=100000
POWTORZENIA=50
FOLDER_DANYCH=dane/
FOLDER_WYNIKOW=wyniki/
PLIK_RAPORT_BF=raport.csv
PLIK_RAPORT_RAND=raport_rand.csv
PLIK_RAPORT_HEURYSTYKI=raport_heurystyki.csv
WSKAZNIK_POSTEPU=1
GENERUJ_MACIERZE=0
ROZMIAR_MIN=6
ROZMIAR_MAX=15
GRANICA_ROZMIARU=10
ILE_MALYCH=5
ILE_DUZYCH=2
WAGA_MIN=1
WAGA_MAX=99

#TEST HEURYSTYKI RNN

TRYB=3
TYP_DANYCH=2
ALGORYTM=4
SCIEZKA_MACIERZY=dane/tsplib/sym/dantzig42.tsp_prosty.txt
ITERACJE=100000
POWTORZENIA=50
FOLDER_DANYCH=dane/
FOLDER_WYNIKOW=wyniki/
PLIK_RAPORT_BF=raport.csv
PLIK_RAPORT_RAND=raport_rand.csv
PLIK_RAPORT_HEURYSTYKI=raport_heurystyki.csv
WSKAZNIK_POSTEPU=1
GENERUJ_MACIERZE=0
ROZMIAR_MIN=6
ROZMIAR_MAX=15
GRANICA_ROZMIARU=10
ILE_MALYCH=5
ILE_DUZYCH=2
WAGA_MIN=1
WAGA_MAX=99

