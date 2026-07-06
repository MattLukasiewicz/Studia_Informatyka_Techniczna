import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Map.Entry;

public class Studies {
    public static void main(String args[]){

        Set<String> lista_studentow =new HashSet<>();
        Set<String> kierunki_studiow =new HashSet<>();
        Map<String, String> kierunki_studiow_ze_starostami =new HashMap<>();

        String[] nazwa= {"Jan Kowalski", "Ania Daborwska", "Mikolaj Strzukawka", "Tytus Bomba"};
        String[] kurs= {"Informatyka techniczna", "Teleinformatyka","Astronomia", "Fuszerka"};
        boolean[] starosta={true,false,false,true};

        int n=nazwa.length;
        //wpis do kartoteki
        for(int i=0;i<n;i++){
            //a
            kierunki_studiow.add(kurs[i]);
            //b
            lista_studentow.add(nazwa[i]);
            //c
            if(starosta[i]){
                kierunki_studiow_ze_starostami.put(kurs[i],nazwa[i]);
            }

        }
        //a
        System.out.println("Rozwiazanie problemu a:");
        for(String nazewka: lista_studentow){
            System.out.println(nazewka);
        }
        System.out.println();
        //b
        System.out.println("Rozwiazanie problemu b:");
        for(String kieruneczek: kierunki_studiow){
            System.out.println(kieruneczek);
        }
        System.out.println();
        //c
        System.out.println("Rozwiazanie problemu c:");
        Set<Entry<String, String>> entrySet = kierunki_studiow_ze_starostami.entrySet();
        for(Entry<String, String> entry:entrySet ){
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }
}
