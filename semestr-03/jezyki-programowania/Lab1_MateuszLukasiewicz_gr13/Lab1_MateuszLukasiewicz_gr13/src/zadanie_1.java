import java.math.BigInteger;
import java.util.Scanner;

public class zadanie_1 {
    //Mateusz Lukasiewicz 281035 gr13 23.10.2024
    //Inty

    public static int Silnia_int(int n){
        int l = 1;
        for(int i=1;i<=n;i++){
            l = l*i;
        }
        return l;
    }

    public static int liczba_int(int n){
        int suma = 0;
        for(int j=3;j<=n;j++){
            int  silnia=Silnia_int(j-2);
            suma += silnia-(j*(silnia/j));
        }
        return suma-1;
    }
    // Funkcja liczaca ile maks n dla typu int i long
    public static int ilosc_maks_int() {
        int silnia=1;
        int maxInt = -1;

        int intMaxValue = Integer.MAX_VALUE;

        for (int j = 3;j<30; j++) {
            silnia = silnia*(j-2);
            //System.out.println(silnia);
            if (silnia*(j-2)/silnia!=j-2) {
                maxInt = j; // Ostatnia wartość n  dla której silnia mieściła się w int

                break;
            }

        }
        return maxInt;
    }
    //Long
    public static long Silnia_long(long n){
        long l = 1;
        for(long i=1;i<=n;i++){
            l = l*i;
        }
        return l;
    }
    public static long liczba_long(int n){
        long suma = 0;
        for(long j=3;j<=n;j++){
            long  silnia=Silnia_long(j-2);
            suma += silnia-(j*(silnia/j));
        }
        return suma-1;
    }
    public static long ilosc_maks_long() {
        long silnia=1;
        long maxLong = -1;
        long intMaxValue = Long.MAX_VALUE;

        for (int j = 3;j<30; j++) {
            silnia = silnia*(j-2);
            //System.out.println(silnia);
            if (silnia*(j-2)/silnia!=j-2) {
                maxLong = j; // Ostatnia wartość n  dla której silnia mieściła się w long
                break;
            }
        }
        return maxLong;
    }
    //Funkcja silnia BIGINT
    public static BigInteger silnia(BigInteger n){
        BigInteger l = BigInteger.valueOf(1);
        for(BigInteger j =BigInteger.valueOf(2);j.compareTo(n)<=0;j=j.add(BigInteger.valueOf(1))){
            l=l.multiply(j);
        }

        return l;
    }
    // Funkcja liczenie ze wzoru
    public static BigInteger ilosc_pierwszych(int x){
        BigInteger n = BigInteger.valueOf(x);
        BigInteger suma = BigInteger.valueOf(0);
        BigInteger silnia = BigInteger.valueOf(1);
        for(BigInteger j = BigInteger.valueOf(3); j.compareTo(n) <=0;j=j.add(BigInteger.valueOf(1))){
            silnia=silnia.multiply(j.subtract(BigInteger.valueOf(2)));
            suma = suma.add(silnia.subtract(j.multiply(silnia.divide(j))));
        }
        return suma.subtract(BigInteger.valueOf(1));
    }
    public static void main(String[] args) {

        try{

            //System.out.print("# zadanie_1 ");
            int n = Integer.parseInt(args[0]);

            //Program pobiera argument za pomoca funkcji scanner
            //Scanner scanner = new Scanner(System.in);
            //int n = scanner.nextInt();
            /*
            //String liczbaStr = args[0];
            int liczbaInt = Integer.parseInt(liczbaStr);
            long liczbaLong = Long.parseLong(liczbaStr);
            BigInteger liczbaBigInteger = new BigInteger(liczbaStr);

             */
            if(n<=3&&n!=-1){
                System.out.println("Blad danych n musi być rowne -1 lub wieksze od 3");


            }else if(n==-1){
                //Funkcja liczaca maks int i long
                System.out.println("Maksymalne n dla typu int: " +ilosc_maks_int());
                System.out.println("Maksymalne n dla typu long: " +ilosc_maks_long());


            }else{
                //Funkcja oblicza na typie na ktorym nie wychodzi poza zakres. Można obliczac osobno dla 3 roznych typow na raz poniewaz program zbudowany jest z funkcji
                if(n<=ilosc_maks_int()){// Funkcja oblicza na Intach

                    System.out.print("Ilosc liczb pierwszych nie wiekszych od n dla n="+n+" wynosi "+liczba_int(n));
                    //System.out.println("Liczone na Intach");

                }else if(n<=ilosc_maks_long()){// Funkcja oblicza na Longach

                    System.out.print("Ilosc liczb pierwszych nie wiekszych od n dla n="+n+" wynosi " +liczba_long(n));
                    //System.out.println("Liczone na Longach");

                } else{//// Funkcja oblicza na Big int

                    System.out.println("Ilosc liczb pierwszych nie wiekszych od n dla n="+n+" wynosi "+ilosc_pierwszych(n));
                    //System.out.println("Liczone na BigInt");

                }




            }
            //scanner.close();
        }catch(Exception e){
            //Blad
            System.out.print("Blad");

        }


    }
}
