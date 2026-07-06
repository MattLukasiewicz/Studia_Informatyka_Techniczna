public class Programmer extends ITEmployee{
    public Programmer(String imie, String nazwisko, double pensja, int doswiadczenie){
        super(imie, nazwisko, pensja, doswiadczenie);
    }
    @Override
    public double ObliczWyplatke(){
        /*
        double hajs= super.ObliczWyplatke();
        return hajs*(1-(0.19*1.0/3.0));

         */

        double premia = 0;
        if(doswiadczenie<=0){
            premia=0;
        }else if(doswiadczenie>=2&&doswiadczenie<5){
            premia= 750;
        }else if(doswiadczenie>=5&&doswiadczenie<7){
            premia = 2000;
        }else if (doswiadczenie>=7){
            premia=5000;
        }
        double kaska= this.pensja+premia;
        return kaska*(1-(0.19*1.0/3.0));



    }

}
