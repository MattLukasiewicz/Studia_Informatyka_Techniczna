import java.util.ArrayList;
import java.util.LinkedList;

public class zadanie_2 {
    //Mateusz Łukasiewiwcz 281035 JP gr 13
    /*
    Todo
    1.Wczytyanie plików tekstowych jako argumenty
    2.Pliki tekstowe z imionami
    3.Enumeracja imiona
    4.Obiekt imię(nazwa ilość)

    przechodzenie po pliku i znajdowanie imienia
    .Lista liter(26) lista imion
    .Wyświetlanie listy liter(array list) w środku niej lista imion(linked list) a w liscie imion imiona jako obiekty


    czy jak jest pusta lista to wyswietlac

     */
    public static void dodajImie(LinkedList<Imie> imiona, ArrayList<LinkedList<Imie>> litery) {

        for (Imie imie : imiona) {

            char pierwszaLitera = Character.toUpperCase(imie.wyswietlNazwe().charAt(0));
            if (pierwszaLitera < 'A' || pierwszaLitera > 'Z') {
                continue;
            }

            int indeks = pierwszaLitera - 'A';

            //Lista imiona na dana literke
            LinkedList<Imie> listaImion = litery.get(indeks);


            listaImion.add(imie);
        }
    }

    public static void main(String[] args) {


        try {
            LiczenieImion.Czy_wejscie_ok(args);
            LiczenieImion liczenieimion = new LiczenieImion();

            LinkedList<Imie> wyniki = new LinkedList<>();//Jedna lista wynikow dla wszystkich plikow
            for (Imiona imie : Imiona.values()) {
                wyniki.add(new Imie(imie.name()));
            }

            for (int i = 0; i < args.length; i++) {
                String fileName =args[i];
                liczenieimion.liczImiona(fileName, i,wyniki);

            }

            ArrayList<LinkedList<Imie>> listaLiter = new ArrayList<>(26);

            //Lista liter
            for (int i = 0; i < 26; i++) {
                listaLiter.add(new LinkedList<>());
            }

            dodajImie(wyniki, listaLiter);

            //Wyswietlanie wynikow dal kazdej litery i pliku
            for (int i = 0; i < 26; i++) {
                char litera = (char) ('A' + i);
                System.out.println("Litera " + litera + ": " + listaLiter.get(i));
            }


        } catch (WyjatekBladneParametry e) {
            System.err.println("Blad: "+e.getMessage());

        }
    }
}
