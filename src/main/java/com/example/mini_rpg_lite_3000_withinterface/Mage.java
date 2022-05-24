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

    public Mage(int lP, int a, int wP, int mP , int cMP){                                                               //Constructeur utilisé si on souhait construire un mahe avec des paramètres spécifiques
        super();
        this.lifePoints = lP;
        this.armor = a;
        this.weaponDamage = wP;
        this.manaPoints = mP;
        this.costManaPoints = cMP;
        this.isArmorOn = false;
    }

    //Méthode d'attaque du mage
    @Override
    public void attack(Enemy enemy, Hero hero) {                                                                        //Attaque du mage : il lance un sort contre l'ennemi
        spelling(enemy, null);
    }

    @Override
    public void spelling(Enemy enemy, Hero hero) {
        if ((this.manaPoints > 0) && (this.manaPoints >= this.costManaPoints)){
            this.manaPoints -= this.costManaPoints;
            enemy.losingLife(this.weaponDamage);
            if (this.manaPoints < 0){
                this.manaPoints = 0;
            }
        }else {
            this.manaPoints = 0;
        }
    }

    @Override
    public void losingLife(int attack) {
        super.losingLife(attack);
        if (this.lifePoints <= 0){
            this.lifePoints = 0;
        }
    }

    @Override
    public String displayData() {
        return "====INFORMATIONS SUR LE MAGE====" + "\n" +
        super.displayData();
    }

    @Override
    public String displayType() {
        return "Mage";
    }
}
