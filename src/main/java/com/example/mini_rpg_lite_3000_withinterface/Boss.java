package com.example.mini_rpg_lite_3000_withinterface;

public class Boss extends Enemy{

    public Boss(int lP){
        this.lifePoints = lP;
    }

    @Override
    public void attack(Hero hero) {
        hero.losingLife(20);
    }

    @Override
    public String displayType() {
        return "Boss";
    }
}
