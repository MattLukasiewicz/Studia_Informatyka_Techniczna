package org.example;

import java.io.PrintWriter;

import static java.lang.Math.abs;

public class Move {
    private Map map;
    private Harvester hero;
    private Tree[] trees;
    private PrintWriter csvWriter;

    public Move(Map map, Harvester hero, PrintWriter csvWriter) {
        this.map = map;
        this.hero = hero;
        this.csvWriter = csvWriter;
        this.trees = new Tree[]{
                new Tree("apple_tree", 'J', 20, 200, 5),
                new Tree("banana_tree", 'B', 10, 330, 10),
                new Tree("wild_strawberry_tree", 'W', 1000, 100, 1)
        };
    }

    public void execute() {
        char[][] mapArray = map.getMapArray();
        int mapSize = map.getMapSize();

        int howManyAppleTrees = 0;
        int howManyBananaTrees = 0;
        int howManyWildStrawberryTrees = 0;

        int treesVisited = 0;

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                char currentSymbol = mapArray[i][j];

                if (currentSymbol != '0') {
                    if (currentSymbol == trees[0].symbol) howManyAppleTrees++;
                    if (currentSymbol == trees[1].symbol) howManyBananaTrees++;
                    if (currentSymbol == trees[2].symbol) howManyWildStrawberryTrees++;
                    for (Tree tree : trees) {
                        if (currentSymbol == tree.symbol) {
                            treesVisited++;
                            if (treesVisited % 5 == 0) {
                                hero.Superpower(tree);
                            } else {
                                double heightDifference = abs(hero.height - tree.heigh_of_the_fruits);

                                if (heightDifference <= 40) {
                                    hero.points += tree.maxPoints();
                                } else if (heightDifference > 40 && heightDifference < 100) {
                                    hero.points += tree.maxPoints() * heightDifference * 0.01;
                                } else if (heightDifference >= 100) {
                                    hero.points += tree.maxPoints() * heightDifference * 0.001;
                                }
                            }
                        }
                    }
                }
            }
        }

        double maxPoints = howManyAppleTrees * trees[0].maxPoints() +
                howManyBananaTrees * trees[1].maxPoints() +
                howManyWildStrawberryTrees * trees[2].maxPoints();

        System.out.println("How many apple trees: " + howManyAppleTrees + " Max points from apple trees: " + howManyAppleTrees * trees[0].maxPoints());
        System.out.println("How many banana trees: " + howManyBananaTrees + " Max points from banana trees: " + howManyBananaTrees * trees[1].maxPoints());
        System.out.println("How many wild strawberry trees: " + howManyWildStrawberryTrees + " Max points from wildstrawberry trees: " + howManyWildStrawberryTrees * trees[2].maxPoints());
        System.out.println("Map max points: "+maxPoints);
        System.out.println("Hero: " + hero.name + " gained " + hero.points);
        System.out.println();

        csvWriter.println(hero.name + ";" + howManyAppleTrees + ";" + howManyBananaTrees + ";" + howManyWildStrawberryTrees + ";" + maxPoints + ";" + hero.points);
    }
}
