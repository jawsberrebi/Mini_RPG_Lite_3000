package com.example.mini_rpg_lite_3000_withinterface;

public class Mage extends SpellCaster{

    public Mage(){
        super();
        this.lifePoints = 5;
        this.armor = 5;
        this.weaponDamage = 10;
        this.manaPoints = 20;
        this.costManaPoints = 5;
        this.isArmorOn = false;
    }

    public Mage(int lP, int a, int wP, int mP , int cMP){
        super();
        this.lifePoints = lP;
        this.armor = a;
        this.weaponDamage = wP;
        this.manaPoints = mP;
        this.costManaPoints = cMP;
        this.isArmorOn = false;
    }

    @Override
    public void attack(Enemy enemy, Hero hero) {
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
            System.out.println("Vous n'assez pas assez de mana !");
        }
    }

    @Override
    public void losingLife(int attack) {
        super.losingLife(attack);
        if (this.lifePoints <= 0){
            this.lifePoints = 0;
            System.out.println("Le Mage est mort !");
        }
    }

    @Override
    public void displayData() {
        System.out.println("====INFORMATIONS SUR LE MAGE====");
        super.displayData();
        System.out.println("===================================");
    }

    @Override
    public String displayType() {
        return "Mage";
    }
}
