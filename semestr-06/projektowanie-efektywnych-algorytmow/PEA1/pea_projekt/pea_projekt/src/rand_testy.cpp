#include "rand_testy.h"
#include "rand_algorytm.h"
#include "brute_force.h"
#include "postep.h"
#include "stoper.h"
#include <iostream>
#include <fstream>

using namespace std;

void uruchomTestyRand(const Config& konf) {
    cout << "TRYB: 2 Test RAND\n";
    int n = 0;
    int optKoszt = 0;
    vector<vector<int>> macierz = wczytajMacierz(konf.sciezka_pojedyncza, n, optKoszt);

    if (n == 0) return; 

    cout << "Rozmiar aktualnie analizowanej instancji: " << n << "\n";

    double sumaCzasu = 0;
    int sumaKosztu = 0;
    Stoper stoper;

    for (int i = 0; i < konf.liczba_powtorzen; i++) {
        stoper.start();
        int wynik = algorytmRand(macierz, n, konf.liczba_iteracji);
        stoper.stop();

        sumaCzasu += stoper.pobierzCzasMs();
        sumaKosztu += wynik;

        cout << "Proba " << i + 1 << " / " << konf.liczba_powtorzen
             << " | Rozmiar: " << n
             << " | Koszt: " << wynik
             << " | Czas: " << stoper.pobierzCzasMs() << " ms";
        if (konf.wskaznik_postepu) {
            const string pasek = zbudujPasekPostepu(i + 1, konf.liczba_powtorzen);
            cout << " | Postep: " << pasek;
        }
        cout << "\n";
    }

    double sredniCzas = sumaCzasu / konf.liczba_powtorzen;
    double sredniKoszt = (double)sumaKosztu / konf.liczba_powtorzen;
    double blad = ((sredniKoszt - optKoszt) / optKoszt) * 100.0;

    cout << "Sredni czas: " << sredniCzas << " ms\n";
    cout << "Sredni blad: " << blad << " %\n";

    const string sciezka_raportu = konf.folder_wynikow + konf.plik_raport_rand;
    bool dodaj_naglowek = false;
    {
        ifstream odczyt(sciezka_raportu);
        dodaj_naglowek = (!odczyt.is_open() || odczyt.peek() == ifstream::traits_type::eof());
    }

    ofstream plik(sciezka_raportu, ios::app);
    if (plik.is_open()) {
        if (dodaj_naglowek) {
            plik << "Rozmiar_n;Sredni_Czas_ms;Sredni_Blad_proc\n";
        }
        plik << n << ";" << sredniCzas << ";" << blad << "\n";
        plik.close();
    }
}