package com.example.mini_rpg_lite_3000_withinterface;

public class BasicEnemy extends Enemy{

    public BasicEnemy(int lP){
        this.lifePoints = lP;
    }

    @Override
    public void attack(Hero hero) {
        hero.losingLife(3);
    }

}
