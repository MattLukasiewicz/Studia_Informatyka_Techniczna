#include "brute_force.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <climits>

using namespace std;

vector<vector<int>> wczytajMacierz(const string& sciezka, int& rozmiar, int& min_sum) {
    ifstream plik(sciezka);
    vector<vector<int>> macierz;
    

    if (!plik.is_open()) {
        cout << "Wykryto blad! Nie mozna otworzyc pliku: " << sciezka << "\n";
        return macierz; 
    }
    min_sum = 0;
    plik >> rozmiar;
    macierz.resize(rozmiar, vector<int>(rozmiar));

    for (int i = 0; i < rozmiar; i++) {
        for (int j = 0; j < rozmiar; j++) {
            plik >> macierz[i][j];
        }
    }
    string linia;
    while (plik >> linia) {
        size_t poz = linia.find("sum_min=");
        if (poz != string::npos) {
            string liczba_str = linia.substr(poz + 8);
            min_sum = stoi(liczba_str);
            break;
        }
    }
    plik.close();
    return macierz;
}

int liczKoszt(const vector<vector<int>>& macierz, const vector<int>& sciezka) {
    int suma = 0;
    int obecny = 0; 
    for (size_t i = 0; i < sciezka.size(); i++) {
        suma += macierz[obecny][sciezka[i]];
        obecny = sciezka[i];
    }
    suma += macierz[obecny][0]; 
    return suma;
}

int liczKosztSym(const vector<vector<int>>& macierz, int p, const vector<int>& srodek, int k) {
    int suma = macierz[0][p];
    int obecny = p;
    for (size_t i = 0; i < srodek.size(); i++) {
        suma += macierz[obecny][srodek[i]];
        obecny = srodek[i];
    }
    suma += macierz[obecny][k];
    suma += macierz[k][0];
    
    return suma;
}

bool nastepnaPermutacja(vector<int>& p) {
    int n = p.size();
    if (n <= 1) return false;

    /*Przykladzik 1, 3, 5, 4, 2chcmey znalezc troszeczkę wieksza liczbe i 
    sprawdzmy ich szyk czy lewy jest wiekszy od prawego jesli tak to jest git jak nie to punkt zamiany*/ 
    int i = n - 2;
    while (i >= 0 && p[i] >= p[i + 1]) {
        i--;
    }
    // warunek jesli przejddzimy i indeks mniejszy to znaczy ze juz nei ma permutacji nastepnej
    if (i < 0) return false; 


    /*teraz szukamy najmniejszej liczby wiekszej od tej na indeksie i jak ją znajdziemy to zamana*/
    int j = n - 1;
    while (p[j] <= p[i]) {
        j--;
    }

    swap(p[i], p[j]);


    /*tu podmieniam strony bo od i w lewo jest uporzadkowane 
    rosnaco ale w prawo malejaco czyli tworzy najwiksza liczbe nie a chcemy najmniejsza mozliwa wiec odbicie lustrzane*/
    int lewy = i + 1;
    int prawy = n - 1;
    while (lewy < prawy) {
        swap(p[lewy], p[prawy]);
        lewy++;
        prawy--;
    }

    return true; 
}


int bruteForceAsymetryczny(const vector<vector<int>>& macierz, int rozmiar) {
    if (rozmiar <= 1) return 0;

    vector<int> sciezka;
    for (int i = 1; i < rozmiar; i++) {
        sciezka.push_back(i);
    }

    int minKoszt = INT_MAX;
    bool sprawdzajDalej = true; 

    while (sprawdzajDalej) {
        int obecnyKoszt = liczKoszt(macierz, sciezka);
        if (obecnyKoszt < minKoszt) {
            minKoszt = obecnyKoszt;
        }
        sprawdzajDalej = nastepnaPermutacja(sciezka);
    }

    return minKoszt;
}

int bruteForceSymetryczny(const vector<vector<int>>& macierz, int rozmiar) {
    if (rozmiar == 2) return macierz[0][1] + macierz[1][0];
    if (rozmiar <= 1) return 0;

    int minKoszt = INT_MAX;

    for (int drugie = 1; drugie < rozmiar; drugie++) {
        for (int ostatnie = drugie + 1; ostatnie < rozmiar; ostatnie++) {
            
            vector<int> srodek;
            for (int i = 1; i < rozmiar; i++) {
                if (i != drugie && i != ostatnie) {
                    srodek.push_back(i);
                }
            }

            if (srodek.empty()) {
                int koszt = macierz[0][drugie] + macierz[drugie][ostatnie] + macierz[ostatnie][0];
                if (koszt < minKoszt) {
                    minKoszt = koszt;
                }
                continue;
            }

            bool sprawdzajDalej = true; 
            
            while (sprawdzajDalej) {
                int obecnyKoszt = liczKosztSym(macierz, drugie, srodek, ostatnie);
                if (obecnyKoszt < minKoszt) {
                    minKoszt = obecnyKoszt;
                }
                
                sprawdzajDalej = nastepnaPermutacja(srodek);
            }
        }
    }

    return minKoszt;
}