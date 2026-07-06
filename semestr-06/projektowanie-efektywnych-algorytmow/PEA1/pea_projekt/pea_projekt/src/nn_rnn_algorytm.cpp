#include "nn_rnn_algorytm.h"

using namespace std;

int algorytmNN(const vector<vector<int>>& macierz, int n) {
    if (n <= 1) return 0;

    vector<bool> odwiedzone(n, false);
    int obecny = 0;
    odwiedzone[obecny] = true;
    
    int kosztCalkowity = 0;
    int odwiedzonych = 1;

    while (odwiedzonych < n) {
        int minKoszt = 99999999;
        int nastepny = -1;

        for (int i = 0; i < n; i++) {
            if (odwiedzone[i] == false && macierz[obecny][i] >= 0) {
                if (macierz[obecny][i] < minKoszt) {
                    minKoszt = macierz[obecny][i];
                    nastepny = i;
                }
            }
        }

        odwiedzone[nastepny] = true;
        kosztCalkowity += minKoszt;
        obecny = nastepny;
        odwiedzonych++;
    }

    kosztCalkowity += macierz[obecny][0]; 
    return kosztCalkowity;
}

void rnnRekurencja(const vector<vector<int>>& macierz, int n, int start, int obecny, vector<bool>& odwiedzone, int odwiedzonych, int aktualnyKoszt, int& najlepszyWynik) {
    
    //jak bylem wszedzie to wracam do domu
    if (odwiedzonych == n) {
        int kosztKoncowy = aktualnyKoszt + macierz[obecny][start];
        if (kosztKoncowy < najlepszyWynik) {
            najlepszyWynik = kosztKoncowy;
        }
        return;
    }
    //szukam najlepszego rozwiazania
    int minKoszt = 99999999;
    for (int i = 0; i < n; i++) {
        if (odwiedzone[i] == false && macierz[obecny][i] >= 0) {
            if (macierz[obecny][i] < minKoszt) {
                minKoszt = macierz[obecny][i];
            }
        }
    }
    //sprawdzam reemisy min koszt ten sam
    for (int i = 0; i < n; i++) {
        if (odwiedzone[i] == false && macierz[obecny][i] == minKoszt) {
            odwiedzone[i] = true;
            //szuka wglab sciezek najlepszych
            rnnRekurencja(macierz, n, start, i, odwiedzone, odwiedzonych + 1, aktualnyKoszt + minKoszt, najlepszyWynik);
                        
            odwiedzone[i] = false; 
        }
    }
}

int algorytmRNN(const vector<vector<int>>& macierz, int n) {
    if (n <= 1) return 0;
    
    int najlepszyWynik = 99999999;

    for (int start = 0; start < n; start++) {
        vector<bool> odwiedzone(n, false);
        odwiedzone[start] = true;
        
        rnnRekurencja(macierz, n, start, start, odwiedzone, 1, 0, najlepszyWynik);
    }

    return najlepszyWynik;
}