public class Artykul {
    String kod;
    String nazwa;
    int ilosc;
    float cena;
    public Artykul(String kod, String nazwa, int ilosc, float cena){
        this.kod=kod;
        this.nazwa=nazwa;
        this.ilosc=ilosc;
        this.cena=cena;
    }
    public String wyswietlNazwe(){
        return nazwa;
    }

    @Override
    public String toString() {

        return String.format("Kod: %s Nazwa: %s Ilosc: %d Cena: %.2f", kod, nazwa, ilosc, cena);
    }

}
