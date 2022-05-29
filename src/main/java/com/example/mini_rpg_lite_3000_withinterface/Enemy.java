package com.example.mini_rpg_lite_3000_withinterface;

public abstract class Enemy {

    protected int lifePoints;                                                                                           //Points de vie

    //Getters/Setters
    public int getLifePoints() {
        return this.lifePoints;
    }
    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    //Méthode d'attaque de l'ennemi
    public abstract void attack(Hero hero);

    //Méthode du hunter utilisée quand il perd de la vie
    public void losingLife(int attack){
        this.lifePoints -= attack;
        if (this.lifePoints < 0){                                                                                       //Si l'ennemi perd de la vie de sorte à ce que ses points de vie sont négatifs
            this.lifePoints = 0;                                                                                        //On redéfinit ses points de vie à 0
        }
    }

    //Méthode pour retourner sous forme de chaîne de caractères les informations de l'ennemi
    public String displayData(){
        return "Points de vie : " + this.lifePoints;
    }

    //Méthode pour déterminer si l'ennemi est mort (true) ou non (false)
    public boolean isDead(){
        if (this.lifePoints <= 0){
            return true;
        }else {
            return false;
        }
    }

    //Méthode pour retourner le type de l'ennemi
    public abstract String displayType();
}