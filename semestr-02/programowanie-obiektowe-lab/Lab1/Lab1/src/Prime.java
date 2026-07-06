//Mateusz Łukasiewicz 281035
import java.util.Scanner;
 public class Prime {
    public static void main(String[] args) {
        Scanner scan =new Scanner(System.in);
        System.out.print("Podaj liczba którą chcesz sprawdzić czy jest liczbą pierwszą: ");
        int N = scan.nextInt();
        isPrime(N);
    }

    public static void isPrime(int N){
        boolean czyT=true;
        if(N<2){
            czyT=false;
        }else {

            for (int i = 2; i < N; i++) {
                if (N % i == 0) {
                    czyT = false;
                }
            }
        }
        if(czyT){
            System.out.println("Jest to liczba pierwsza");
        }else{
            System.out.println("Nie jest to liczba pierwsza");
        }
    }


}