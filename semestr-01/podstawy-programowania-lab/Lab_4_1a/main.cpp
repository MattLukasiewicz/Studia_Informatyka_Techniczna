/*
Autor:  Mateusz Łukasiewicz
Grupa:  Czwartek/P 15:15
Temat:  Laboratoria 3
Data:   24.12.2023 r.
*/
#include <iostream>
#include <stdio.h>
#include <conio.h>

using namespace std;
void CzytajRownanie(float &a, float &b, float &c);
float ObliczWyznacznik(float p1, float p2, float p3, float p4);
int ObliczRozwiązanie(float a1, float b1, float c1, float a2, float b2, float c2, float &x, float &y);
void WypiszRozwiązanie( int N, float x, float y);

int main() {
    float A1, B1, C1; // pierwsze równanie,
    float A2, B2, C2; // drugie równanie,
    float X, Y; // rozwiązanie,
    int N; // liczba rozwiazan.
    CzytajRownanie(A1, B1, C1);
    CzytajRownanie(A2, B2, C2);
    N = ObliczRozwiązanie(A1, B1, C1, A2, B2, C2, X, Y);
    WypiszRozwiązanie( N, X, Y);
    getch();
    return 0;
}
void CzytajRownanie(float &a, float &b, float &c){
    printf("Podaj wspolczynniki rownania liniowego\n");
    printf("Ax +Bx = C\n");
    printf("Podaj wartosci wspolczynnikow A, B, C : ");
    scanf(" %f %f %f",&a,&b,&c);
    printf("Rownanie liniowe ma postac: ");
    printf("%.2fx + %.2fy = %.2f\n",a,b,c);

}
float ObliczWyznacznik(float p1, float p2, float p3, float p4){
    return (p1*p2)-(p3*p4);
}
int ObliczRozwiązanie(float a1, float b1, float c1, float a2, float b2, float c2, float &x, float &y){
    float W= ObliczWyznacznik(a1,b2,a2,b2);
    float Wx= ObliczWyznacznik(c1,b1,c2,b2);
    float Wy = ObliczWyznacznik(a1,c1,a2,c2);
    if(W!=0){
        x=Wx/W;
        y=Wy/W;
        return 1;
    }else if(W==0 && Wx==0 && Wy==0){
        return 2;
    }else{
        return 0;
    }
}
void WypiszRozwiązanie( int N, float x, float y) {
    if(N==0){
        printf("Uklad rownan jest sprzeczny tzn. nie ma zadnych rozwiazan");
    }else if(N==1){
        printf("Uklad ma dokladnie jedno rozwiazanie: ");
        printf("Rozwiazaniem ukladu jest para liczb: x=%.2f , y=%.2f ",x,y);
    }else if(N==2){
        printf("Podany uklad rownan ma nieskonczenie wiele rozwiazan ");
    }
}