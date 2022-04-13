package com.example.mini_rpg_lite_3000_withinterface;

public class Healer extends SpellCaster{

    public Healer(){
        super();
        this.lifePoints = 5;
        this.armor = 5;
        this.weaponDamage = 2;
        this.manaPoints = 10;
        this.costManaPoints = 4;
        this.isArmorOn = false;
    }

    public Healer(int lP, int a, int wD, int mP, int cMP){
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
        if ((this.manaPoints > 0) && (this.manaPoints >= this.costManaPoints)){
            this.manaPoints -= this.costManaPoints;
            hero.healLifePoints(this.weaponDamage);
            if (this.manaPoints < 0){
                this.manaPoints = 0;
            }
            System.out.println("Le personnage a ete gueri");
        }else {
            this.manaPoints = 0;
            System.out.println("Vous n'avez pas assez de mana !");
        }
    }

    public void attack(Enemy enemy, Hero hero) {
        spelling(null, hero);
    }

    @Override
    public void losingLife(int attack) {
        super.losingLife(attack);
        if (this.lifePoints <= 0){
            this.lifePoints = 0;
            System.out.println("Le Healer est mort !");
        }
    }

    @Override
    public String displayData() {
        return "====INFORMATIONS SUR LE HEALER====" + "\n" +
        super.displayData();
    }

    @Override
    public String displayType() {
        return "Healer";
    }
}
