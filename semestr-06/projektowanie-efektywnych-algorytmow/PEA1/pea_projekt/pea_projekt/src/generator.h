#pragma once
#include <string>
#include <vector>
#include "WczytywanieKonfiguracji.h"

void zapiszMacierzDoPliku(const std::string& sciezka, const std::vector<std::vector<int>>& macierz, int rozmiar);
void generujWszystkieMacierze(const Config& konf);