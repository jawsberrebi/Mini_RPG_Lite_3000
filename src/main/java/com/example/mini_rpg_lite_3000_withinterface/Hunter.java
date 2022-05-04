package com.example.mini_rpg_lite_3000_withinterface;

public class Hunter extends Hero{

    private int arrows;

    public Hunter(){
        super();
        this.lifePoints = 15;
        this.armor = 5;
        this.weaponDamage = 2;
        this.arrows = 10;
        this.isArmorOn = false;
    }

    public Hunter(int lP, int armor, int wD, int a){
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


    //Méthodes
    @Override
    public void attack(Enemy enemy, Hero hero) {
        this.isArmorOn = false;
        if ((this.arrows > 0) && (this.lifePoints > 0)){
            this.arrows--;
            enemy.losingLife(this.weaponDamage);
        }else {
            System.out.println("Ce Hunter n'a plus de fleche !");
        }
    }

    @Override
    public void losingLife(int attack) {
        super.losingLife(attack);
        if (this.lifePoints <= 0){
            this.lifePoints = 0;
            System.out.println("Le Hunter est mort !");
        }
    }

    @Override
    public String displayData() {
        return "====INFORMATIONS SUR LE HUNTER====" + "\n" +
        super.displayData() +
        "Nombre de fleches : " + this.arrows;
    }

    @Override
    public String displayType() {
        return "Hunter";
    }

    @Override
    public void enhance(int enhanceBonus) {
        this.arrows += enhanceBonus;
    }
}
