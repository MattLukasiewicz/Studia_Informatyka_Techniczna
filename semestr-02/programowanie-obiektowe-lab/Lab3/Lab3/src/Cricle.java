import java.lang.Math.*;
public class Cricle{
    public static void main(String[] args){
        int N=10;

        printCircle(N);
    }
    public static void printCircle(int n){
        String[][] Tab= new String [2*n+1][2*n+1];
        if(n>=2){
            int x=n;
            int y=n;
            for(int i=0; i<2*n+1;i++){
                for(int j=0; j<2*n+1;j++){
                    double odleglosc = Math.sqrt((x - i) * (x - i) + (y - j) * (y - j));
                    /*
                    System.out.println(i);
                    System.out.println(j);
                    System.out.println(odleglosc);
                    */
                    if(odleglosc<=n){
                        Tab[i][j]=" $ ";


                    }else {
                        Tab[i][j] = "   ";


                    }


                }
            }

            //Wypisywanie
            for(int a=0;a<2*n+1;a++){
                for(int b=0; b<2*n+1;b++){
                    System.out.print(Tab[a][b]);
                }
                System.out.println();

            }

        }else{
            System.out.println("Za mały promien kola!");

        }

    }
}
