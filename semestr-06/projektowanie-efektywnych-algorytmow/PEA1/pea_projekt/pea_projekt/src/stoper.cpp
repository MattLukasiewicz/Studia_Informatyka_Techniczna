#include "stoper.h"

void Stoper::start() {
    czas_start = std::chrono::high_resolution_clock::now();
}

void Stoper::stop() {
    czas_stop = std::chrono::high_resolution_clock::now();
}

double Stoper::pobierzCzasMs() {
    std::chrono::duration<double, std::milli> czas_wykonania = czas_stop - czas_start;
    return czas_wykonania.count();
}