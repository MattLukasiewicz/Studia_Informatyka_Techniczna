package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (PrintWriter csvWriter = new PrintWriter(new FileWriter("simulation_results.csv"))) {
            // Write CSV header
            csvWriter.println("Hero;Apple Trees;Banana Trees;Wild Strawberry Trees;Max Points;Hero Points");

            // Program mode selection
            System.out.println("Choose mode of the program:");
            System.out.println("1. Custom play");
            System.out.println("2. Simulation");
            System.out.println("Insert here: ");

            if (!scanner.hasNextInt()) {
                throw new InputMismatchException("Invalid input. Please enter a number.");
            }
            int modeChoice = scanner.nextInt();

            switch (modeChoice) {
                case 1:
                    // Hero selection
                    System.out.println("Choose your hero:");
                    System.out.println("1. Shrek");
                    System.out.println("2. Batman");
                    System.out.println("3. Santa Claus");
                    System.out.println("Insert here: ");

                    if (!scanner.hasNextInt()) {
                        throw new InputMismatchException("Invalid input. Please enter a number.");
                    }
                    int heroChoice = scanner.nextInt();
                    Harvester hero;

                    switch (heroChoice) {
                        case 1:
                            hero = new Shrek("Shrek", '%', 280, 1.0);
                            break;
                        case 2:
                            hero = new Batman("Batman", '#', 190, 1);
                            break;
                        case 3:
                            hero = new Santa_Claus("Santa Claus", '@', 160, 1);
                            break;
                        default:
                            System.out.println("Invalid choice! Defaulting to Shrek.");
                            hero = new Shrek("Shrek", '%', 280, 1.0);
                    }

                    // Map selection
                    System.out.println("Choose your map:");
                    System.out.println("1. Map1.txt");
                    System.out.println("2. Map2.txt");
                    System.out.println("3. Map3.txt");
                    System.out.println("4. Random Map");
                    System.out.println("Insert here: ");

                    if (!scanner.hasNextInt()) {
                        throw new InputMismatchException("Invalid input. Please enter a number.");
                    }
                    int mapChoice = scanner.nextInt();
                    Map map;

                    switch (mapChoice) {
                        case 1:
                            map = new Map("Map1.txt");
                            break;
                        case 2:
                            map = new Map("Map2.txt");
                            break;
                        case 3:
                            map = new Map("Map3.txt");
                            break;
                        case 4:
                            System.out.println("Enter map size: ");
                            if (!scanner.hasNextInt()) {
                                throw new InputMismatchException("Invalid input. Please enter a number.");
                            }
                            int mapSize = scanner.nextInt();
                            map = new Map(mapSize);
                            break;
                        default:
                            System.out.println("Invalid choice! Defaulting to Map1.txt.");
                            map = new Map("Map1.txt");
                    }

                    map.displayMap();

                    Move move = new Move(map, hero, csvWriter);
                    move.execute();
                    break;
                case 2:
                    System.out.println("How many simulations do you want to run?");
                    if (!scanner.hasNextInt()) {
                        throw new InputMismatchException("Invalid input. Please enter a number.");
                    }
                    int howManySimulations = scanner.nextInt();

                    System.out.println("Do you want to display every map?");
                    System.out.println("Yes(1), No(0)");
                    if (!scanner.hasNextInt()) {
                        throw new InputMismatchException("Invalid input. Please enter a number.");
                    }
                    int mapDisplaySimulation = scanner.nextInt();

                    int simulationMapSize = 5;

                    Harvester[] heroes = {
                            new Shrek("Shrek", '%', 280, 1.0),
                            new Batman("Batman", '#', 190, 1),
                            new Santa_Claus("Santa Claus", '@', 160, 1)
                    };



                    for (int i = 0; i < howManySimulations; i++) {
                        map = new Map(simulationMapSize);
                        if (mapDisplaySimulation == 1) map.displayMap();
                        for (Harvester simHero : heroes) {
                            Move moveSimulation = new Move(map, simHero, csvWriter);
                            moveSimulation.execute();
                            simHero.resetPoints();
                        }

                    }
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (InputMismatchException | IOException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
