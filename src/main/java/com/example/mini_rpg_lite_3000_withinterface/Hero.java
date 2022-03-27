package com.example.mini_rpg_lite_3000_withinterface;
import java.util.ArrayList;
import java.util.List;

public abstract class Hero {

    // Attributs
    protected int lifePoints;
    protected int armor;
    protected int weaponDamage;
    protected boolean isArmorOn;
    protected List<Potion> potions;
    protected List<Food> lembdas;

    public Hero(){
        this.lembdas = new ArrayList<>();
        this.lembdas.add(new Food("Lembda niveau 1", 2));
        this.lembdas.add(new Food("Lembda niveau 1", 2));
        this.lembdas.add(new Food("Lembda niveau 1", 2));
        this.lembdas.add(new Food("Lembda niveau 2", 4));
    }


    //Setters/Getters
    public int getLifePoints() {
        return this.lifePoints;
    }
    public int getArmor() {
        return this.armor;
    }
    public int getWeaponDamage() {
        return this.weaponDamage;
    }
    public void setIsArmorOn(boolean armorOn) {
        this.isArmorOn = armorOn;
    }

    // Méthodes
    public abstract void attack(Enemy enemy, Hero hero);
    public void defend(){
        this.isArmorOn = true;
    }
    public void losingLife(int attack){
        if (this.isArmorOn == true){
            int pointParrage = this.armor;
            this.armor -= attack;
            attack -= pointParrage;

            if (this.armor <= 0){
                this.armor = 0;
                this.lifePoints -= attack;
                if (this.lifePoints < 0){
                    this.lifePoints = 0;
                }
            }
        }else {
            this.lifePoints -= attack;
            if (this.lifePoints < 0){
                this.lifePoints = 0;
            }
        }
    }

    public boolean isDead(){
        if (this.lifePoints <= 0){
            return true;
        }else {
            return false;
        }
    }
    public void healLifePoints(int healLifePoints) {
        this.lifePoints += healLifePoints;
    }
    public void useConsumable(Consumable consumable){
        if (consumable instanceof Food) {
            this.lifePoints += consumable.giveBonus();
        }
    }
    public void displayData(){
        System.out.println("Points de vie : " + getLifePoints());
        System.out.println("Points d'armure : " + getArmor());
        System.out.println("Points de degat : " + getWeaponDamage());
        System.out.println("Consommables actuels :");
        System.out.println("=   FOOD    =");
        for (Food element: lembdas){
            element.display();
        }
    }
    public abstract String displayType();
}
