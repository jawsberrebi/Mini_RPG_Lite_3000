package com.example.mini_rpg_lite_3000_withinterface;

public class Food implements Consumable{

    private String name;                                                                                                //Nom de la potion
    private int bonusLifePoints;                                                                                        //Nombre de points de vie de bonus
    private int level;                                                                                                  //Niveau de la nourriture

    public Food(String n, int bLP){                                                                                     //Constructeur sans définition du niveau de bonus
        this.level = 1;
        this.name = n;                                                                                                  //Définition du nom
        this.bonusLifePoints = bLP;                                                                                     //Ajout de points de vie
    }

    public Food(String n, int bLP , int l){                                                                             //Constructeur avec définition du niveau de bonus
        this.level = l;                                                                                                 //Définition du niveau
        this.name = n;                                                                                                  //Définition du nom
        this.bonusLifePoints = bLP;                                                                                     //Ajout de points de points de vie
    }

    //Méthode retournant le nombre de points de vie que confère la nourriture
    @Override
    public int giveBonus() {
        return this.bonusLifePoints;
    }

    //Méthode retournant les informations de la nourriture sous forme de chaîne de caractère
    @Override
    public String display() {
        return this.name + " niveau " +  this.level + " : " + this.bonusLifePoints + " points";
    }

    //Méthode ajoutant définissant les points bonus de la nourriture et augmentant son niveau
    @Override
    public void addBonusPoints(int bonusPoints) {
        this.bonusLifePoints = bonusPoints;
        this.level++;
    }
}