import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
public class zadanie_1{

    //Funkcje dostępne dla magazyniera
    public static void podgladStanu(LinkedList<Artykul> magazyn){
        for (Artykul artykul: magazyn){
            System.out.println(artykul);
        }
    }
    //Funkcja dodajArtykul najpierw sprawdza unikalnosc artykulu a potem dodaje artykul
    public static void dodajArtykul(LinkedList<Artykul> magazyn, Artykul artykul) {
        // Sprawdzanie unikalności kodu i nazwy artykulu
        for (Artykul artykule : magazyn) {
            if (artykule.nazwa.equals(artykul.nazwa)) {
                System.out.println("Artykul o takiej nazwie juz istnieje");
                return;
            } else if (artykule.kod.equals(artykul.kod)) {
                System.out.println("Artykul o takim kodzie juz istnieje");
                return;
            }
        }


        magazyn.add(artykul);
        aktualizacja_stanu_magazynu(magazyn);
        System.out.println("Dodano artykul pomyslnie");
    }
    //Funkcja pobierzArtykul pobiera liste nazwe artykulu i jego ilosc a potem sprawdza czy taki istnieje jesli tak i mozna usunac dana liczbe z magazynu to tak robi
    public static void pobierzArtykul(LinkedList<Artykul> magazyn,String kod, int ile_pobrac, int czy_usunac_artykul){

        for(Artykul artykul: magazyn){
            if(kod.equals(artykul.kod)){
                if(czy_usunac_artykul==1){
                    magazyn.remove(artykul);
                }else if(artykul.ilosc<ile_pobrac){
                    System.out.println("Nie można wykonać operacji nie wystarczajaca ilosc artykulu w magazynie");
                    break;
                } else if (artykul.ilosc==ile_pobrac) {
                    magazyn.remove(artykul);
                    System.out.println("Usunieto artykul z magazynu");
                }else if(artykul.ilosc>ile_pobrac){
                    artykul.ilosc=artykul.ilosc-ile_pobrac;
                    System.out.println("Zmienono ilosc artykulu ");

                }
            }

        }


        aktualizacja_stanu_magazynu(magazyn);
    }
    //Funkcja  aktualizacja_stanu_magazynu nadpisuje plik z artykulami(magazyn)
    public static void aktualizacja_stanu_magazynu(LinkedList<Artykul> magazyn){
        String csvFile = "dane.txt";

        try (FileWriter writer = new FileWriter(csvFile, false)) { // false oznacza nadpisywanie
            // Dodaj nagłówki
            writer.write("kod;nazwa;ilosc;cena\n");

            // Dodaj dane
            for (Artykul artykul : magazyn) {
                writer.write(artykul.kod+";"+artykul.nazwa+";"+artykul.ilosc+";"+artykul.cena+"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Funkcje dostępne dla kilenta

    public static Artykul znajdzArtykul(LinkedList<Artykul> magazyn, String nazwa) {
        // amiana znaków ? i * na wyrazenia regularne
        String wzorzec = nazwa.replace("?", ".").replace("*", ".*");

        for (Artykul artykul : magazyn) {
            if (artykul.nazwa.matches(wzorzec)) {  //Sprawdzenie czy nazwa artykulu pasuje do wzorca
                return artykul;  //Zwraca pierwszy pasujący artykul
            }
        }


        System.out.println("Brak artykułu pasującego do wzorca: " + nazwa);
        return null;  // Zwracamy null jezeli artykul nie zostal znaleziony



    }
    public static void podgladKoszyka(LinkedList<Artykul> koszyk) {
        if (koszyk.isEmpty()) {
            System.out.println("Koszyk jest pusty.");
        } else {
            System.out.println("Zawartość koszyka:");
            for (Artykul artykul : koszyk) {
                System.out.println(artykul.nazwa + " | Ilość: " + artykul.ilosc + " | Cena: " + artykul.cena);
            }
        }
    }
    public static Artykul sprawdzDostepnosc(LinkedList<Artykul> magazyn, String nazwa, int ilosci) {

        for (Artykul artykule : magazyn) {
            if (artykule.nazwa.equals(nazwa) && artykule.ilosc >= ilosci) {

                return new Artykul(artykule.kod, artykule.nazwa, artykule.ilosc, artykule.cena);
            }
        }
        System.out.println("Pordukt o takiej nazwie nie istenieje lub nie ma wystarczającej ilosci na magazynie");
        return null;
    }
    public static float wycenaZamowienia(LinkedList<Artykul> koszyk) {
        float suma = 0.0f;
        for (Artykul artykul : koszyk) {
            suma += artykul.cena * artykul.ilosc;
        }
        return suma;
    }
    public static boolean realizujZamowienie(LinkedList<Artykul> magazyn, LinkedList<Artykul> koszyk) {
        // Sprawdzamy czy wszystkie artykuly w koszyku ss dostępne w magazynie w odpowiedniej ilosci
        for (Artykul artykulKoszyk : koszyk) {
            Artykul artykulMagazyn = znajdzArtykul(magazyn, artykulKoszyk.nazwa);

            if (artykulMagazyn == null || artykulMagazyn.ilosc < artykulKoszyk.ilosc) {
                System.out.println("Brak wystarczającej ilości: " + artykulKoszyk.nazwa);
                return false; // jak brakuje czegos ni mozna zrobic zamowienia
            }
        }

        //Zmiana ilosci w magazynie
        for (Artykul artykulKoszyk : koszyk) {
            Artykul artykulMagazyn = znajdzArtykul(magazyn, artykulKoszyk.nazwa);
            artykulMagazyn.ilosc -= artykulKoszyk.ilosc;
        }

        //zapisywanie zmian
        aktualizacja_stanu_magazynu(magazyn);

        System.out.println("Zamówienie zostało zrealizowane i stan magazynu został zaktualizowany.");
        return true; // Zamówienie zrealizowane pomyślnie
    }
    public static LinkedList<Artykul> koszyk(LinkedList<Artykul> magazyn) {
        boolean zakupy= true;
        LinkedList<Artykul> koszyk = new LinkedList<>();

        int wybor;
        while (zakupy) {
            System.out.println("Tryb koszyk : ");
            System.out.println("Dostępne akcje:");
            System.out.println("1.Dodaj artykul do koszyka");
            System.out.println("2.Usun artykul z koszyka");
            System.out.println("3.Wyswietl zawartosc koszyka");
            System.out.println("4.Wycen zamowienie");
            System.out.println("5.Zrealizuj zamowienie");
            System.out.println("6.Sprawdz dostepnosc artykulu");
            System.out.println("7.Dostępne produkty");
            System.out.println("8.Zakoncz zakupy");
            Scanner scanner = new Scanner(System.in);
            wybor = Integer.parseInt(scanner.nextLine());
            switch (wybor) {
                case 1:
                    //Dodawanie artykułu do koszyka
                    System.out.println("Podaj nazwę produktu, który chcesz dodać do zamówienia:");
                    String nazwa = scanner.nextLine();

                    System.out.println("Podaj ilość produktu, który chcesz dodać do zamówienia:");
                    int ileChce = Integer.parseInt(scanner.nextLine());

                    Artykul wynik = znajdzArtykul(magazyn, nazwa);
                    if (wynik != null && wynik.ilosc >= ileChce) {
                        //Dodajemy artykul do koszyka
                        koszyk.add(new Artykul(wynik.kod, wynik.nazwa, ileChce, wynik.cena));
                        System.out.println("Dodano " + ileChce + " sztuk " + nazwa + " do koszyka.");
                    } else {
                        System.out.println("Produkt niedostępny lub brak wystarczającej ilości.");
                    }
                    break;
                case 2:
                    System.out.println("Podaj nazwę produktu, który chcesz usunąć z koszyka:");
                    String nazwaUsun = scanner.nextLine();
                    boolean znaleziono = false;
                    for (int i = 0; i < koszyk.size(); i++) {
                        if (koszyk.get(i).nazwa.equals(nazwaUsun)) {
                            koszyk.remove(i);
                            znaleziono = true;
                            System.out.println("Usunięto " + nazwaUsun + " z koszyka.");
                            break;
                        }
                    }

                    if (!znaleziono) {
                        System.out.println("Nie znaleziono " + nazwaUsun + " w koszyku.");
                    }
                    break;
                case 3:
                    //Wyswietlanie zawartości koszyka klienta
                    podgladKoszyka(koszyk);
                    break;

                case 4: //Wycena zamowienia
                    podgladKoszyka(koszyk);
                    float wycena = wycenaZamowienia(koszyk);
                    System.out.println("Łączny koszt zamówienia: " + wycena + " zł");
                    break;
                case 5: //Realizacja zamowienia klienta
                    if (realizujZamowienie(magazyn, koszyk)) {
                        System.out.println("Zamówienie zrealizowane.");
                        koszyk.clear();
                    } else {
                        System.out.println("Nie udało się zrealizować zamówienia. Sprawdź dostępność w magazynie.");
                    }
                    break;
                case 6:


                    System.out.println("Podaj nazwe artykulu: ");
                    nazwa= scanner.nextLine();


                    System.out.println("Podaj ilosc artykulu(sprawdzaznie czy jest wystarczająca ilość):  ");
                    int ilosc = Integer.parseInt(scanner.nextLine());


                    System.out.println(sprawdzDostepnosc(magazyn,nazwa,ilosc));
                    break;
                case 7: //Wysweitlanie dostepnych produktow
                    podgladStanu(magazyn);
                    break;
                case 8: //Zakonczenie zakupow
                    zakupy = false;
                    break;
                default:
                    System.out.println("Blad");
            }


        }
        return koszyk;

    }
    public static LinkedList<Artykul> OdczytPLiku(){

        String filePath = "dane.txt";
        String line;
        String separator = ";"; //Separatorem w  pliku jest ";"
        LinkedList<Artykul> data = new LinkedList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();//Pomijam pierwsza linie ktora jest naglowkiem
            while ((line = br.readLine()) != null) {
                line = line.trim();  // Usuwanie ewentualnych białych znaków na początku i końcu

                if (line.isEmpty()) {  // Jeśli linia jest pusta, pomijamy ją
                    continue;
                }
                String[] values = line.split(separator);
                //Pobieranie parametrow obiektu artykul z pliku wedlug kod[0],nazwa[1],ilość[2],cena[3]
                String kod = values[0];
                String nazwa = values[1];
                int ilosc = Integer.parseInt(values[2]);
                float cena = Float.parseFloat(values[3]);

                //Tworzenie obiektu Artykul
                Artykul artykul = new Artykul(kod, nazwa, ilosc, cena);
                //Dodawanie Artykulu do listy
                data.add(artykul);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;

    }
    public static void main(String[] args) {


        String kod;
        String nazwa;
        int ilosc;
        float cena;
        LinkedList<Artykul> data = OdczytPLiku();
        Scanner scanner = new Scanner(System.in);

        boolean czyKoniec = false;
        while (!czyKoniec) {
            System.out.println("Podaj w jakim trybie chcesz odpalic program('klient'/'magazynier'/'koniec'): ");
            String wybor = scanner.nextLine();

            switch (wybor) {
                case "klient":
                    boolean klientTryb = true;
                    while (klientTryb) {
                        System.out.println("Tryb klienta:");
                        System.out.println("1. Tryb koszyk");
                        System.out.println("2. Powrót do menu głównego");
                        int wyborKlienta = Integer.parseInt(scanner.nextLine());

                        switch (wyborKlienta) {
                            case 1:
                                koszyk(data); // Funkcja obsługująca koszyk
                                break;
                            case 2:
                                klientTryb = false; // Powrót do menu głównego
                                break;
                            default:
                                System.out.println("Nieprawidłowy wybór.");
                                break;
                        }
                    }
                    break;
                case "magazynier":
                    boolean magazynierTryb = true;
                    while (magazynierTryb) {
                        System.out.println("Menu magazyniera:");
                        System.out.println("1. Dodawanie artykułu do magazynu");
                        System.out.println("2. Usuwanie artykułu lub zmniejszanie ilości artykułu z magazynu");
                        System.out.println("3. Podgląd stanu magazynu");
                        System.out.println("4. Powrót do menu głównego");
                        int wyborek = Integer.parseInt(scanner.nextLine());

                        switch (wyborek) {
                            case 1:
                                System.out.println("Podaj unikalny czterocyfrowy kod artykułu:");
                                kod = scanner.nextLine();
                                System.out.println("Podaj nazwę artykułu: ");
                                nazwa = scanner.nextLine();
                                System.out.println("Podaj ilość artykułu: ");
                                ilosc = Integer.parseInt(scanner.nextLine());
                                System.out.println("Podaj cenę artykułu: ");
                                cena = Float.parseFloat(scanner.nextLine());
                                Artykul artykul = new Artykul(kod, nazwa, ilosc, cena);
                                dodajArtykul(data, artykul);
                                break;
                            case 2:
                                System.out.println("Podaj kod artykułu, którego ilość chcesz zmniejszyć/usunąć: ");
                                String kodUsun = scanner.nextLine();
                                System.out.println("Podaj ilość artykułu do zmniejszenia/usunięcia: ");
                                int iloscUsun = Integer.parseInt(scanner.nextLine());
                                //Pobierz artykuł i zmniejsz jego ilość
                                pobierzArtykul(data, kodUsun, iloscUsun, 0);
                                break;
                            case 3:
                                podgladStanu(data);
                                break;
                            case 4:
                                magazynierTryb = false; // Powrót do menu głównego
                                break;
                            default:
                                System.out.println("Nieprawidłowy wybór.");
                                break;
                        }
                    }
                    break;
                case "koniec":
                    czyKoniec = true;
                    System.out.println("Zamykanie programu.");
                    break;
                default:
                    System.out.println("Nieprawidłowy wybór! Spróbuj ponownie.");
                    break;
            }
        }





    }
}