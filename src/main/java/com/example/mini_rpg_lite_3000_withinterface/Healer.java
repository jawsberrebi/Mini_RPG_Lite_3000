package com.example.mini_rpg_lite_3000_withinterface;

public class Healer extends SpellCaster{                                                                                //La classe healer hérite de la classe magicien

    public Healer(){                                                                                                    //Pour construire un healer, on réutilise le code de la classe mère supérieure et on y spécifie des paramètres de base
        super();
        this.lifePoints = 5;
        this.armor = 5;
        this.weaponDamage = 2;
        this.manaPoints = 10;
        this.costManaPoints = 4;
        this.isArmorOn = false;
    }

    public Healer(int lP, int a, int wD, int mP, int cMP){                                                              //Constructeur utilisé si on souhaite construire un healer avec des paramètres spécifiques
        super();
        this.lifePoints = lP;
        this.armor = a;
        this.weaponDamage = wD;
        this.manaPoints = mP;
        this.costManaPoints = cMP;
        this.isArmorOn = false;
    }

    @Override
    public void spelling(Enemy enemy, Hero hero) {
        if ((this.manaPoints > 0) && (this.manaPoints >= this.costManaPoints)){                                         //Si le healer a encore des points de healer et si le nombre de points de mana est suffisant par rapport au coût d'un sort en mana
            this.manaPoints -= this.costManaPoints;                                                                     //On soustrait le nombre de points de mana avec le coût en mana
            hero.healLifePoints(this.weaponDamage);                                                                     //Le héros se fait guérir par le healer
            if (this.manaPoints < 0){                                                                                   //Si le nombre de points de mana a été totalement épuisé jusqu'à en devenir négatif
                this.manaPoints = 0;                                                                                    //On met le nombre de points de mana à 0
            }
        }else {
            this.manaPoints = 0;                                                                                        //Sinon, met le nombre de points de mana à 0
        }
    }

    //Méthode d'attaque du healer, qui lance un sort
    public void attack(Enemy enemy, Hero hero) {
        spelling(null, hero);
    }

    //Méthode du healer utilisée quand il perd de la vie
    @Override
    public void losingLife(int attack) {
        super.losingLife(attack);                                                                                       //On hérite du code de la classe supérieure
        if (this.lifePoints <= 0){                                                                                      //(Utilisé dans le cas d'un test)
            this.lifePoints = 0;
        }
    }

    //Méthode pour retourner sous forme de chaîne de caractères les informations du healer
    @Override
    public String displayData() {
        return "====INFORMATIONS SUR LE HEALER====" + "\n" +
        super.displayData();                                                                                            //Héritage du code de la classe supérieure
    }

    //Méthode pour retourner le type du héros
    @Override
    public String displayType() {
        return "Healer";
    }
}