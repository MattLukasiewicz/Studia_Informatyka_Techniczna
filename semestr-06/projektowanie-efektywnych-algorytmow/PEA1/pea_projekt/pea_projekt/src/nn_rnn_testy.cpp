#include "nn_rnn_testy.h"
#include "nn_rnn_algorytm.h"
#include "brute_force.h" 
#include "postep.h"
#include "stoper.h"
#include <iostream>
#include <fstream>

using namespace std;

void testujHeurystyki(const Config& konf) {
    cout << "TRYB: 3 Testy NN/RNN\n";
    int n = 0;
    int optymalnyKoszt = 0;
    vector<vector<int>> macierz = wczytajMacierz(konf.sciezka_pojedyncza, n, optymalnyKoszt);

    if (n == 0) return;

    cout << "Rozmiar aktualnie analizowanej instancji: " << n << "\n";

    double sumaCzasu = 0;
    int sumaKosztow = 0;
    Stoper stoper;

    for (int i = 0; i < konf.liczba_powtorzen; i++) {
        int wynik = 0;
        if (konf.wybrany_algorytm == 3) {
            stoper.start();
            wynik = algorytmNN(macierz, n);
            stoper.stop();
        } else if (konf.wybrany_algorytm == 4) {
            stoper.start();
            wynik = algorytmRNN(macierz, n);
            stoper.stop();
        }

        sumaCzasu += stoper.pobierzCzasMs();
        sumaKosztow += wynik;

        cout << "Przejscie " << i + 1 << " / " << konf.liczba_powtorzen
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
    double sredniKoszt = (double)sumaKosztow / konf.liczba_powtorzen;
    double blad = ((sredniKoszt - optymalnyKoszt) / optymalnyKoszt) * 100.0;

    cout << "Sredni czas: " << sredniCzas << " ms\n";
    cout << "Sredni blad: " << blad << " %\n";

    const string sciezka_raportu = konf.folder_wynikow + konf.plik_raport_heurystyki;
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