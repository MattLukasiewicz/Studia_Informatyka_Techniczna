package org.example;

public class Santa_Claus extends Harvester {
    public Santa_Claus(String name, char symbol, int height, double harvesting_accuracy) {
        super(name, symbol, height, harvesting_accuracy);
    }

    @Override
    public void Superpower(Tree tree) {
        if(tree.heigh_of_the_fruits<120) {
            points+=tree.maxPoints();
        }
    }
}
