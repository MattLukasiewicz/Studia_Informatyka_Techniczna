/*
Autor:  Mateusz Łukasiewicz
Grupa:  Czwartek/P 15:15
Temat:  Laboratoria 5
Data:   25.01.2024 r.
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Inicjalizacja dynamicznej tablicy dwuwymiarowej.
// Funkcję należy wywołać na początku programu.
void InitTab(char **&wsk);
// Dodawanie nowego imienia do tablicy dynamiczne.j
void AddName(char *buf, char **&wsk);
// Usuwanie imienia znajdującego się w tablicy na pozycji nr,
void RemoveName(int nr, char **&wsk);
//Uusuwanie z tablicy imienia, wprowadzonego z klawiatury
void RemoveNameByKeyboard(char *buf, char **&wsk);
// drukowanie na ekranie wszystkich imion
void PrintAllNames(char **wsk);
// drukowanie na ekranie imion rozpoczynających wskazaną literą
void PrintNames(char first_letter, char **wsk);
// sortowanie imion zawartych w tablicy według alfabetu
void SortAlphabet(char **wsk);
// sortowanie imion zawartych w tablicy według długości
void SortLength(char **wsk);


int main(){
    printf("Autor: Mateusz Lukasiewicz (Czwartek/P 15:15) \n\n");//Mateusz Łukasiewicz
    char **WSK;
    char buf[81];

    InitTab(WSK);


    int numer_zadania;
    do {
        printf("Menu:\n");
        printf("0. Wyjscie\n");
        printf("1. Dodaj imie\n");
        printf("2. Wyswietl wszystkie imiona\n");
        printf("3. Wyswietl imiona zaczynajace sie od litery\n");
        printf("4. Usun imie z pozycji\n");
        printf("5. Usun imie wprowadzone z klawiatury\n");
        printf("6. Posortuj imiona alfabetycznie\n");
        printf("7. Posortuj imiona wedlug dlugosci\n");
        printf("Wybierz opcje: ");
        scanf("%d", &numer_zadania);

        switch (numer_zadania) {
            case 0:
                // Zwolnienie pamięci przed wyjściem
                for (int i = 0; WSK != NULL && WSK[i] != NULL; ++i) {
                    free(WSK[i]);
                }
                free(WSK);
                printf("Program zakoczony.\n");
                break;
            case 1:
                printf("Wprowadz imie:  ");
                fflush(stdin);
                fgets (buf, 81, stdin);
                AddName(buf, WSK);
                break;
            case 2:
                PrintAllNames(WSK);
                break;
            case 3:
                printf("Podaj litere: ");
                char pierwsza_litera;
                scanf(" %c", &pierwsza_litera);  //na jaka litere wyswietlic imie
                PrintNames(pierwsza_litera, WSK);
                break;
            case 4:
                printf("Podaj indeks imienia do usuniecia(indeks>=1): ");
                int indeks;
                scanf("%d", &indeks);
                RemoveName(indeks, WSK);
                break;
            case 5:
                printf("Podaj imie ktore chcesz usunac: ");
                fflush(stdin);
                fgets (buf, 81, stdin);
                RemoveNameByKeyboard(buf, WSK);
                break;
            case 6:
                SortAlphabet(WSK);
                break;
            case 7:
                SortLength(WSK);
                break;
            default:printf("Nieprawidlowy wybor. Sprobuj ponownie.\n");break;
        }
    } while (numer_zadania!=0);

    return 0;
}
// Inicjalizacja dynamicznej tablicy dwuwymiarowej.
void InitTab(char **&wsk){
    wsk=(char**) malloc(sizeof(char *));
    *wsk = NULL;
}
// Dodawanie nowego imienia do tablicy dynamicznej
void AddName(char *buf, char **&wsk) {
    int pozycja = 0;
    while (wsk[pozycja] != NULL) {
        pozycja++;
    }

    wsk = (char **)realloc(wsk, (pozycja + 2) * sizeof(char *));
    wsk[pozycja+1]=NULL;

    wsk[pozycja]=(char*)malloc(sizeof(char)*(strlen(buf)+1));
    strcpy(wsk[pozycja], buf);
    /* int pozycja=0;while(wsk[pozycaja]!=NULL)poz++;
     * char tmpbuf=strdup(buf);
     * if(tmpbuf==NULL){
     * printf("Brak pamieci");
     * return 1;
     * }
     * char **tmpwsk=(char**)realloc(wsk,(pozycja+2)*sizeof(char*));
     * if(tmpwsk==NULL){
     * tmpwsk[pozycja]=tmpbuff
     * tmpwsk[pozycja+1]=NULL;
     * wsk=tmpwsk;
     * return 0;
     * }
     *
     * free(tmpbuf);printf("Brak pamieci");return 2;
     * }
     */
}

// drukowanie na ekranie wszystkich imion
void PrintAllNames(char **wsk){
    if (wsk[0] == NULL) {
        printf("Tablica jest pusta, nie ma czego wypisywac\n");
        return;
    }
    int pozycja = 0;
    while (wsk[pozycja] != NULL) {
        printf("%d. %s\n", pozycja + 1, wsk[pozycja]);
        pozycja++;
    }
    /*
    int i=1;
    while(*wsk!=NULL) {
        printf("%d. %s\n",i++, *wsk);
        wsk++;
    }


     */
}



// Usuwanie imienia znajdującego się w tablicy na pozycji nr,
void RemoveName(int nr, char **&wsk){
    //nr musi byc liczba wieksza od zera tak jak normalnie sie numeruje
    // nie jak w informatyce ponieważ w funkcji PrintAllNames
    // wyswietlam liste imion z numeracja od 1 dla wygody normalnego uzytkownika
    if (wsk[0] == NULL) {
        printf("Tablica jest pusta, nie ma czego usuwac\n");
        return;
    }
    int pozycja =0;
    while(wsk[pozycja]!=NULL){
        pozycja++;
    }
    if(nr>pozycja || nr<1){
        printf("Nieprawidlowy numer imienia\n");
        return;
    }else{
        free(wsk[nr-1]);
        for(int i=(nr-1);i<pozycja;i++){
            wsk[i]=wsk[i+1];
        }
        wsk=(char**)realloc(wsk,(pozycja*sizeof (char*)));
        //printf("Usunieto imie znajdujace sie na %d pozycji w tablicy\n",nr);
    }
}
// drukowanie na ekranie imion rozpoczynających wskazaną literą
void PrintNames(char first_letter, char **wsk){
    if (wsk[0] == NULL) {
        printf("Tablica jest pusta, nie ma czego wypisywac\n");
        return;
    }
    int i=0;
    while(wsk[i]!=NULL){
        if(wsk[i][0]==first_letter){
            printf("%d. %s\n",i+1,wsk[i]);
        }
        i++;
    }
}

//Uusuwanie z tablicy imienia, wprowadzonego z klawiatury
void RemoveNameByKeyboard(char *buf, char **&wsk){
    //*wsk=wsk[i]=wartość wskaznika wsk=adres buf=tekst *buf=adres
    //printf("%s , %d  wsk %s , %d",buf,*buf,*wsk,wsk);
    if (wsk[0] == NULL) {
        printf("Tablica jest pusta, nie ma czego usuwac\n");
        return;
    }
    int i=0;
    int ile=0;
    /*int removeAll;
    printf("Czy chcesz usunac jedno imie (1) czy wszystkie (0)? ");
    scanf("%d",removeAll);
     */
    while(wsk[i]!=NULL){
        if(strcmp(wsk[i],buf)==0){
            RemoveName(i+1,wsk);
            ile++;
            /*
            if (removeAll==1) {
                return; // Jeśli removeAll jest ustawione na 1, to usuwamy tylko jedno imię
            }
             */
        }else{
            i++;
        }


    }
    if (ile==0) {
        printf("Nie znaleziono imienia '%s' w tablicy.\n", buf);
    }

}


// sortowanie imion zawartych w tablicy według alfabetu
void SortAlphabet(char **wsk){
    if (wsk[0] == NULL) {
        printf("Tablica jest pusta, nie można jej posortować\n");
        return;
    }else{
        int ile=0;
        while(wsk[ile]!=NULL){
            ile++;
        }
        for(int i=0;i<(ile-1);i++) {
            for (int j = 0; j < ile-i-1; j++) {
                if(strcmp(wsk[j],wsk[j+1])>0){
                    char *temp=wsk[j];
                    wsk[j]=wsk[j+1];
                    wsk[j+1]=temp;
                }
            }
        }
        printf("Tablica zostala posortowana pomyslnie\n");
    }


}

// sortowanie imion zawartych w tablicy według długości
void SortLength(char **wsk){
    if (wsk[0] == NULL) {
        printf("Tablica jest pusta, nie można jej posortować\n");
        return;
    }else{
        int ile=0;
        while(wsk[ile]!=NULL){
            ile++;
        }
        for(int i=0;i<(ile-1);i++) {
            for (int j = 0; j < ile-i-1; j++) {
                if(strlen(wsk[j])>strlen(wsk[j+1])){
                    char *temp=wsk[j];
                    wsk[j]=wsk[j+1];
                    wsk[j+1]=temp;
                }
            }
        }
        printf("Tablica zostala posortowana pomyslnie\n");
    }
}