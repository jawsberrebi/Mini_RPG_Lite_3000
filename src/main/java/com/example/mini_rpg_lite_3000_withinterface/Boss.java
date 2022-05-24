package com.example.mini_rpg_lite_3000_withinterface;

public class Boss extends Enemy{

    public Boss(int lP){                                                                                                //Constructeur d'un boss : on y spécifie ses points de vie
        this.lifePoints = lP;
    }

    //Méthode d'attaque du boss
    @Override
    public void attack(Hero hero) {
        hero.losingLife(8);
    }

    //Affichage du type de l'ennemi
    @Override
    public String displayType() {
        return "Boss";
    }
}
