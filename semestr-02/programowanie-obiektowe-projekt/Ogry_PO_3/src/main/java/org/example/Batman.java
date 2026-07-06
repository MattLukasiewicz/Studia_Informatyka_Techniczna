package org.example;

public class Batman extends Harvester {
    public Batman(String name, char symbol, int height, double harvesting_accuracy) {
        super(name, symbol, height, harvesting_accuracy);
    }

    @Override
    public void Superpower(Tree tree) {
        if(tree.heigh_of_the_fruits>230) {
            points+=tree.maxPoints();
        }
    }
}
