/*
Autor:  Mateusz Łukasiewicz
Grupa:  Czwartek/P 15:15
Temat:  Laboratoria 2
Data:   26.11.2023 r.
*/
#include <stdio.h>
#include <iostream>
#include <ctime>
#include <cmath>

using namespace  std;
void zadanie_1(){
    int wiersze, kolumny;
    printf("Podaj liczbe wierszy: ");
    scanf("%d",&wiersze);
    printf("Podaj liczbe kolumn: ");
    scanf("%d",&kolumny);
    //warunki wielkości
    if(wiersze>20||wiersze<1){
        wiersze=20;
    }
    if(kolumny>25||kolumny<1){
        kolumny =25;
    }
    //tabela
    printf("    |");
    for(int i=1;i<=kolumny;i++)
        printf("%4d", i);
    printf("\n-----|");
    for(int i=1;i<=kolumny;i++)
        printf("----");
    printf("\n");


    //wypełnianie tabeli
    for(int i=1;i<=wiersze;i++){
        for (int j = 1; j <= kolumny; j++) {
            if(j==1){
                printf("%4d |",i);
            }printf("%4d",i*j);
        }printf("\n");
    }
}
void zadanie_2() {
    unsigned long suma = 0,suma_2=0,liczba_1,liczba_2;
    printf("Podaj duza liczbe calkowita dodatnia: ");
    scanf("%ld",&liczba_1);
    liczba_2=liczba_1;
    //warunek konieczny
    if (liczba_1 < 0) {
        printf("Podana liczba musi byc dodatnia.\n");
        //liczenie dla liczby
    } else {
        while (liczba_1 != 0) {
            suma += liczba_1 % 10;
            liczba_1 /= 10;
        }
        printf("Suma cyfr liczby wynosi: %ld\n",suma);
        //liczenie dla liczby w systemie dwojkowym
        while(liczba_2!=0){
            suma_2+=liczba_2%2;
            liczba_2/=2;
        }
        printf("Suma cyfr liczby w systemie dwojkowym wynosi: %ld\n",suma_2);
    }

}

void zadanie_3(){
    double S1=0,l=1, wyraz=1,znak=1;//S1 i S2: Zmienne do przechowywania sumy szeregów.l: Numer iteracji, który jest wykorzystywany do obliczeń w szeregach. wyraz: Wartość aktualnego wyrazu w szeregach.
    double S2=1;
    double eps;

    cout<<"Podaj dokladnosc obliczen (eps < 0.1): ";
    cin>>eps;
    if(eps>=0.1){
        cout<<"Bledna dokladnosc";
    }else{
        //obliczanie pierwszego szeregu
        while(fabs(wyraz)>=eps){
            S1 +=wyraz;
            znak*=-1;
            l+=2;
            wyraz=znak/l;
        }
        l=1;
        wyraz=1;
        //obliczanie drugiego szeregu
        while(wyraz>=eps){
            S2+=wyraz;
            l+=1;
            wyraz*=1/l;

        }

        cout<< "Wartosc sumy szeregu S1 z zadana dokladnoscia: " << 4*S1 <<endl;//korzystanie z wlasnosci szeregu
        cout<< "Wartosc sumy szeregu S2 z zadana dokladnoscia: " <<S2 ;

    }

}
void zadanie_4(){
    int liczba,min,maxym,ile,suma_ujemnych=0,suma_dodatnich=0,ilosc_ujemnych=0,ilosc_dodatnich=0;
    //Zakres liczb
    cout<<"Podaj najmniejsza liczbe: ";
    cin >> min ;
    cout<<"Podaj najwieksza liczbe:  ";
    cin>> maxym;
    //Ilosc liczb
    cout<<"Podaj ilosc liczb jakie mam wylosowac: ";
    cin>>ile;
    //uruchamianie generatora
    srand(time(NULL));
    for(int i=0;i<ile;i++) {
        liczba = min + rand() % ( maxym - min + 1);
        cout<<liczba<<"\n";
        if (liczba<0){
            suma_ujemnych+=liczba;
            ilosc_ujemnych++;
        }else{
            suma_dodatnich+=liczba;
            ilosc_dodatnich++;
        }

    }
    //wypisywanie wynikow
    if(suma_dodatnich!=0){
        cout<<"Suma liczb dodatnich jest rowna "<<suma_dodatnich<<" Ilosc liczb dodatnich wynosi "<<ilosc_dodatnich<<"  Srednia liczb dodatnich jest rowna "<<float(suma_dodatnich)/ilosc_dodatnich<<"\n";
    }else{
        cout<<"Brak wylosowanych liczb dodatnich";
    }
    if(suma_ujemnych!=0) {
        cout << "Suma liczb ujemnych jest rowna " << suma_ujemnych << " Ilosc liczb ujemnych wynosi " << ilosc_ujemnych<< "  Srednia liczb ujemnych jest rowna " << float(suma_ujemnych) / ilosc_ujemnych << "\n";
    }else{
        cout<<"Brak wylosowanych liczb ujemnych";
    }
}
void zadanie_5() {
    const char RED_TEXT[] = "\033[31m";
    const char GREEN_TEXT[] = "\033[32m";
    const char DEFAULT_TEXT[] = "\033[0m";

    int szerokosc_szachownicy,wysokosc_szachownicy, rozmiar_pola_szerokosc, rozmiar_pola_wysokosc;
    cout << "Podaj szerokosc szachownicy :  ";
    cin >> szerokosc_szachownicy;
    cout << "Podaj wysokosc szachownicy :  ";
    cin >> wysokosc_szachownicy;
    cout << "Podaj rozmiar pojedynczego pola (szerokosc): ";
    cin >> rozmiar_pola_szerokosc;
    cout << "Podaj rozmiar pojedynczego pola (wysokosc): ";
    cin >> rozmiar_pola_wysokosc;

    for (int i = 0; i < wysokosc_szachownicy * rozmiar_pola_wysokosc; ++i) {//wiersze szachownicy
        for (int j = 0; j < szerokosc_szachownicy * rozmiar_pola_szerokosc; ++j) {//kolumny szachownicy

            // Zmiana koloru pola
            if ((i / rozmiar_pola_wysokosc + j / rozmiar_pola_szerokosc) % 2 == 0) {//Warunek if sprawdza, czy suma indeksów aktualnej kolumny i wiersza (po podziale przez odpowiednie rozmiary pól) jest liczbą parzystą czy nieparzystą.
                cout << RED_TEXT;
            } else {
                cout << GREEN_TEXT;
            }

            // Wypisywanie pola
            cout << '#';
        }

        cout << DEFAULT_TEXT << endl;//przywrocenie domyslnego kolor tekstu
    }
}


int main(){
    cout<<"Autor: Mateusz Lukasiewicz (Czwartek/P 15:15) \n\n";//Mateusz Łukasiewicz
    int numer_zadania;



    do{
        cout<<"\nMenu\n";
        cout<<"1. Zadanie 1\n";
        cout<<"2. Zadanie 2\n";
        cout<<"3. Zadanie 3\n";
        cout<<"4. Zadanie 4\n";
        cout<<"5. Zadanie 5\n";
        cout<<"0. Zakoncz program\n";
        cout<<"Podaj numer zadania, ktore chcesz wyswietlic (1,2,3,4,5): ";
        cin>>numer_zadania;
        switch(numer_zadania) {
            case 0:cout<<"Koniec programu";break;
            case 1:zadanie_1();break;
            case 2:zadanie_2();break;
            case 3:zadanie_3();break;
            case 4:zadanie_4();break;
            case 5:zadanie_5();break;
            default:cout<<"Nieprawidlowy numer zadania";break;
        }
    }while(numer_zadania!=0);
    return 0;
}


