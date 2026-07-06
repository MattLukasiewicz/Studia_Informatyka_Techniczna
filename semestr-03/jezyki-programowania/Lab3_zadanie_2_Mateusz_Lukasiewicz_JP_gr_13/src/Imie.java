enum Imiona{
    Marek, Darek, Arek,Andrzej
}
public class Imie {


    int[] ilosc_wystapien = new int[3];//tablica wystapien imion w poszczegolnych plikach
    String nazwa;
    public Imie(String nazwa){
        this.nazwa= nazwa;
        this.ilosc_wystapien = new int[3];

    }
    public String wyswietlNazwe(){
        return nazwa;
    }



    public void zliczWystapienie(int nrpliku) {

        ilosc_wystapien[nrpliku]++;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(nazwa);

        for (int i = 0; i < ilosc_wystapien.length; i++) {
            sb.append(" plik").append(i + 1).append(": ").append(ilosc_wystapien[i]).append(" ");
        }
        return sb.toString();
    }


}

