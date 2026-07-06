package org.example;

public class Tree {

    String name;
    char symbol;
    int quantity_of_fruit;
    int heigh_of_the_fruits;
    int points;

    int maxPoints()
    {
        return quantity_of_fruit*points;
    }

    public Tree(String name,char symbol,int quantity_of_fruit,int heigh_of_the_fruits,int points){
        this.name=name;
        this.symbol=symbol;
        this.quantity_of_fruit=quantity_of_fruit;
        this.heigh_of_the_fruits=heigh_of_the_fruits;
        this.points=points;
    }
}
