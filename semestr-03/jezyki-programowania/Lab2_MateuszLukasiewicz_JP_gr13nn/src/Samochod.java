import java.util.Random;

enum Marka{
    POLONEZ,
    FIAT,
    SYRENKA;
}
public class Samochod {

    
    Marka marka;
    float cena;
    int rocznik;

    public Samochod(Marka marka,float cena, int rocznik){
        this.marka = marka;
        this.cena=cena;
        this.rocznik = rocznik;
    }


    public static Samochod stworzSamochod() {
        Random rand = new Random();
        Marka marka = Marka.values()[rand.nextInt(Marka.values().length)];
        float cena=0;
        int rocznik=0;
        switch(marka) {
            case POLONEZ:
                cena = 20000 + (30000 * rand.nextFloat());// ceny od 20,000 do 50,000
                rocznik = 1978 + rand.nextInt(25);// roczniki 1978–2002
                break;

            case FIAT:
                cena = 10000 + (40000 * rand.nextFloat());// ceny od 10,000 do 50,000
                rocznik = 1967 + rand.nextInt(25);// roczniki 1967–1991
                break;

            case SYRENKA:
                cena = 8000 + (30000 * rand.nextFloat());// ceny od 8,000 do 38,000
                rocznik = 1957 + rand.nextInt(27);// roczniki 1957–1983
                break;
            default:
                break;
        }
        //float cena = 10000 + (50000 * rand.nextFloat());
        //int rocznik = 1980 + rand.nextInt(44);
        // Zaokrąglenie ceny do dwóch miejsc po przecinku
        cena = Math.round(cena * 100) / 100.0f; // Używamy 100 (int), aby uzyskać int, a następnie konwertujemy na float


        return new Samochod(marka, cena, rocznik);
    }


    @Override
    public String toString() {
        return "Samochod{" +
                "marka=" + marka +
                ", cena=" + cena +
                ", rocznik=" + rocznik +
                '}';
    }


}
