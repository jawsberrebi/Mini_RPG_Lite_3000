package com.example.mini_rpg_lite_3000_withinterface;

public class Food implements Consumable{

    private String name;
    private int bonusLifePoints;

    public Food(String n, int bLP){
        this.name = n;
        this.bonusLifePoints = bLP;
    }

    @Override
    public int giveBonus() {
        return this.bonusLifePoints;
    }

    @Override
    public void display() {
        System.out.println(this.name + " : " + this.bonusLifePoints + " points");
    }
}
