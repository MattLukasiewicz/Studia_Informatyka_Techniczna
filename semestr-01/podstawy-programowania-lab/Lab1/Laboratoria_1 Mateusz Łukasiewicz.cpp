/*
Autor:  Mateusz £ukasiewicz
Grupa:  Czwartek/P 15:15
Temat:  Laboratoria 1
Data:   19.102023 r.
*/
#include <iostream>
#include <stdio.h>
#include <math.h>
#include <algorithm>
using namespace std;
void zadanie_1(){
    double a,b,c,delta,x1,x2;
    cout<<"Zadanie 1: Sprawdza czy jest po poprawne rownanie kwadratowe i jesli ma ono pierwiastki to je oblicza\n";
    cout<<"Podaj wspolczynnik a: ";
    cin>>a;
    cout<<"\nPodaj wspolczynnik b: ";
    cin>>b;
    cout<<"\nPodaj wspolczynnik c: ";
    cin>>c;
    cout<<"\n";
    if(a!=0){
        delta=(b*b)-(4*a*c);
        //cout<<delta;
        if(delta>=0){
            if(delta==0){
                x1=-b/(2*a);
                cout<<"Jedyny pierwiastek funkcji x0=:"<<x1;
            }else{
                x1=(-b-sqrt(delta))/(2*a);
                x2=(-b+sqrt(delta))/(2*a);
                cout<<"Podane rownanie kwadratowe ma 2 pierwiastki: x1="<<x1<<" x2="<<x2;
            }
        }else{
            cout<<"Brak pierwiastkow rzeczywistych";
        }

    }else{
        cout<<"To nie jest rownanie kwadratowe";

    }


}
void zadanie_2(){

    int dzien,miesiac,rok;
    cout<<"Zadanie 2 sprawdza czy podana przez uzytkownika data jest poprawna\n";    cout<<"Podaj dzien: ";
    cin>>dzien;
    cout<<"\nPodaj miesiac: ";
    cin>>miesiac;
    cout<<"\nPodaj rok: ";
    cin>>rok;
    cout<<"\nFormat daty (dd/mm/yyyy): "<<dzien<<" "<<miesiac<<" "<<rok<<"\n";
    if(miesiac==1||miesiac==3||miesiac==5||miesiac==7||miesiac==8||miesiac==10||miesiac==12){
        if(dzien>0&&dzien<32&&rok>=0){
            cout<<"data poprawna";
        }else{
            cout<<"data niepoprawna";
        }

    }else if(miesiac==4||miesiac==6||miesiac==9||miesiac==11){

        if(dzien>0&&dzien<31){
            cout<<"data poprawna";
        }else{
            cout<<"data niepoprawna";
        }
    }else if(miesiac==2){
        if((rok % 4 == 0 && rok % 100 != 0) || rok % 400 == 0){
            if(dzien>0&&dzien<=29){
                cout<<"Data poprawna rok przestepny";
            }else{
                cout<<"Data niepoprawna";
            }

        }else{
            if(dzien>0&&dzien<29){
                cout<<"Data poprawna rok NIE przestepny";
            }else{
                cout<<"Data niepoprawna";
            }

        }

    }

}
void zadanie_3(){
    float a,b,c;
    cout<<"Podaj trzy liczby dodatnie, ktore beda oznaczac dlugosci trzech odcinkow\n";
    cin>>a>>b>>c;
    if(a>0&&b>0&&c>0){
        if(a<b){
            swap(a,b);

        }
        if(a<c){
                swap(a,c);

        }
        //cout<<a<<b<<c;
        if(a<(b+c)){
            cout<<"Poprawne dane, z podanych odcinkow mozna utworzyc trojkat\n";
            if(a==b&&a==c){
                cout<<"Trojkat jest rownoramienny, rownoboczny i ostrokatny\n";
            }else if(a==b||a==c||b==c){
                if((a*a)==(b*b)+(c*c)){
                    cout<<"Trojkat jest równoramienny prostokatny";
                }else if((a*a)>(b*b)+(c*c)){
                    cout<<"Trojkat jest rownoramienny rozwartokatny";
                }else{// warunek trojkata ostrokatnego if((a*a)<(b*b)+(c*c))
                    cout<<"Trojkat jest rownoramienny ostrokatny";
                }
            }else if((a*a)==(b*b)+(c*c)){
                cout<<"Trojkat jest prostokatny";
            }else if((a*a)>(b*b)+(c*c)){
                cout<<"Trojkat jest rozwartokatny";
            }else{// warunek trojkata ostrokatnego if((a*a)<(b*b)+(c*c))
                cout<<"Trojkat jest ostrokatny";
            }
        }else{
            cout<<"Trojkat jest niepoprawny";
        }

    }else{
        cout<<"Dane są niepoprawne, z podanych odcinkow nie mozna utworzyc trojkat\n";
    }
}
void zadanie_4(){
    float minimalny,maksymalny;
    int liczba_wierszy;
    printf("Zadanie 4 oblicza promien obwod kola i pole kola dla podanych wartosci\n");
    printf("Podaj minimalny promien: ");
    scanf("%f",&minimalny);
    printf("Podaj maksymalny promien: ");
    scanf("%f",&maksymalny);
    printf("Podaj liczbe wierszy: ");
    scanf("%d",&liczba_wierszy);
    if (minimalny >= maksymalny || liczba_wierszy <= 0) {
        printf("Bledne dane wejsciowe.\n");
        return;
    }
    printf("=========================================\n");
    printf("| %s | %s | %s | %s |\n","Lp","promien","obwod kola", "pole kola");
    printf("=========================================\n");
    double krok = (maksymalny - minimalny) / (liczba_wierszy - 1);
    for(int i=0;i<liczba_wierszy;i++)
    {   double promien = minimalny + i * krok;
        double obwod_kola = 2.0 * M_PI * promien;
        double pole = M_PI * promien * promien;
        printf("| %2d |  %6.2f | %10.2f | %9.2f |\n", i + 1, promien, obwod_kola, pole);
    }
    printf("=========================================\n\n");
}
int main()
{
    printf("Autor: Mateusz Łukasiewicz (Czwartek/P 15:15) \n\n");
    //zadanie_1();
    //zadanie_2();
    //zadanie_3();
    //zadanie_4();
    int nr_zadania;
    printf("Podaj numer zadania, ktore chcesz wyswietlic: \n");
    scanf("%d",&nr_zadania);
    if(nr_zadania>4||nr_zadania<1){
        printf("Bledny numer zadania");
    }else{
        switch(nr_zadania){
            case 1: zadanie_1(); break;
            case 2: zadanie_2(); break;
            case 3: zadanie_3(); break;
            case 4: zadanie_4(); break;
            default:
                printf("Nie ma zadania o takim numerze\n");
                break;
        }

    }



return 0;
}
