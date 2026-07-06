//Mateusz Łukasiewicz 281035
import java.util.Scanner;

public class Procrastination {
    public static void main(String[] args) {
        Scanner scan =new Scanner(System.in);
        System.out.print("Podaj ktora metode chcesz uzyc 1(rekurencja) 2(iteracja) :");
        int wybor = scan.nextInt();
        System.out.print("Podaj nr wyrazu ciągu, którego wartość chcesz policzyć: ");
        int N = scan.nextInt();
        switch(wybor){
            case 1:System.out.println(proRek(N));break;
            case 2:System.out.println(proIte(N));break;
            default:break;
        }

    }
    public static int proRek(int n){

        if(n==0){
            return 1;
        }
        if(n==1){
            return -1;
        }
        return 2*proRek(n-1)+4*proRek(n-2);
    }
    public static int proIte(int n){
        int a_0=1,a_1=-1,a = 0;
        if(n==0){
            return 1;
        }else if(n==1){
            return -1;
        }else {
            for (int i = 1; i < n; i++) {
                a = 4 * a_0 + 2 * a_1;
                a_0 = a_1;
                a_1 = a;
            }
            return a;
        }

    }

}
