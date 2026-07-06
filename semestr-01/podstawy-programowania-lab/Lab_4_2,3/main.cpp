/*
Autor:  Mateusz Łukasiewicz
Grupa:  Czwartek/P 15:15
Temat:  Laboratoria 4
Data:   24.12.2023 r.
*/
#include <iostream>
using namespace std;
int zadanie_2(char* lancuch) {
    int liczba_usunietych = 0;

    for (int i = 0; lancuch[i] != '\0'; i++) {

        if (lancuch[i] >= '0' && lancuch[i] <='9') {
            /* Jesli znak to cyfra to usuwamy go i zwiekszamy liczbe usunietych cyfr
            przesuwamy wszystkie znaki po prawej stronie o jedną pozycję w lewo
            Zaczynamy od indeksu 'i', ktory wskazuje na aktualna cyfre, która chcemy usunac*/
            for (int j = i; lancuch[j] != '\0'; j++) {
                lancuch[j] = lancuch[j + 1];
            }
            i--; //zmniejszamy 'i', aby ponownie sprawdzic aktualny indeks, poniewaz przesuwamy znaki w lewo
            liczba_usunietych++;
        }
    }
    return liczba_usunietych;
}


char* zadanie_3(char* lancuch) {
    char* wynik = lancuch; //inicjalizacja wskaznika wyniku na początku lancucha


    while (*lancuch != '\0') {
        if (*lancuch == '/' && *(lancuch + 1) == '*') {
            while (*lancuch != '\0' && !(*lancuch == '*' && *(lancuch + 1) == '/')) {// Przesuwamy się do końca komentarza "*/"
                lancuch++;
            }
            if (*lancuch != '\0') {
                lancuch += 2; // Przesuwamy sie poza "*/"
            }
        }else if (*lancuch == '/' && *(lancuch + 1) == '/') {
            while (*lancuch != '\0' && *lancuch != '\n') {// Przesuwamy się do konca linii
                lancuch++;
            }
            if (*lancuch != '\0') {
                lancuch++; // Przesuwamy się poza znak nowej linii
            }
        }
            // Jesli nie jest to komentarz, kopiujemy znak do wynik
        else {
            *wynik = *lancuch;
            wynik++;
            lancuch++;
        }
    }

    *wynik = '\0';
    cout<<wynik;
    return lancuch;
}
int main() {
cout<<"Autor: Mateusz Lukasiewicz (Czwartek/P 15:15) \n\n";//Mateusz Łukasiewicz
const int rozmiar = 100;
char lancuch[rozmiar];
int wybor;

do {
    cout << "\nMenu:\n";
    cout << "0. Wyjscie\n";
    cout << "2. Zadanie 2 - Usuniecie cyfr\n";
    cout << "3. Zadanie 3 - Usuniecie komentarzy\n";
    cout << "Wybierz opcje (0,2,3): ";
    cin >> wybor;

    switch (wybor) {
        case 2:
            cout << "Podaj lancuch znakow: ";
            cin.ignore(); // ignorujemy znak nowej linii z poprzedniego wejscia
            cin.getline(lancuch, rozmiar);
            cout << "Liczba znalezionych i usunietych cyfr: " << zadanie_2(lancuch) << endl;
            cout << "Zmodyfikowany lancuch zgodny z poleceniem zadania 2:  " << lancuch << endl;
            break;

        case 3:
            cout << "Podaj lancuch znakow: ";
            cin.ignore();
            cin.getline(lancuch, rozmiar);
            zadanie_3(lancuch);
            cout << "Zmodyfikowany lancuch zgodny z poleceniem zadania 3: " << lancuch << endl;
            break;

        case 0:
            cout << "Koniec programu.\n";
            break;

        default:
            cout << "Nieprawidlowy wybor. Sprobuj ponownie.\n";
    }

} while (wybor != 0);

return 0;
}
