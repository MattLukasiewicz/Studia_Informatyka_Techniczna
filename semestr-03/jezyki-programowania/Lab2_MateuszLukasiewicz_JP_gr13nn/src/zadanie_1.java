import java.util.ArrayList;
import java.util.List;
//Mateusz Lukasiewicz 281035 gr13 06.11.2024
public class zadanie_1{
    // Funkcja generująca listę samochodów za pomocą pętli
    public static List<Samochod> generujSamochody(int liczbaSamochodow) {
        List<Samochod> samochody = new ArrayList<>();
        for (int i = 0; i < liczbaSamochodow; i++) {
            Samochod samochod = Samochod.stworzSamochod(); // Tworzy jeden samochód
            samochody.add(samochod); // Dodaje samochód do listy
        }
        return samochody;
    }
    // Wysietla pomoc jesli argumentem jest -h
    public static  void wyswietlPomoc() {
        System.out.println("zadanie_1 N Metoda Kryterium [ROK]");
        System.out.println("N – liczba samochodów (liczba całkowita)");
        System.out.println("Metoda – Zwyczajnie/Wyjatkowo");
        System.out.println("Kryterium – jedno z czterech zapytań:");
        System.out.println("najstarszy");
        System.out.println("nie starszy niż ROK");
        System.out.println("nie młodszy niż ROK");
        System.out.println("najmłodszy");
        System.out.println("ROK – rok jako liczba naturalna (opcjonalnie dla odpowiednich kryteriów)");
    }
    // Funkcja wyświetlająca listę samochodów
    public static  void wyswietlListe(List<Samochod> samochody) {
        for (Samochod samochod : samochody) {
            System.out.println(samochod);
        }
    }
    /*
    // Funkcja wyświetlająca listę samochodów
    public static void wyswietlListke(List<Samochod> samochody) {
       samochody.forEach(System.out::println);
    }
     */


    //Kryteria wyszukiwania
    public static List<Samochod> Najstarszy_samochod(List<Samochod> samochody){
        List<Samochod> najstarsze_samochody = new ArrayList<>();
        // Jak jest pusta to zwraca pusta
        if(samochody.isEmpty()){
            return najstarsze_samochody;
        }
        //Znajdujemy najstarszy rocznik
        int najstarszy_rocznik = samochody.get(0).rocznik;//Zakladamy ze pierwszy samochod jest na poczatku najstarszy
        for (Samochod samochod:samochody){
            if(samochod.rocznik<najstarszy_rocznik){
                najstarszy_rocznik=samochod.rocznik;
            }
        }
        //Dodajemy do listy wszystkie najstarsze samochody(moze byc pare o tym samym roczniku)
        for(Samochod samochod:samochody){
            if(samochod.rocznik==najstarszy_rocznik){
                najstarsze_samochody.add(samochod);
            }
        }
        return najstarsze_samochody;
    }
    public static List<Samochod> Najmlodszy_samochod(List<Samochod> samochody){
        List<Samochod> najmlodsze_samochody = new ArrayList<>();
        // Jak jest pusta to zwraca pusta
        if(samochody.isEmpty()){
            return najmlodsze_samochody;
        }
        //Znajdujemy najmlodszy_rocznik
        int najmlodszy_rocznik = samochody.get(0).rocznik;//Zakladamy ze pierwszy samochod jest na poczatku najmlodszy
        for (Samochod samochod:samochody){
            if(samochod.rocznik>najmlodszy_rocznik){
                najmlodszy_rocznik=samochod.rocznik;
            }
        }
        //Dodajemy do listy wszystkie najmlodszy samochody(moze byc pare o tym samym roczniku)
        for(Samochod samochod:samochody){
            if(samochod.rocznik==najmlodszy_rocznik){
                najmlodsze_samochody.add(samochod);
            }
        }
        return najmlodsze_samochody;
    }
    public static List<Samochod> Nie_mlodszy_niz(List<Samochod> samochody,int rocznik){
        List<Samochod> nie_mlodszy_niz = new ArrayList<>();
        // Jak jest pusta to zwraca pusta
        if(samochody.isEmpty()){
            System.out.println("Brak samochodów w bazie.");
            return nie_mlodszy_niz;
        }

        //Dodajemy do listy wszystkie nie_mlodszy_niz samochody
        for(Samochod samochod:samochody){
            if(samochod.rocznik>=rocznik){
                nie_mlodszy_niz.add(samochod);
            }
        }
        if (nie_mlodszy_niz.isEmpty()) {
            System.out.println("Brak samochodów spełniających kryterium: rocznik <= " + rocznik);
        }
        return nie_mlodszy_niz;
    }
    public static List<Samochod> Nie_starszy_niz(List<Samochod> samochody, int rocznik) {
        List<Samochod> nie_starszy_niz = new ArrayList<>();

        if (samochody.isEmpty()) {
            System.out.println("Brak samochodów w bazie.");
            return nie_starszy_niz;
        }

        for (Samochod samochod : samochody) {
            //System.out.println("Sprawdzanie rocznika: Samochod " + samochod.rocznik + ", rokk: " + rocznik);
            if (samochod.rocznik <= rocznik) {
                //System.out.println("Dodano samochód: " + samochod);
                nie_starszy_niz.add(samochod);
            }
        }

        if (nie_starszy_niz.isEmpty()) {
            System.out.println("Brak samochodów spełniających kryterium: rocznik <= " + rocznik);
        }

        return nie_starszy_niz;
    }
    public static List<Samochod> Kryterium(String kryterium, List<Samochod> samochody, int rocznik) {
        List<Samochod> wynik = new ArrayList<>();
        switch (kryterium) {
            case "najstarszy":
                wynik=Najstarszy_samochod(samochody);
                break;
            case "najmłodszy":
                wynik=Najmlodszy_samochod(samochody);
                break;
            case "nie starszy niż":
                wynik=Nie_starszy_niz(samochody, rocznik);
                break;
            case "nie mlodszy niż":
                wynik=Nie_mlodszy_niz(samochody, rocznik);
                break;
            default:
                System.out.println("Nieznane kryterium: " + kryterium);
                break;
        }
        return wynik;
    }



    public static void main(String[] args) {

        //Samochod samochod = new Samochod(Marka.POLONEZ , 200.7,1980);
        //System.out.println(samochod.cena);
        /*
        int liczbaSamochodow = 5; // Ustal liczbę samochodów
        List<Samochod> samochody = generujSamochody(liczbaSamochodow);


        // Wyświetl wszystkie wygenerowane samochody
        for (Samochod samochod : samochody) {
            System.out.println(samochod);
        }

         */


        //Początek programu
        if (args.length == 1 && args[0].equals("-h")) {
            wyswietlPomoc();
            return;
        } else if (args.length < 3) {
            System.out.println("Podano zbyt mało argumentów! Użyj -h, aby zobaczyć pomoc.");
            return;
        }
        // Sprawdzanie liczby samochodów
        int liczbaSamochodow = Integer.parseInt(args[0]);
        try {


            // Sprawdzenie, czy liczba jest dodatnia
            if (liczbaSamochodow <= 0) {
                System.out.println("Podaj liczbę dodatnią większą niż 0.");
                return;
            }

        } catch (Exception e) {
            System.out.println("Podano niepoprawną liczbę! Proszę podać liczbę dodatnią w zakresie typu int.");
            return;
        }



        String kryterium = "";


        int rok = 0;

        // Analiza kryterium na podstawie liczby argumentów
        if (args.length == 3) {
            kryterium = args[2];  // dla kryteriów takich jak "najstarszy" lub "najmłodszy"

        }else if(args.length > 6){
            System.out.println("Za dużo argumentów: " + String.join(" ", args));
            return;

        }else if (args.length == 6) {
            // Składanie kryteriów wielosłownych, np. "nie starszy niż" lub "nie młodszy niż"
            if (args[2].equals("nie") && args[3].equals("starszy") && args[4].equals("niż")) {
                kryterium = "nie starszy niż";
                rok = Integer.parseInt(args[5]);
            } else if (args[2].equals("nie") && args[3].equals("młodszy") && args[4].equals("niż")) {
                kryterium = "nie mlodszy niż";
                rok = Integer.parseInt(args[5]);
            } else {
                System.out.println("Nieznane kryterium: " + String.join(" ", args));
                return;
            }
        } else {
            System.out.println("Nieznane kryterium, brak roku: " + String.join(" ", args));
            return;
        }



        try {
            // Sprawdzenie, czy rok jest dodatni
            if (rok< 0) {
                System.out.println("Podaj ROK liczbę dodatnią większą niż 0.");
                return;
            }

        } catch (Exception e) {
            System.out.println("Podano niepoprawną liczbę ROK! Proszę podać liczbę dodatnią w zakresie typu int.");
            return;
        }
        // Generowanie listy samochodów
        List<Samochod> samochody = generujSamochody(liczbaSamochodow);
        //System.out.println(metoda);
        //System.out.println(kryterium);
        //System.out.println(rok);

        // Wybieranie metody
        String metoda = args[1];
        switch(metoda){
            case "Zwyczajnie":
                // Wyświetl wszystkie wygenerowane samochody
                System.out.println("Wyświetl wszystkie wygenerowane samochody");
                wyswietlListe(samochody);
                System.out.println("Wyświetl samochody z kryterium "+kryterium);
                wyswietlListe(Kryterium(kryterium,samochody,rok));
                //System.out.println(Kryterium(kryterium,samochody,rok)); //Wyświetlanie listy
                break;
            case "Wyjatkowo":
                try {
                    // Wyświetl wszystkie wygenerowane samochody
                    System.out.println("Wyświetl wszystkie wygenerowane samochody");
                    wyswietlListe(samochody);

                    List<Samochod> nowyWynik = Kryterium(kryterium,samochody,rok);
                    System.out.println("Wyświetl samochody z kryterium (Metoda Wyjatkowo) "+kryterium);
                    throw new Wyjatek(nowyWynik);
                }
                catch (Wyjatek e) {
                    e.wyswietListe();
                }
                break;
            default:
                System.out.println("Nieznana metoda");
                break;
        }




        //wyswietlListe(Nie_starszy_niz(samochody, rok));
        //wyswietlListe(Nie_mlodszy_niz(samochody, rok));
        //wyswietlListe(Najstarszy_samochod(samochody));
        //wyswietlListe(Najmlodszy_samochod(samochody));








    }



}