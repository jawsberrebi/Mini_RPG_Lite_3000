package com.example.mini_rpg_lite_3000_withinterface;

import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    public static Game context;
    private static List<Hero> heroes = new ArrayList<Hero>();
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private int playerTurn;
    public com.example.mini_rpg_lite_3000_withinterface.utils.InputParser InputParser;
    private int lifePointBasicEnemy;
    private int lifePointBoss;
    private static int combatNumber;
    private static int armorRewardPoints;
    protected static int currentPositionHero;
    protected static int currentPositionEnemy;
    private static int damagesRewardPoints;
    private static int consumablesRewardPoints;
    private static int consumablesRewardQuantity;
    private static int enhanceBonusPoints;

    //Getters/Setters
    public static int getCombatNumber() {
        return combatNumber;
    }

    public static List<Hero> getHeroes() {
        return heroes;
    }

    public static void setHeroes(List<Hero> heroes) {
        Game.heroes = heroes;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public static int getCurrentPositionHero() {
        return currentPositionHero;
    }

    public static int getCurrentPositionEnemy() {
        return currentPositionEnemy;
    }

    //Enumération
    public static enum Status{
        START_COMBAT,
        HERO_TURN,
        ENEMY_TURN,
        REWARDS_TIME,
        END_GAME,
    }
    public static Status status;


    //Méthodes
    public static void playGame(){

        if (Game.context != null){
            throw new RuntimeException("Merci de ne pas ouvrir plusieurs fois le jeu");
        }

        Game.context = new Game();
        Game.context.lifePointBasicEnemy = 5;
        Game.context.lifePointBoss = 50;
        Game.context.combatNumber = 0;
        Game.context.armorRewardPoints = 2;
        Game.context.damagesRewardPoints = 3;
        Game.context.consumablesRewardPoints = 2;
        Game.context.consumablesRewardQuantity = 1;
        Game.context.enhanceBonusPoints = 2;

        Game.context.status = Status.START_COMBAT;
    }

    public void generateCombat(){
        System.out.println("Entered");
        Random random = new Random();
        int chanceOfBoss = random.nextInt(4 + 1) + 1;

        //Creation des ennemis
        //Remplacer this.heroes.size par le nombre de héros de départ ?
        for (int i = 0; i < this.heroes.size(); i++){
            this.enemies.add(i, new BasicEnemy(this.lifePointBasicEnemy));
        }

        //Probabilite d'obtenir un boss dans un groupe
        if (chanceOfBoss == 3){
            System.out.println(this.enemies.size());
            if (this.enemies.size() < 3){
                this.enemies.set(this.enemies.size() -1, new Boss(this.lifePointBoss));
            }else{
                this.enemies.set(this.enemies.size() - 2, new Boss(this.lifePointBoss));
            }


            this.lifePointBoss += 10;
        }

        //Incrementation des points de vie des ennemis pour les prochaines parties
        this.lifePointBasicEnemy++;
    }

    public static void createHeroGroup(int heroesNumber, ListView<String> selectedHeroes){
        for (int i = 0; i < heroesNumber; i++){
            String heroType = (String) selectedHeroes.getItems().get(i);
            switch (heroType) {
                case "Hunter" -> {
                    Game.context.heroes.add(i, new Hunter());
                    System.out.println("Hunter selectionne");
                    System.out.println("");
                }
                case "Healer" -> {
                    Game.context.heroes.add(i, new Healer());
                    System.out.println("Healer selectionne");
                    System.out.println("");
                }
                case "Mage" -> {
                    Game.context.heroes.add(i, new Mage());
                    System.out.println("Mage selectionne");
                    System.out.println("");
                }
                case "Warrior" -> {
                    Game.context.heroes.add(i, new Warrior());
                    System.out.println("Warrior selectionne");
                    System.out.println("");
                }
                default -> {
                    System.out.println("Vous n'avez pas entre un nom valide de type. Ressayez");
                    System.out.println("");
                }
            }
        }
    }

    public static boolean attack(int indexHeroToHeal){
        if(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof Healer){
            Game.context.heroes.get(Game.context.currentPositionHero).attack(null, Game.context.heroes.get(indexHeroToHeal));
            return false;
        }else{
            Game.context.heroes.get(Game.context.currentPositionHero).attack(Game.context.enemies.get(Game.context.currentPositionEnemy), null);
            System.out.println("fff");
            if (Game.context.enemies.get(Game.context.currentPositionEnemy).isDead()){
                Game.context.enemies.remove(Game.context.currentPositionEnemy);
                if (Game.context.enemies.isEmpty()){
                    Game.context.status = Status.REWARDS_TIME;
                }else {
                    Random random = new Random();
                    Game.context.status = Status.ENEMY_TURN;
                    Game.context.currentPositionEnemy = random.nextInt(Game.context.enemies.size());
                }
                return true;
            }else {
                Game.context.status = Status.ENEMY_TURN;
                return false;
            }
        }

    }

    public static boolean attackEnemy(){
        Game.context.enemies.get(Game.context.currentPositionEnemy).attack(Game.context.heroes.get(Game.context.currentPositionHero));
        if (!Game.context.heroes.get(Game.context.currentPositionHero).isDead()){
            System.out.println("Fait");
            //Game.context.enemies.get(Game.context.currentPositionEnemy).attack(Game.context.heroes.get(Game.context.currentPositionHero));
            Game.context.status = Status.HERO_TURN;
            return false;
        } else {
            Game.context.heroes.remove(Game.context.currentPositionHero);
            Random random = new Random();
            if (!Game.context.heroes.isEmpty()) {
                Game.context.currentPositionHero = random.nextInt(0, Game.context.heroes.size());
                Game.context.status = Status.HERO_TURN;
            } else {
                Game.context.status = Status.END_GAME;
            }
            return true;
        }
    }

    public static void defend(){
        Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).defend();
        Game.context.status = Status.ENEMY_TURN;
    }

    public static void undefendAll(){
        for(int i = 0; i < Game.context.getHeroes().size(); i++){
            Game.context.getHeroes().get(i).setIsArmorOn(false);
        }
    }

    public static boolean isArmorOn(){
        if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getIsArmorOn()){
            if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getArmor() > 0){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public static void enhanceArmor(int cursorHero){
        /*
        for (int i = 0; i < Game.context.getHeroes().size(); i++){
            Game.context.getHeroes().get(i).setArmor(Game.context.getHeroes().get(i).getArmor() + Game.context.armorRewardPoints);
        }
         */
        Game.context.getHeroes().get(cursorHero).setArmor(Game.context.getHeroes().get(cursorHero).getArmor() + Game.context.armorRewardPoints);
        Game.context.armorRewardPoints += 2;
    }

    public static void enhanceDamages(int cursorHero){
        /*
        for (int i = 0; i < Game.context.getHeroes().size(); i++){
            Game.context.getHeroes().get(i).setWeaponDamage(Game.context.getHeroes().get(i).getWeaponDamage() + Game.context.damagesRewardPoints);
        }
         */
        Game.context.getHeroes().get(cursorHero).setWeaponDamage(Game.context.getHeroes().get(cursorHero).getWeaponDamage() + Game.context.damagesRewardPoints);
        Game.context.damagesRewardPoints += 3;
    }

    public static void enhanceConsumables(int cursorHero){
        if (Game.context.getHeroes().get(cursorHero) instanceof SpellCaster){
            for (int u = 0; u < Game.context.getHeroes().get(cursorHero).getPotions().size(); u++){
                Game.context.getHeroes().get(cursorHero).getPotions().get(u).addBonusPoints(Game.context.getHeroes().get(cursorHero).getPotions().get(u).giveBonus() + Game.context.consumablesRewardPoints);
            }
        }else {
            for (int u = 0; u < Game.context.getHeroes().get(cursorHero).getLembdas().size(); u++){
                Game.context.getHeroes().get(cursorHero).getLembdas().get(u).addBonusPoints(Game.context.getHeroes().get(cursorHero).getLembdas().get(u).giveBonus() + Game.context.consumablesRewardPoints);
            }
        }
        Game.context.consumablesRewardPoints++;
    }

    public static void enhanceQuantityFood(int cursorHero){
        for (int u = 0; u < Game.context.consumablesRewardQuantity; u++){
            Game.context.getHeroes().get(cursorHero).getLembdas().add(new Food("Lembas", 6,3));
        }
        Game.context.consumablesRewardQuantity++;
    }

    public static void enhanceQuantityPotions(int cursorHero){
        for (int u = 0; u < Game.context.consumablesRewardQuantity; u++){
            if(Game.context.getHeroes().get(cursorHero) instanceof SpellCaster){
                Game.context.getHeroes().get(cursorHero).getPotions().add(new Potion("Elixir", 6, 2));
            }
        }
        Game.context.consumablesRewardQuantity++;
    }

    public static void enhanceHero(int cursorHero){
        Game.context.getHeroes().get(cursorHero).enhance(Game.context.enhanceBonusPoints);
        Game.context.enhanceBonusPoints += 2;
    }

    public static void reduceManaCost(int cursorHero){
        if(Game.context.getHeroes().get(cursorHero) instanceof SpellCaster){
            ((SpellCaster) Game.context.getHeroes().get(cursorHero)).reduceManaCost();
        }
    }
}