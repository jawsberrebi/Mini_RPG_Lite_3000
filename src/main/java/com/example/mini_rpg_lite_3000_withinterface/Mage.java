package com.example.mini_rpg_lite_3000_withinterface;

public class Mage extends SpellCaster{                                                                                  //La classe mage hérite de la classe magicien

    public Mage(){                                                                                                      //Pour construire un mage, on réutilise le code de la classe mère supérieure et on y spécifie des paramètres de base
        super();
        this.lifePoints = 5;
        this.armor = 5;
        this.weaponDamage = 10;
        this.manaPoints = 20;
        this.costManaPoints = 5;
        this.isArmorOn = false;
    }

    public Mage(int lP, int a, int wP, int mP , int cMP){                                                               //Constructeur utilisé si on souhaite construire un mage avec des paramètres spécifiques
        super();
        this.lifePoints = lP;
        this.armor = a;
        this.weaponDamage = wP;
        this.manaPoints = mP;
        this.costManaPoints = cMP;
        this.isArmorOn = false;
    }

    //Méthode d'attaque du mage, qui lance un sort
    @Override
    public void attack(Enemy enemy, Hero hero) {                                                                        //Attaque du mage : il lance un sort contre l'ennemi
        spelling(enemy, null);
    }

    //Méthode lancement du sort du mage
    @Override
    public void spelling(Enemy enemy, Hero hero) {
        if ((this.manaPoints > 0) && (this.manaPoints >= this.costManaPoints)){                                         //Si le mage a encore des points de mana et si le nombre de points de mana est suffisant par rapport au coût d'un sort en mana
            this.manaPoints -= this.costManaPoints;                                                                     //On soustrait le nombre de points de mana avec le coût en mana
            enemy.losingLife(this.weaponDamage);                                                                        //Le mage fait perdre de la vie à l'ennemi
            if (this.manaPoints < 0){                                                                                   //Si le nombre de points de mana a été totalement épuisé jusqu'à en devenir négatif
                this.manaPoints = 0;                                                                                    //On met le nombre de points de mana à 0
            }
        }else {
            this.manaPoints = 0;                                                                                        //Sinon, met le nombre de points de mana à 0
        }
    }

    //Méthode du mage utilisée quand il perd de la vie
    @Override
    public void losingLife(int attack) {
        super.losingLife(attack);                                                                                       //On hérite du code de la classe supérieure
        if (this.lifePoints <= 0){                                                                                      //(Utilisé dans le cas d'un test)
            this.lifePoints = 0;
        }
    }

    //Méthode pour retourner sous forme de chaîne de caractères les informations du mage
    @Override
    public String displayData() {
        return "====INFORMATIONS SUR LE MAGE====" + "\n" +
        super.displayData();                                                                                            //Héritage du code de la classe supérieure
    }

    //Méthode pour retourner le type du héros
    @Override
    public String displayType() {
        return "Mage";
    }
}