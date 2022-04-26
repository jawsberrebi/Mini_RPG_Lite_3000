package com.example.mini_rpg_lite_3000_withinterface;

public class Potion implements Consumable{

    private String name;
    private int manaPoints;

    public Potion(String n, int mP){
        this.name = n;
        this.manaPoints = mP;
    }

    @Override
    public int giveBonus() {
        return this.manaPoints;
    }

    @Override
    public String display() {
        return this.name + " : " + this.manaPoints + " points";
    }
}
