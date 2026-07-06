import java.util.List;

public class Wyjatek extends Exception {
    List<Samochod> samochody;

    public Wyjatek(List<Samochod> samochody) {
        this.samochody = samochody;
    }

    public void wyswietListe() {
        samochody.forEach(System.out::println);
    }
}
