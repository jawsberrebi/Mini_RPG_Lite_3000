package com.example.mini_rpg_lite_3000_withinterface;

public class Warrior extends Hero{                                                                                      //La classe warrior hérite de la classe hero

    public Warrior(){                                                                                                   //Pour construire un warrior, on réutilise le code de la classe mère supérieure et on y spécifie des paramètres de base
        super();
        this.lifePoints = 10;
        this.armor = 5;
        this.weaponDamage = 5;
        this.isArmorOn = false;
    }

    public Warrior(int lP, int a, int wD){                                                                              //Constructeur utilisé si on souhaite construire un warrior avec des paramètres spécifiques
        super();
        this.lifePoints = lP;
        this.armor = a;
        this.weaponDamage = wD;
        this.isArmorOn = false;
    }

    //Méthode d'attaque du warrior
    @Override
    public void attack(Enemy enemy, Hero hero) {
        enemy.losingLife(this.weaponDamage);                                                                            //L'attaque du warrior fait perdre de la vie à l'ennemi
    }

    //Méthode du warrior utilisée quand il perd de la vie
    @Override
    public void losingLife(int attack) {
        super.losingLife(attack);                                                                                       //On hérite du code de la classe supérieure
        if (this.lifePoints <= 0){                                                                                      //(Utilisé dans le cas d'un test)
            this.lifePoints = 0;
        }
    }

    //Méthode pour retourner sous forme de chaîne de caractères les informations du warrior
    @Override
    public String displayData() {
        return "====INFORMATIONS SUR LE WARRIOR====" + "\n" +
        super.displayData();                                                                                            //Héritage du code de la classe supérieure
    }

    //Méthode pour retourner le type du héros
    @Override
    public String displayType() {
        return "Warrior";
    }

    @Override
    public void enhance(int enhanceBonus) {}
}