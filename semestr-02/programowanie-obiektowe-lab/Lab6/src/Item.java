public class Item {
    String Nazwa;
    double Cena;

    String marka;
    Date date;

    public static int countStatic_ = 0;
    public int countNonStatic_ = 0;
    public static int idStatic_ = 0;
    public int idNonStatic_ = 0;


    public Item(){
        this.Nazwa = "ERROR";
        this.Cena= -1;
        this.marka = "ERROR";
        System.out.println("Siema tu Konstruktor domyślny");
        countStatic_++;
        //countNonStatic_++;
        idStatic_++;
        //idNonStatic_++;
    }
    public Item(String Nazwa, double Cena, String marka){
        this.Nazwa = Nazwa;
        this.Cena = Cena;
        this.marka = marka;
        System.out.println("Siema tu Konstruktor z parametrem");
        countStatic_++;
        //countNonStatic_++;
        idStatic_++;
        //idNonStatic_++;
    }
    public Item(Item item){
        this.Nazwa = item.Nazwa;
        this.Cena = item.Cena;
        this.marka = item.marka;
        System.out.println("Siema tu Konstruktor kopiujacy");
        countStatic_++;
        //countNonStatic_++;
        idStatic_++;
        //idNonStatic_++;

    }

    public void show(){
        System.out.println("Produkt: "+ Nazwa+ " Cena: "+ Cena +" Marka: "+ marka);
        System.out.println("Count static: "+ countStatic_);
        //System.out.println("Count Non static: "+ countNonStatic_);
        System.out.println("id static: "+ idStatic_);
        //System.out.println("id non static: "+ idNonStatic_);
    }


}
