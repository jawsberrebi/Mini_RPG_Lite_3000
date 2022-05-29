package com.example.mini_rpg_lite_3000_withinterface;

public interface Consumable {
    public abstract int giveBonus();                                                                                    //Méthode pour retourner des points bonus
    public abstract String display();                                                                                   //Méthode pour renvoyer des informations sur le consommable en chaîne de caractère
    public abstract void addBonusPoints(int bonusPoints);                                                               //Ajout de points bonus au consommable
}
