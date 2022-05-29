package com.example.mini_rpg_lite_3000_withinterface;

public class Potion implements Consumable{

    private String name;                                                                                                //Nom de la potion
    private int manaPoints;                                                                                             //Nombre de points de mana bonus
    private int level;                                                                                                  //Niveau de la potion

    public Potion(String n, int mP){                                                                                    //Constructeur sans définition du niveau de bonus
        this.level = 1;
        this.name = n;                                                                                                  //Définition du nom
        this.manaPoints = mP;                                                                                           //Ajout de points de mana
    }

    public Potion(String n, int mP, int l){                                                                             //Constructeur avec définition du niveau de bonus
        this.level = l;                                                                                                 //Définition du niveau
        this.name = n;                                                                                                  //Définition du nom
        this.manaPoints = mP;                                                                                           //Ajout de points de mana
    }

    //Méthode retournant le nombre de points de mana que confère la potion
    @Override
    public int giveBonus() {
        return this.manaPoints;
    }

    //Méthode retournant les informations de la potion sous forme de chaîne de caractère
    @Override
    public String display() {
        return this.name + " niveau " + this.level + " : " + this.manaPoints + " points";
    }

    //Méthode ajoutant définissant les points bonus de la potion et augmentant son niveau
    @Override
    public void addBonusPoints(int bonusPoints) {
        this.manaPoints = bonusPoints;
        this.level++;
    }
}