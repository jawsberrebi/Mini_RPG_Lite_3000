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
        this.lembdas.add(new Food("Lembda", 2));
        this.lembdas.add(new Food("Lembda", 2));
        this.lembdas.add(new Food("Lembda", 2));
        this.lembdas.add(new Food("Lembda", 4, 2));
    }


    //Setters/Getters
    public int getLifePoints() {
        return this.lifePoints;
    }
    public int getArmor() {
        return this.armor;
    }
    public void setArmor(int armor) {
        this.armor = armor;
    }
    public void setWeaponDamage(int weaponDamage) {
        this.weaponDamage = weaponDamage;
    }
    public int getWeaponDamage() {
        return this.weaponDamage;
    }
    public void setIsArmorOn(boolean armorOn) {
        this.isArmorOn = armorOn;
    }
    public boolean getIsArmorOn(){
        return this.isArmorOn;
    }
    public List<Food> getLembdas() {
        return this.lembdas;
    }
    public List<Potion> getPotions() {
        return this.potions;
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
    public void useConsumable(int indexConsumable, boolean isFood){
        if (isFood){
            this.lifePoints += this.lembdas.get(indexConsumable).giveBonus();
            System.out.println(this.lembdas.get(indexConsumable).giveBonus());
            this.lembdas.remove(indexConsumable);
        }
    }
    public String displayData(){
        StringBuilder food = new StringBuilder();
        for (Food lembda : this.lembdas) {
            food.append(lembda.display()).append("\n");
        }
        return "Points de vie : " + getLifePoints() + "\n" +
                "Points d'armure : " + getArmor() + "\n" +
                "Points de degat : " + getWeaponDamage() + "\n" +
                "Consommables actuels :" + "\n" + food;


    }
    public abstract String displayType();
}
