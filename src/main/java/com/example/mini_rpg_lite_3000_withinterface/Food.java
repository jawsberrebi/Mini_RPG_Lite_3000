package com.example.mini_rpg_lite_3000_withinterface;

public class Food implements Consumable{

    private String name;
    private int bonusLifePoints;
    private int level;

    public Food(String n, int bLP){
        this.level = 1;
        this.name = n;
        this.bonusLifePoints = bLP;
    }

    public Food(String n, int bLP , int l){
        this.level = l;
        this.name = n;
        this.bonusLifePoints = bLP;
    }

    @Override
    public int giveBonus() {
        return this.bonusLifePoints;
    }

    @Override
    public String display() {
        return this.name + " niveau " +  this.level + " : " + this.bonusLifePoints + " points";
    }

    @Override
    public void addBonusPoints(int bonusPoints) {
        this.bonusLifePoints = bonusPoints;
        this.level++;
    }

}
