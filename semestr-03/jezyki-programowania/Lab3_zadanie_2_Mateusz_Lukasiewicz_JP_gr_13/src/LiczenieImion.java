import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class LiczenieImion {
    public static void Czy_wejscie_ok(String[] args) throws WyjatekBladneParametry {
        // sprawdzamy czy podana liczba argumentow sie zgadza z oczekiwaniem programu(3)
        if (args.length != 3) {
            throw new WyjatekBladneParametry("Należy podać dokładnie 3 nazwy plików jako argumenty.");
        }

        for (int i = 0; i < args.length; i++) {
            String fileName = args[i];
            /*
            // Mozna dodać jeśli ktoś zapomni dać na końcu txt

            if (!fileName.endsWith(".txt")) {
                fileName += ".txt"; // Dodaje .txt, jeśli brakuje
            }

             */


            File file = new File(fileName);

            if (!fileName.endsWith(".txt")) {
                throw new WyjatekBladneParametry("Plik musi mieć rozszerzenie .txt: " + fileName);
            }
            if (!file.exists()) {
                throw new WyjatekBladneParametry("Plik o nazwie: "+fileName+" nie istnieje ");
            }

            if (!file.isFile()) {
                throw new WyjatekBladneParametry("Podany argument nie jest plikiem: " + fileName);
            }

            //Sprawdzanie czy ds się przeczytac plik
            if (!file.canRead()) {
                throw new WyjatekBladneParametry("Nie mozna przeczytac pliku: " + fileName);
            }

        }
        System.out.println("Wszystkie pliki sa poprawne");

    }





    public LinkedList<Imie> liczImiona(String fileName, int ktory_plik, LinkedList<Imie> imiona){
        /*
        LinkedList<Imie> imiona = new LinkedList<>();
        // Tworzymy obiekt Imie dla każdego imienia z enum i dodajemy go do listy
        for (Imiona imie : Imiona.values()) {
            imiona.add(new Imie(imie.name()));
        }

        for(Imie imie: imiona){
            System.out.println(imie);
        }

         */



        //System.out.println(fileName);
        String fileNames = fileName;
        File file = new File(fileNames);

        try (BufferedReader buffer = new BufferedReader(
                new FileReader(file))) {
            String str;
            //Przechodzimy po kazdej linii pliku, jeśli buforowany strumień zakończył się i nie ma wiersza do odczytania ta metoda zwraca NULL.
            while ((str= buffer.readLine()) != null) {
                //slowa oddzielone spacjami
                String[] slowa = str.split(" ");

                for (String slowo : slowa) {
                    //Porownujemy imiona w liście zw ze slowami
                    for (Imie imie : imiona) {
                        // (ignorujac wielkosc liter w porowananiu) jeśli slowo rowna się imieniu
                        if (slowo.equalsIgnoreCase(imie.wyswietlNazwe())) {
                            imie.zliczWystapienie(ktory_plik);

                        }
                    }
                }
            }
            buffer.close();
            imiona.sort((i1, i2) -> i1.wyswietlNazwe().compareToIgnoreCase(i2.wyswietlNazwe()));

        } catch (IOException e) {


            e.printStackTrace();
        }

        return imiona;

    }
}
