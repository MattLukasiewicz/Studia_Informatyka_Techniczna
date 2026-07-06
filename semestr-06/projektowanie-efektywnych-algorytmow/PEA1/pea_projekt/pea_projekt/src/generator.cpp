#include "generator.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <random>
#include <string>

using namespace std;

void zapiszMacierzDoPliku(const string& sciezka, const vector<vector<int>>& macierz, int rozmiar) {
    ofstream plik(sciezka);
    if (!plik.is_open()) {
        cerr << "Wykryto blad nie mozna utworzyc pliku " << sciezka << "\n";
        return;
    }

    plik << rozmiar << "\n"; 
    
    for (int i = 0; i < rozmiar; ++i) {
        for (int j = 0; j < rozmiar; ++j) {
            plik << macierz[i][j] << " ";
        }
        plik << "\n";
    }
    
    plik << "\nsum_min=0\n";
    plik.close();
}

void generujWszystkieMacierze(const Config& konf) {
    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> dystrybucja(konf.waga_min, konf.waga_max);

    for (int rozmiar = konf.rozmiar_min; rozmiar <= konf.rozmiar_max; ++rozmiar) {
        
        int liczba_instancji = (rozmiar <= konf.granica_rozmiaru) ? konf.ile_malych : konf.ile_duzych;

        for (int instancja = 1; instancja <= liczba_instancji; ++instancja) {
            vector<vector<int>> macierzSymetryczna(rozmiar, vector<int>(rozmiar));
            vector<vector<int>> macierzAsymetryczna(rozmiar, vector<int>(rozmiar));

            for (int i = 0; i < rozmiar; ++i) {
                for (int j = 0; j < rozmiar; ++j) {
                    if (i == j) {
                        macierzSymetryczna[i][j] = -1;
                        macierzAsymetryczna[i][j] = -1;
                    } else if (i < j) {
                        int waga_sym = dystrybucja(gen);
                        int waga_asym1 = dystrybucja(gen);
                        int waga_asym2 = dystrybucja(gen);

                        macierzSymetryczna[i][j] = waga_sym;
                        macierzSymetryczna[j][i] = waga_sym;

                        macierzAsymetryczna[i][j] = waga_asym1;
                        macierzAsymetryczna[j][i] = waga_asym2;
                    }
                }
            }

            string nazwa_pliku = "macierz_" + to_string(rozmiar) + "x" + to_string(rozmiar) + "_" + to_string(instancja) + ".txt";

            string sciezkaSym = konf.folder_danych + "symetryczne/" + to_string(rozmiar) + "/" + nazwa_pliku;
            string sciezkaAsym = konf.folder_danych + "asymetryczne/" + to_string(rozmiar) + "/" + nazwa_pliku;

            zapiszMacierzDoPliku(sciezkaSym, macierzSymetryczna, rozmiar);
            zapiszMacierzDoPliku(sciezkaAsym, macierzAsymetryczna, rozmiar);
        }
    }
    
    
        cout << "Macierze wygenerowane\n";
    
}