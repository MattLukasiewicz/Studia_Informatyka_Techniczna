import java.awt.Color;
public class Kolor {
    private final String nazwa;
    private final Color kolor;

    public Kolor(String nazwa, Color kolor) {
        this.nazwa = nazwa;
        this.kolor = kolor;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Color getKolor() {
        return kolor;
    }

    @Override
    public String toString() {
        return nazwa; // Dla ComboBox wyświetlana będzie tylko nazwa
    }
}
