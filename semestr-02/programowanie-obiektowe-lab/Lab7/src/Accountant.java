public class Accountant extends Employee{
    public Accountant(String imie, String nazwisko,double pensja){
                super(imie, nazwisko, pensja);
    }
    @Override
    public double ObliczWyplatke(){
        double premia = this.pensja*0.12;
        double kaska=this.pensja+premia;
        return kaska*(1-0.19);
    }

}
