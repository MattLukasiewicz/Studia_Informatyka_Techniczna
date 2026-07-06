package org.example;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

class Map {
    private char[][] mapArray;

    private int mapSize;

    // Constructor that initializes the mapArray and loads the map from file
    public Map(String filename) {
        this.mapSize = 5; // Fixed size for file maps
        mapArray = new char[mapSize][mapSize];
        loadMapFromFile(filename);
    }

    // Constructor that initializes the mapArray with a given size and generates a random map
    public Map(int mapSize) {
        this.mapSize = mapSize;
        mapArray = new char[mapSize][mapSize];
        randomMap();
    }

    // Method to load the map from a file into the mapArray
    private void loadMapFromFile(String filename) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream == null) {
            System.out.println("File not found: " + filename);
            System.exit(1);
        }

        Scanner scanner = new Scanner(inputStream);
        for (int i = 0; i < mapSize; i++) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elements = line.split(" ");
                for (int j = 0; j < mapSize; j++) {
                    mapArray[i][j] = elements[j].charAt(0);
                }
            }
        }
        scanner.close();
    }

    // Method to display the mapArray on the screen
    public void displayMap() {
        System.out.println("Map:");
        for (int i = 0; i < mapSize; i++) { // Adjust loop to mapSize
            for (int j = 0; j < mapSize; j++) { // Adjust loop to mapSize
                System.out.print(mapArray[i][j] + " ");
            }
            System.out.println();
        }
    }

    public char[][] getMapArray() {
        return mapArray;
    }

    public int getMapSize() {
        return mapSize;
    }

    char[] symbols = {'W', 'J', 'B', '0'};

    public void randomMap() {
        Random random = new Random();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                int randomIndex = random.nextInt(symbols.length);
                mapArray[i][j] = symbols[randomIndex];
            }
        }
    }
}