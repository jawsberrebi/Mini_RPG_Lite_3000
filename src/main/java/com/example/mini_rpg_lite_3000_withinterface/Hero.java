package com.example.mini_rpg_lite_3000_withinterface;
import java.util.ArrayList;
import java.util.List;

public abstract class Hero {

    // Attributs
    protected int lifePoints;                                                                                           //Points de vie du héros
    protected int armor;                                                                                                //Points d'armure du héros
    protected int weaponDamage;                                                                                         //Points de dégât de l'arme du héros
    protected boolean isArmorOn;                                                                                        //Booléen indiquant si le héros porte l'armure ou non
    protected List<Potion> potions;                                                                                     //Liste des potions du héros
    protected List<Food> lembdas;                                                                                       //Liste de la nourriture du héros

    public Hero(){                                                                                                      //Constructeur du héros
        this.lembdas = new ArrayList<>();                                                                               //On créée une nouvelle liste de consommables et pn ajoute consommables de départ
        this.lembdas.add(new Food("Lembas", 2));
        this.lembdas.add(new Food("Lembas", 2));
        this.lembdas.add(new Food("Lembas", 2));
        this.lembdas.add(new Food("Lembas", 4, 2));
    }


    //Setters/Getters des attributs cités en haut
    public int getLifePoints() {
        return this.lifePoints;
    }
    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
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
    public void setLembdas(List<Food> lembdas) {
        this.lembdas = lembdas;
    }
    public List<Potion> getPotions() {
        return this.potions;
    }

    // Méthodes
    public abstract void attack(Enemy enemy, Hero hero);                                                                //Attaque du héros, méthodes qui change selon le type de héros
    public void defend(){                                                                                               //Le héros met son armure
        this.isArmorOn = true;
    }
    public void losingLife(int attack){                                                                                 //Méthode activée quand le héros est attaqué, avec en entrée le nombre de points d'attaque envoyé par l'ennemi
        if (this.isArmorOn){                                                                                            //Dans le cas où l'armure est activée
            int pointParrage = this.armor;                                                                              //Le nombre de points d'armure servira à parer l'attaque de l'ennemi
            this.armor -= attack;                                                                                       //Les points d'armure diminuent sous l'effet de l'attaque
            attack -= pointParrage;                                                                                     //Mais les points d'attaque utilisée sur l'armure diminuent aussi, car ils ont été utilisés sur l'armure au préalable

            if (this.armor <= 0){                                                                                       //Si l'armure n'as plus de points d'armure
                this.armor = 0;                                                                                         //Elle est mise à 0 (pour éviter les points d'armure négatifs)
                this.lifePoints -= attack;                                                                              //Sous l'effet des points d'attaque, la vie du héros diminue
                if (this.lifePoints < 0){                                                                               //Si le héros a perdu tous ses points de vie
                    this.lifePoints = 0;                                                                                //Ses points de vie sont mis à 0
                }
            }
        }else {                                                                                                         //Dans le cas échéant, où l'armure n'a pas été mise
            this.lifePoints -= attack;                                                                                  //Les points d'attaque envoyés par l'ennemi diminuent les points de vie du héros
            if (this.lifePoints < 0){                                                                                   //Si le héros a perdu tous ses points de vie
                this.lifePoints = 0;                                                                                    //Ses points de vie sont mis à 0
            }
        }
    }

    //Méthode renvoyant un booléen disant si le héros est mort ou pas
    public boolean isDead(){
        if (this.lifePoints <= 0){                                                                                      //Si son nombre de points de vie est inférieur ou égal à 0
            return true;                                                                                                //On retourne vrai ; le héros est mort
        }else {
            return false;                                                                                               //Sinon le héros n'est pas mort
        }
    }

    //Méthode permettant de guérir un héros, par exemple par une attaque de healer ou par de la nourriture
    public void healLifePoints(int healLifePoints) {
        this.lifePoints += healLifePoints;
    }

    //Méthode exécutée lors de l'utilisation d'un consommable, on y spécifie, l'index de la liste du consommable et s'il s'agit de nourriture ou de potion (par un booléen)
    public void useConsumable(int indexConsumable, boolean isFood){
        if (isFood){                                                                                                    //S'il s'agit de nourriture
            this.lifePoints += this.lembdas.get(indexConsumable).giveBonus();                                           //Le consommable sélectionné par le joueur ajoute de la vie au héros
            this.lembdas.remove(indexConsumable);                                                                       //Comme le consommable a été consommé, on le retire de la liste
        }
    }

    //Cette méthode sert à afficher toutes les informations d'un héros sous forme de chaîne de caractère (ses points de vie, ses points d'amure, ses points de dégât d'arme et la liste des consommables actuels
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
    public abstract String displayType();                                                                               //Cette méthode affiche le type du héros : warrior, hunter, healer ou mage

    public abstract void enhance(int enhanceBonus);                                                                     //Cette méthode améliore les héros hunter, mage et healer (augmente le nombre de flèches ou réduit le coût du mana)
}
