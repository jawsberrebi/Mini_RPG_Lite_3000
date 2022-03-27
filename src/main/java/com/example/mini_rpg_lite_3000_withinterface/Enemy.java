package com.example.mini_rpg_lite_3000_withinterface;

public abstract class Enemy {

    protected int lifePoints;

    //Getters/Setters
    public int getLifePoints() {
        return this.lifePoints;
    }
    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    //Méthodes
    public abstract void attack(Hero hero);

    public void losingLife(int attack){
        this.lifePoints -= attack;

        if (this.lifePoints < 0){
            this.lifePoints = 0;
        }
    }
    public void displayData(){
        System.out.println("Points de vie : " + this.lifePoints);
    }
    public boolean isDead(){
        if (this.lifePoints <= 0){
            return true;
        }else {
            return false;
        }
    }
}