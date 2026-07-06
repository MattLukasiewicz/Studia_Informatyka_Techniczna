#include <iostream>
#include <chrono> 
#include "WczytywanieKonfiguracji.h"
#include "generator.h"
#include "brute_force.h" 
#include "rand_algorytm.h"
#include "nn_rnn_algorytm.h"
#include "stoper.h"
#include "brute_force_testy.h"
#include "rand_testy.h"
#include "nn_rnn_testy.h"

using namespace std;

int main() {
    Config ustawienia = wczytajKonfiguracje("config.txt");

    if (ustawienia.generuj_macierze) {
        if (ustawienia.wskaznik_postepu) cout << "Rozpoczynam czyszczenie i generowanie macierzy...\n";
        generujWszystkieMacierze(ustawienia);
    }

    if (ustawienia.tryb_dzialania == 0) {
        cout << "TRYB: 0 Test pojedynczej macierzy\n";
        
        cout << "Badany plik: " << ustawienia.sciezka_pojedyncza << "\n";
        

        int wczytany_rozmiar = 0;
        int pusty_koszt = 0;
        vector<vector<int>> macierz = wczytajMacierz(ustawienia.sciezka_pojedyncza, wczytany_rozmiar, pusty_koszt);

        if (wczytany_rozmiar > 0) {
            int wynik = 0; 
            Stoper stoper;
            
            if (ustawienia.wybrany_algorytm == 1) {
                if (ustawienia.wskaznik_postepu) cout << "Uruchamiam: Brute Force (Asymetryczny)\n";
                
                stoper.start();
                wynik = bruteForceAsymetryczny(macierz, wczytany_rozmiar);
                stoper.stop();
            } 
            else if (ustawienia.wybrany_algorytm == 2) {
                if (ustawienia.wskaznik_postepu) cout << "Uruchamiam: Brute Force (Symetryczny)\n";
                
                stoper.start();
                wynik = bruteForceSymetryczny(macierz, wczytany_rozmiar);
                stoper.stop();
            } 
            else if (ustawienia.wybrany_algorytm == 3) {
                if (ustawienia.wskaznik_postepu) cout << "Uruchamiam: NN \n";

                stoper.start();
                wynik = algorytmNN(macierz, wczytany_rozmiar);
                stoper.stop();
            }
            else if (ustawienia.wybrany_algorytm == 4) {
                if (ustawienia.wskaznik_postepu) cout << "Uruchamiam: RNN \n";

                stoper.start();
                wynik = algorytmRNN(macierz, wczytany_rozmiar);
                stoper.stop();
            }
            else if (ustawienia.wybrany_algorytm == 5) {
                if (ustawienia.wskaznik_postepu) {
                    cout << "Uruchamiam: RAND\n";
                    cout << "Liczba iteracji RAND: " << ustawienia.liczba_iteracji << "\n";
                }

                stoper.start();
                wynik = algorytmRand(macierz, wczytany_rozmiar, ustawienia.liczba_iteracji);
                stoper.stop();
            }
            else {
                cerr << "Blad: Nieznany algorytm w pliku konfiguracyjnym!\n";
                return 1; 
            }
            
            cout << "Minimalny koszt trasy: " << wynik << "\n";
            cout << "Czas obliczen: " << stoper.pobierzCzasMs() << " ms\n";
            
        } else {
            cout << "Blad: Nie udalo sie wczytac macierzy. Sprawdz sciezke w config.txt!\n";
        }
    }else if (ustawienia.tryb_dzialania == 1) {
        uruchomTestyMasowe(ustawienia);    
    }else if (ustawienia.tryb_dzialania == 2) {
        uruchomTestyRand(ustawienia);
    } else if (ustawienia.tryb_dzialania == 3) {
        testujHeurystyki(ustawienia);
    }

    return 0;
}