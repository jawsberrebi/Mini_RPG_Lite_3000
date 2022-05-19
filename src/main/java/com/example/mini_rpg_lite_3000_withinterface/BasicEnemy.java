package com.example.mini_rpg_lite_3000_withinterface;

public class BasicEnemy extends Enemy{

    public BasicEnemy(int lP){                                                                                          //Constructeur d'un ennemi basique : on y spécifié ses points de vie
        this.lifePoints = lP;
    }

    //Méthode d'attaque de l'ennemi
    @Override
    public void attack(Hero hero) {                                                                                     //L'ennemi basique a trop de points de dégât lors d'une attaque
        hero.losingLife(3);
    }

    //Affichage du type de l'ennemi
    @Override
    public String displayType() {
        return "Ennemi basique";
    }

}
