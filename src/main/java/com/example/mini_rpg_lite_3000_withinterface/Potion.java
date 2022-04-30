package com.example.mini_rpg_lite_3000_withinterface;

public class Potion implements Consumable{

    private String name;
    private int manaPoints;
    private int level;

    public Potion(String n, int mP){
        this.level = 1;
        this.name = n;
        this.manaPoints = mP;
    }

    public Potion(String n, int mP, int l){
        this.level = l;
        this.name = n;
        this.manaPoints = mP;
    }

    @Override
    public int giveBonus() {
        return this.manaPoints;
    }

    @Override
    public String display() {
        return this.name + " niveau " + this.level + " : " + this.manaPoints + " points";
    }

    @Override
    public void addBonusPoints(int bonusPoints) {
        this.manaPoints = bonusPoints;
        this.level++;
    }

}
