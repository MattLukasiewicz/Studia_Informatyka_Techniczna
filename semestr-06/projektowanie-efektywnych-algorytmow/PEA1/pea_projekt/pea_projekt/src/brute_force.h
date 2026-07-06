#pragma once
#include <string>
#include <vector>

std::vector<std::vector<int>> wczytajMacierz(const std::string& sciezka, int& rozmiar, int& optymalny_koszt);

int bruteForceAsymetryczny(const std::vector<std::vector<int>>& macierz, int rozmiar);
int bruteForceSymetryczny(const std::vector<std::vector<int>>& macierz, int rozmiar);