#include <iostream>
using namespace std;

int main() {
    int *p, K[][5] = {{1, 2, 3, 4}, {5, 6, 7}, {8, 9}, {10}};
    p = *K;
    cout << *p << endl; // 1

    p = K[1];
    cout << *p << endl; // 5

    p = *K + 2;
    cout << *p << endl; // 3

    p = *(K + 2);
    cout << *p << endl; // 8

    p = K[1] + 1;
    cout << *p << endl; // 6

    cout << *K << endl; // Adres początku pierwszego wiersza tablicy K

    cout << **K << endl; // Wartość na pierwszej pozycji w pierwszym wierszu, czyli 1

    cout << **K + 3 << endl; // Wartość na pierwszej pozycji w pierwszym wierszu + 3, czyli 1 + 3 = 4

    cout << *(*K + 3) << endl; // Wartość na czwartej pozycji w pierwszym wierszu, czyli 4

    cout << K[2][3] << endl; // Wartość na czwartej pozycji w trzecim wierszu, czyli 0
}
