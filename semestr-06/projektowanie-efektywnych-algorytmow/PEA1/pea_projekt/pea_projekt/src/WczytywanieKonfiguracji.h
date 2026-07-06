#pragma once
#include <string>

struct Config {
    int tryb_dzialania;
    int typ_danych;
    std::string sciezka_pojedyncza;
    std::string folder_danych;
    std::string folder_wynikow;
    std::string plik_raport_bf;
    std::string plik_raport_rand;
    std::string plik_raport_heurystyki;
    
    bool wskaznik_postepu;
    bool generuj_macierze;

    long long liczba_iteracji;
    int liczba_powtorzen; 

    int wybrany_algorytm;
    int rozmiar_min;
    int rozmiar_max;
    int granica_rozmiaru;
    int ile_malych;
    int ile_duzych;
    int waga_min;
    int waga_max;
};

Config wczytajKonfiguracje(const std::string& nazwa_pliku);