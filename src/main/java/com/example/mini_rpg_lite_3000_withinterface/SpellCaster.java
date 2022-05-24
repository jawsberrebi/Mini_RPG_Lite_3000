package com.example.mini_rpg_lite_3000_withinterface;

import java.util.ArrayList;

public abstract class SpellCaster extends Hero{                                                                         //La classe magicien hérite de classe héro
    protected int manaPoints;                                                                                           //Nombre de points de mana
    protected int costManaPoints;                                                                                       //Coût en mana pour lancer un sort

    public SpellCaster(){                                                                                               //Constructueur d'un magicien : on hérite des paramètres de la classe héro et on y ajoute des potions
        super();
        this.potions = new ArrayList<>();
        this.potions.add(new Potion("Nectar", 2));
        this.potions.add(new Potion("Nectar", 2));
        this.potions.add(new Potion("Elixir", 5));
    }

    //Getters/Setters
    public int getManaPoints() {
        return this.manaPoints;
    }

    public int getCostManaPoints() {
        return this.costManaPoints;
    }


    //Méthodes
    //Méthode pour lancer un sort
    public abstract void spelling(Enemy enemy, Hero hero);

    //Méthode pour afficher les informations sur le magicien
    @Override
    public String displayData() {
        StringBuilder ptn = new StringBuilder();
        for (Potion potion : this.potions) {
            ptn.append(potion.display()).append("\n");
        }
        return "Points de mana : " + this.manaPoints + "\n" +
                "Cout actuel du mana : " + this.costManaPoints + "\n" +
                super.displayData() + ptn;
    }

    //Méthode pour utiliser un consommable
    @Override
    public void useConsumable(int indexConsumable, boolean isFood) {
        if (isFood){                                                                                                    //S'il s'agit de nourriture
            super.useConsumable(indexConsumable, isFood);                                                               //On réutilise le code de la classe héro
        }else {                                                                                                         //Sinon, il s'agit d'une position
            this.manaPoints += this.potions.get(indexConsumable).giveBonus();                                           //Les potions redonnent du mana au magicien
            this.potions.remove(indexConsumable);                                                                       //La potion a été utilisée, on l'enlève de la liste
        }
    }

    //Méthode pour augmenter les dégâts du magicien
    @Override
    public void enhance(int enhanceBonus){
        this.weaponDamage += enhanceBonus;
    }

    //Méthode pour réduire le coût en mana d'un sort
    public void reduceManaCost(){
        if(this.costManaPoints > 0){
            this.costManaPoints--;
        }
    }
}
