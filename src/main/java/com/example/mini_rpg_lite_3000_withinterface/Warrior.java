package com.example.mini_rpg_lite_3000_withinterface;

public class Warrior extends Hero{

    public Warrior(){
        super();
        this.lifePoints = 10;
        this.armor = 5;
        this.weaponDamage = 5;
        this.isArmorOn = false;
    }

    public Warrior(int lP, int a, int wD){
        super();
        this.lifePoints = lP;
        this.armor = a;
        this.weaponDamage = wD;
        this.isArmorOn = false;
    }

    @Override
    public void attack(Enemy enemy, Hero hero) {
        enemy.losingLife(this.weaponDamage);
    }

    @Override
    public void losingLife(int attack) {
        super.losingLife(attack);
        if (this.lifePoints <= 0){
            this.lifePoints = 0;
            System.out.println("Le Warrior est mort !");
        }
    }

    @Override
    public String displayData() {
        return "====INFORMATIONS SUR LE WARRIOR====" + "\n" +
        super.displayData();
    }

    @Override
    public String displayType() {
        return "Warrior";
    }
}
