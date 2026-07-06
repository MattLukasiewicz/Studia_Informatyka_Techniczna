
import java.io.*;
import java.util.*;

public class Main {
    private static String filename;
    private static ArrayList<ArrayList<Integer>> binaryMask;
    private static int centerX;
    private static int centerY;

    public static void readMask() throws FileNotFoundException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj nazwę pliku (bez rozszerzenia .txt): ");
        filename = scanner.nextLine() + ".txt";


        File file = new File(filename);
        Scanner fileScanner = new Scanner(file);

        //wczytywanie do 2wl
        binaryMask = new ArrayList<>();
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] values = line.split(" ");
            ArrayList<Integer> row = new ArrayList<>();
            for (String value : values) {
                row.add(Integer.parseInt(value));
            }
            binaryMask.add(row);
        }
        fileScanner.close();


    }

    public static int calculateCenter() {
        int totalX = 0;
        int totalY = 0;
        int count = 0;

        for (int i = 0; i < binaryMask.size(); i++) {
            for (int j = 0; j < binaryMask.get(i).size(); j++) {
                if (binaryMask.get(i).get(j) == 1) {
                    totalX += j;
                    totalY += i;
                    count++;
                }
            }
        }

        if (count == 0) {
            System.out.println("Nie można obliczyć środka ciężkości - brak niezerowych elementów w masce.");
            return -1; // Błąd
        }

        centerX = totalX / count;
        centerY = totalY / count;

        return 0;
    }

    public static void saveResult() {
        try {
            FileWriter writer = new FileWriter(filename.replace(".txt", "_solution.txt"));
            for (ArrayList<Integer> row : binaryMask) {
                for (int value : row) {
                    if (value == 1 && centerX >= 0 && centerY >= 0) {
                        writer.write("P ");
                    } else {
                        writer.write(value + " ");
                    }
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("Wynik zapisano do pliku " + filename.replace(".txt", "_solution.txt"));
        } catch (IOException e) {
            System.err.println("Błąd zapisu pliku.");
            System.exit(1);
        }
    }

    public static void main(String[] args) throws FileNotFoundException{
        readMask();
        calculateCenter();
        saveResult();
    }
}