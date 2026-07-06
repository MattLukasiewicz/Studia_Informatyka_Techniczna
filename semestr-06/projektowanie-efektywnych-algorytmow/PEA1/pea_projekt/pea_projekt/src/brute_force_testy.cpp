#include "brute_force_testy.h"
#include "brute_force.h"
#include "postep.h"
#include "stoper.h"
#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

void uruchomTestyMasowe(const Config& ustawienia) {
    cout << "TRYB: 1 BF\n";
    cout << "Badanie algorytmu nr: " << ustawienia.wybrany_algorytm << "\n";

    string sciezka_raportu = ustawienia.folder_wynikow + ustawienia.plik_raport_bf;

    ofstream plik_startowy(sciezka_raportu);
    if (!plik_startowy.is_open()) {
        cerr << "Wykryto blad Nie udalo sie utworzyc pliku z raportem: " << sciezka_raportu << "\n";
        return;
    }
    plik_startowy << "Rozmiar;Sredni_Czas_ms\n";
    plik_startowy.close();

    string podfolder = (ustawienia.typ_danych == 1) ? "asymetryczne/" : "symetryczne/";
    long long laczna_liczba_instancji = 0;
    for (int rozmiar = ustawienia.rozmiar_min; rozmiar <= ustawienia.rozmiar_max; ++rozmiar) {
        laczna_liczba_instancji += (rozmiar <= ustawienia.granica_rozmiaru) ? ustawienia.ile_malych : ustawienia.ile_duzych;
    }
    long long wykonane_instancje = 0;

    for (int rozmiar = ustawienia.rozmiar_min; rozmiar <= ustawienia.rozmiar_max; ++rozmiar) {
        cout << "Rozmiar aktualnie analizowanych instancji: " << rozmiar << "\n";

        int liczba_plikow = (rozmiar <= ustawienia.granica_rozmiaru) ? ustawienia.ile_malych : ustawienia.ile_duzych;
        double suma_czasow = 0.0;
        int liczba_poprawnych_plikow = 0;
        Stoper stoper;

        for (int instancja = 1; instancja <= liczba_plikow; ++instancja) {
            ++wykonane_instancje;
            string sciezka = ustawienia.folder_danych + podfolder + to_string(rozmiar) +
                             "/macierz_" + to_string(rozmiar) + "x" + to_string(rozmiar) + "_" + to_string(instancja) + ".txt";

            int wczytany_rozmiar = 0;
            int pusty_koszt = 0;
            vector<vector<int>> macierz = wczytajMacierz(sciezka, wczytany_rozmiar, pusty_koszt);

            if (wczytany_rozmiar > 0) {
                int koszt = 0;
                if (ustawienia.wybrany_algorytm == 1) {
                    stoper.start();
                    koszt = bruteForceAsymetryczny(macierz, wczytany_rozmiar);
                    stoper.stop();
                } else if (ustawienia.wybrany_algorytm == 2) {
                    stoper.start();
                    koszt = bruteForceSymetryczny(macierz, wczytany_rozmiar);
                    stoper.stop();
                }

                suma_czasow += stoper.pobierzCzasMs();
                ++liczba_poprawnych_plikow;

                cout << "Instancja " << instancja << " / " << liczba_plikow
                     << " | Rozmiar: " << rozmiar
                     << " | Koszt: " << koszt
                     << " | Czas: " << stoper.pobierzCzasMs() << " ms";
                if (ustawienia.wskaznik_postepu) {
                    const string pasek = zbudujPasekPostepu(wykonane_instancje, laczna_liczba_instancji);
                    cout << " | Postep: " << pasek;
                }
                cout << "\n";
            }
        }

        double sredni_czas = 0.0;
        if (liczba_poprawnych_plikow > 0) {
            sredni_czas = suma_czasow / liczba_poprawnych_plikow;
        }

        ofstream plik_dopisywanie(sciezka_raportu, ios::app);
        if (plik_dopisywanie.is_open()) {
            plik_dopisywanie << rozmiar << ";" << sredni_czas << "\n";
            plik_dopisywanie.close();
        }

        if (liczba_poprawnych_plikow > 0) {
            cout << "Sredni czas dla rozmiaru " << rozmiar << ": "
                 << sredni_czas << " ms (na podstawie "
                 << liczba_poprawnych_plikow << " instancji).\n";
        } else {
            cout << "Brak poprawnych plikow dla rozmiaru " << rozmiar << ".\n";
        }
    }

    cout << "Koniec testow\n";
}
