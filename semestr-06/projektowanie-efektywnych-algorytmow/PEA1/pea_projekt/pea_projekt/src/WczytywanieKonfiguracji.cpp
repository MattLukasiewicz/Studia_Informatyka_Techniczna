#include "WczytywanieKonfiguracji.h"
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

Config wczytajKonfiguracje(const string& nazwa_pliku) {
    Config konf = {};
    ifstream plik(nazwa_pliku);
    
    string linia;
    while (getline(plik, linia)) {
        if (linia.empty() || linia[0] == '#') {
            continue;
        }
        size_t pozycja = linia.find('=');
        if (pozycja != string::npos) {
            string klucz = linia.substr(0, pozycja);
            string wartosc = linia.substr(pozycja + 1);
            if (!wartosc.empty() && wartosc.back() == '\r') {
                wartosc.pop_back();
            }

            if (klucz == "TRYB") konf.tryb_dzialania = stoi(wartosc);
            else if (klucz == "TYP_DANYCH") konf.typ_danych = stoi(wartosc);
            else if (klucz == "ALGORYTM") konf.wybrany_algorytm = stoi(wartosc);
            else if (klucz == "SCIEZKA_MACIERZY") konf.sciezka_pojedyncza = wartosc;
            else if (klucz == "FOLDER_DANYCH") konf.folder_danych = wartosc;
            else if (klucz == "FOLDER_WYNIKOW") konf.folder_wynikow = wartosc;
            else if (klucz == "PLIK_RAPORT_BF") konf.plik_raport_bf = wartosc;
            else if (klucz == "PLIK_RAPORT_RAND") konf.plik_raport_rand = wartosc;
            else if (klucz == "PLIK_RAPORT_HEURYSTYKI") konf.plik_raport_heurystyki = wartosc;
            else if (klucz == "WSKAZNIK_POSTEPU") konf.wskaznik_postepu = (wartosc == "1");
            else if (klucz == "GENERUJ_MACIERZE") konf.generuj_macierze = (wartosc == "1");
            else if (klucz == "ROZMIAR_MIN") konf.rozmiar_min = stoi(wartosc);
            else if (klucz == "ROZMIAR_MAX") konf.rozmiar_max = stoi(wartosc);
            else if (klucz == "GRANICA_ROZMIARU") konf.granica_rozmiaru = stoi(wartosc);
            else if (klucz == "ILE_MALYCH") konf.ile_malych = stoi(wartosc);
            else if (klucz == "ILE_DUZYCH") konf.ile_duzych = stoi(wartosc);
            else if (klucz == "WAGA_MIN") konf.waga_min = stoi(wartosc);
            else if (klucz == "WAGA_MAX") konf.waga_max = stoi(wartosc);
            else if (klucz == "ITERACJE") konf.liczba_iteracji = stoll(wartosc);
            else if (klucz == "POWTORZENIA") konf.liczba_powtorzen = stoi(wartosc);
        }
    }
    plik.close();
    return konf;
}