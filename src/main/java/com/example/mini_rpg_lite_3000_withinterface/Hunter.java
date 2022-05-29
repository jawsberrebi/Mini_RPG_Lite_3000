package com.example.mini_rpg_lite_3000_withinterface;

public class Hunter extends Hero{                                                                                       //La classe hunter hérite de la classe hero

    private int arrows;                                                                                                 //Nombre de flèches

    public Hunter(){                                                                                                    //Pour construire un hunter, on réutilise le code de la classe mère supérieure et on y spécifie des paramètres de base
        super();
        this.lifePoints = 15;
        this.armor = 5;
        this.weaponDamage = 2;
        this.arrows = 10;
        this.isArmorOn = false;
    }

    public Hunter(int lP, int armor, int wD, int a){                                                                    //Constructeur utilisé si on souhaite construire un hunter avec des paramètres spécifiques
        super();
        this.lifePoints = lP;
        this.armor = armor;
        this.weaponDamage = wD;
        this.arrows = a;
        this.isArmorOn = false;
    }

    //Getters/Setters
    public int getArrows() {
        return this.arrows;
    }


    //Méthode d'attaque du hunter
    @Override
    public void attack(Enemy enemy, Hero hero) {
        if ((this.arrows > 0) && (this.lifePoints > 0)){                                                                //S'il reste encore des flèches au hunter et s'il est encore en vie
            this.arrows--;                                                                                              //On décrémente le nombre de flèches
            enemy.losingLife(this.weaponDamage);                                                                        //L'attaque du hunter fait perdre de la vie à l'ennemi
        }
    }

    //Méthode du hunter utilisée quand il perd de la vie
    @Override
    public void losingLife(int attack) {
        super.losingLife(attack);                                                                                       //On hérite du code de la classe supérieure
        if (this.lifePoints <= 0){                                                                                      //(Utilisé dans le cas d'un test)
            this.lifePoints = 0;
        }
    }

    //Méthode pour retourner sous forme de chaîne de caractères les informations du hunter
    @Override
    public String displayData() {
        return "====INFORMATIONS SUR LE HUNTER====" + "\n" +
        super.displayData() +                                                                                           //Héritage du code de la classe supérieure
        "Nombre de fleches : " + this.arrows;
    }

    //Méthode pour retourner le type du héros
    @Override
    public String displayType() {
        return "Hunter";
    }

    //Méthode utilisée dans le cas des récompenses pour ajouter des flèches au hunter
    @Override
    public void enhance(int enhanceBonus) {
        this.arrows += enhanceBonus;
    }
}