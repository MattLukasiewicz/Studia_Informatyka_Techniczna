#include "rand_algorytm.h"
#include <algorithm>
#include <random>
#include <climits>

using namespace std;

int algorytmRand(const vector<vector<int>>& macierz, int rozmiar, long long iteracje) {
    if (rozmiar <= 1) return 0;

    vector<int> miasta;
    for (int i = 1; i < rozmiar; i++) {
        miasta.push_back(i);
    }

    int minKoszt = INT_MAX;
    random_device rd;
    mt19937 gen(rd()); 

    for (long long i = 0; i < iteracje; ++i) {
        shuffle(miasta.begin(), miasta.end(), gen);

        int obecnyKoszt = macierz[0][miasta[0]];
        for (size_t j = 0; j < miasta.size() - 1; j++) {
            obecnyKoszt += macierz[miasta[j]][miasta[j + 1]];
        }
        obecnyKoszt += macierz[miasta.back()][0];

        
        if (obecnyKoszt < minKoszt) {
            minKoszt = obecnyKoszt;
        }
    }

    return minKoszt;
}