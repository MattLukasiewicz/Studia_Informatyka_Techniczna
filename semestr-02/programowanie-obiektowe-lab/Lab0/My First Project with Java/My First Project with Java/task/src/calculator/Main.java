package calculator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Earned amount:");
        System.out.println("Bubblegum: $202");
        System.out.println("Toffee: $118");
        System.out.println("Ice cream: $2250");
        System.out.println("Milk chocolate: $1680");
        System.out.println("Doughnut: $1075");
        System.out.println("Pancake: $80");
        int income=202+118+2250+1680+1075+80;

        System.out.printf("Income: %d\n",income);
        System.out.println("Staff expenses:");
        Scanner expenses = new Scanner(System.in);
        int Staff_exenses = expenses.nextInt();
        System.out.println("Other expenses:");
        int Other_expenses = expenses.nextInt();
        System.out.printf("Net income: $%d",income-Staff_exenses-Other_expenses);



    }
}