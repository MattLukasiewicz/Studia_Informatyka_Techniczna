import java.util.Scanner;

public class zadanie_2{
    //Mateusz Lukasiewicz 281035 gr13 23.10.2024
    //3433333333333333333
    //Funkcja do robienia zadania
    public static void kolejna(int n){
        boolean warunek=true;
        int pomoc = 0;
        int licznik=0;

        while(warunek&&licznik<1000){//warunek zakończenia programu po 1000 liczbie ciągu
            if(n > (Integer.MAX_VALUE - 1) / 3){//sprawdzenie, czy operacja n * 3 + 1 nie przekroczy zakresu
                System.out.println("Przekroczenie zakresu możliwej operacji");
                warunek=false;
                break;
            }else if(n==1){//Warunek koncowy
                warunek=false;
                System.out.println("Warunek koncowy spelniony n=1");
                break;
            } else if (n%2==0) {//Operacja  i warunek jesli n jest liczba parzysta
                pomoc=n;
                n=n/2;
                System.out.println(pomoc+",parzysta,"+n);
            }else if(n%2==1){//Operacja i warunek jesli n jest liczba nieparzysta
                pomoc=n;
                n = 3 * n + 1;
                System.out.println(pomoc + ",nieparzysta," + n);

            }

            licznik++;

        }
        //Wyswietlanie ile operacji
        System.out.println("Ilość operacji: "+licznik);
        if (licznik == 999) {
            System.out.println("Osiągnięto maksymalną liczbę 1000 iteracji. Program zakończony.");

        }


    }
    public static void main(String[] args){
        //Program pobiera argument za pomoca funkcji scanner
        //System.out.print("# zadanie_2 ");
        //Scanner scanner = new Scanner(System.in);


        try {
            //int n = scanner.nextInt();
            int n = Integer.parseInt(args[0]);
            if (n < 1) {//Sprawdzanie czy dane wejsciowe sa poprawne
                System.out.println("Blad danych");

            } else if (n <= Integer.MAX_VALUE) {//Wywołanie funkcji
                kolejna(n);
            } else {
                System.out.println("Blad danych wyjscie poza zakres zmiennej(int)");
            }
        }catch(Exception e){
            System.out.println("Blad");
        }


        //scanner.close();

    }

}

