/*
Autor:  Mateusz Łukasiewicz
Grupa:  Czwartek/P 15:15
Temat:  Laboratoria 3
Data:   10.12.2023 r.
*/
#include <iostream>
#include <ctime>
#include <stdio.h>
#include <conio.h>
using namespace std;

#define ROZMIAR 5

// Funkcja do wczytywania elementów tablicy z klawiatury
void wczytaj_tablice(float tab[], int rozmiar) {
    for (int i = 0; i < rozmiar; i++) {
        printf("Tab[%d] = ", i);
        scanf("%f", &tab[i]);
    }
}

// Funkcja do wypisywania elementów tablicy na ekranie
void wypisz_tablice(float tab[], int rozmiar) {
    printf("Tab = [ ");
    for (int i = 0; i < rozmiar; i++) {
        printf("%.1f", tab[i]);
        if (i < rozmiar - 1) {
            printf("  ");
        }
    }
    printf(" ]\n");
}
//Funkcja zliczajaca ilośc liczb dodatnich i ujemnych ich sumę i średnia
void analiza_tablicy(float tab[], int rozmiar){
    float suma_dodatnich=0,suma_ujemnych=0;
    int dodatnie=0,ujemne=0;
    for(int i = 0; i<rozmiar;i++){
        if(tab[i]>0){
            suma_dodatnich+=tab[i];
            dodatnie++;
        }else if(tab[i]<0){
            suma_ujemnych+=tab[i];
            ujemne++;
        }
    }
    printf("Ilosc liczb dodatnich: %d\n", dodatnie);
    printf("Ilosc liczb ujemnych: %d\n", ujemne);
    printf("Suma liczb dodatnich: %.2f\n", suma_dodatnich);
    printf("Suma liczb ujemnych: %.2f\n", suma_ujemnych);

    if (dodatnie > 0) {
        printf("Srednia dodatnich: %.2f\n", suma_dodatnich / dodatnie);
    } else {
        printf("Brak liczb dodatnich do obliczenia sredniej.\n");
    }

    if (ujemne > 0) {
        printf("Srednia ujemnych: %.2f\n", suma_ujemnych / ujemne);
    } else {
        printf("Brak liczb ujemnych do obliczenia sredniej.\n");
    }
}

//sprawdzanie uporzadkowania przez warunki
void sprawdz_uporzadkowanie(float tab[], int rozmiar) {
    int rosnaco = 1, niemalejaco = 1, rowne = 1, nierosnaco = 1, malejaco = 1;

    for (int i = 0; i < rozmiar-1; i++) {
        if(tab[i]<tab[i+1])
            rosnaco++;
        if(tab[i]<=tab[i+1])
            niemalejaco++;
        if(tab[i]==tab[i+1])
            rowne++;
        if(tab[i]>=tab[i+1])
            nierosnaco++;
        if(tab[i]>tab[i+1])
            malejaco++;
    }

    if (rosnaco==rozmiar) {
        printf("Wszystkie elementy tablicy sa uporzadkowane rosnaco.\n");
    } else if (niemalejaco==rozmiar) {
        printf("Wszystkie elementy tablicy sa uporzadkowane niemalejaco.\n");
    } else if (rowne==rozmiar) {
        printf("Wszystkie elementy tablicy maja jednakowa wartosc.\n");
    } else if (nierosnaco==rozmiar) {
        printf("Wszystkie elementy tablicy sa uporzadkowane nierosnaco.\n");
    } else if (malejaco==rozmiar) {
        printf("Wszystkie elementy tablicy sa uporzadkowane malejaco.\n");
    } else {
        printf("Elementy tablicy sa nie uporzadkowane.\n");
    }
}
void zadanie_2(float tab[], int rozmiar){// funkcja losująca elementy tablicy
    int najmniejszy_element,najwiekszy_element, losowa_calkowita, wybor;
    double losowa_rzeczywista;
    cout<<"Podaj najmniejszy element zakresu losowanych liczb: ";
    cin>>najmniejszy_element;
    cout<<"Podaj najwiekszy element zakresu losowanych liczb: ";
    cin>>najwiekszy_element;

    srand(time(NULL));
    for(int i=0;i<rozmiar;i++){
        start://skok
        // wybór czy losowana liczba ma byc liczba calkowita czy rzeczywista
        cout<<"\nLosowanie liczby calkowitej wpisz: 1. Losowanie liczby rzeczywistej wpisz 2. :";
        cin>>wybor;
        if(wybor ==  1){
            losowa_calkowita = najmniejszy_element + rand()%(najwiekszy_element - najmniejszy_element +1);
            tab[i]=losowa_calkowita;
            cout<<"Wylosowana liczba to: "<<losowa_calkowita<<endl;
        }else if(wybor == 2){
            losowa_rzeczywista = najmniejszy_element + (najwiekszy_element - najmniejszy_element)*rand()/((double)RAND_MAX);
            tab[i]=losowa_rzeczywista;
            cout<<"Wylosowana liczba to: "<<losowa_rzeczywista<<endl;
        }else{
            cout<<"Nieprawidlowy wybor";
            goto start;// ochrona przed zlym wyborem typu liczby aby nie pominac wpisac do tablicy
        }


    }
}
//funkcja sortująca
void zadanie_3(float tab[], int rozmiar){
    float pomocnicza;

    for(int i=0; i<rozmiar-1;i++)
    {
        for(int j=i;j>=0;j--)
        {
            if(tab[j]>tab[j+1])
            {
                pomocnicza=tab[j];
                tab[j]=tab[j+1];
                tab[j+1]=pomocnicza;
            }
            else
                break;
        }
    }
    cout<<"\nTablica posortowana pomyslnie\n";
}
//Zliczanie liter dopóki nie wciśnie się klawisza ESC
void zadanie_4(){
        int tab[26], licznik=0;
        char znak;

        //Wypelnianie tablicy zerrami
        for(int i=0;i<26;i++){
            tab[i]=0;
        }
        cout<<"Wczytuj znaki do poki nie nacisniesz ESC\n";
        cout<<"Wpisuj litery: ";
        //pobieranie znaku z klawiatury dopoki nie jest to ESC
        do{
            znak=getch();
            licznik++;
            if (znak>='A' && znak <='Z'){//alfabet dużych liter
                tab[int(znak)-'A']+=1;
            }
            if(znak>='a' && znak <= 'z'){//alfabet malych liter
                tab[int(znak)-'a']+=1;
            }

        }while(znak!=27);//dopoki nie nacisnie sie ESC
        cout<<"Wpisano lacznie "<<licznik<<" znakow"<<endl;

        //wypisywanie danych
        int alfabet=65;
        for(int j=0;j<26;j++){
            cout<<"litera "<<char(alfabet)<<" "<<tab[j]<<" ";
            if(tab[j]>0){
                for(int x=0;x<tab[j];x++){
                    cout<<"#";
                }
            }
            cout<<endl;
            alfabet++;

        }
        
}

int main() {
    cout<<"Autor: Mateusz Lukasiewicz (Czwartek/P 15:15) \n\n";//Mateusz Łukasiewicz
    int numer_zadania;
    float tablica[ROZMIAR];

    do {
        printf("\nMENU:\n");
        printf("1. Wczytaj tablice\n");
        printf("2. Wypisz tablice\n");
        printf("3. Analiza liczb ujemnych i dodatnich w tablicy\n");
        printf("4. Sprawdz uporzadkowanie tablicy\n");
        printf("5. Wypelnianie tablicy losowymi liczbami z przedzialu (Zadanie 2)\n");
        printf("6. Sortowanie tablicy (Zadanie 3)\n");
        printf("7. Zadanie 4\n");
        printf("0. Zakoncz program\n");
        printf("Wybierz opcje: ");
        scanf("%d", &numer_zadania);

        switch (numer_zadania) {
            case 0:printf("Program zakonczony.\n");break;
            case 1:wczytaj_tablice(tablica,ROZMIAR);break;
            case 2:wypisz_tablice(tablica,ROZMIAR);break;
            case 3:analiza_tablicy(tablica,ROZMIAR);break;
            case 4: sprawdz_uporzadkowanie(tablica,ROZMIAR);break;
            case 5:zadanie_2(tablica, ROZMIAR);break;
            case 6:zadanie_3(tablica,ROZMIAR); break;
            case 7:zadanie_4(); break;

            default:printf("Nieprawidlowy wybor. Sprobuj ponownie.\n");break;
        }
    } while (numer_zadania!= 0);
    return 0;
}
