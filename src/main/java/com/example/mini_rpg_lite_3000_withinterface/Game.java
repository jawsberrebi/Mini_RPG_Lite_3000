package com.example.mini_rpg_lite_3000_withinterface;

import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    public static Game context;                                                                                         //Contexte du jeu, qui évoluera en fonction du déroulement du jeu
    private static List<Hero> heroes = new ArrayList<Hero>();                                                           //Liste des héros
    private List<Enemy> enemies = new ArrayList<Enemy>();                                                                   //Liste des ennemis
    private int playerTurn;
    public com.example.mini_rpg_lite_3000_withinterface.utils.InputParser InputParser;                                  //Parser pour la console
    private int lifePointBasicEnemy;                                                                                    //Points de vie de l'ennemi basique qui augmentera au fil des combats
    private int lifePointBoss;                                                                                          //Points de vie du boss qui augmentera au fil des combats
    private static int combatNumber;                                                                                    //Numéro du combat
    private static int armorRewardPoints;                                                                               //Nombre de points d'armure ajoutés dans le cas d'une récompense après une victoire (augmente à chaque victoire)
    protected static int currentPositionHero;                                                                           //Position du héros actif (actuel, qui peut agir) dans la liste
    protected static int currentPositionEnemy;                                                                          //Position de l'ennemi actif (actuel, qui peut attaquer) dans la liste
    private static int damagesRewardPoints;                                                                             //Nombre de points de dégât ajoutés dans le cas d'une récompense après une victoire (augmente à chaque victoire)
    private static int consumablesRewardPoints;                                                                         //Nombre de points conférés ajoutés par les consommables dans le cas d'une récompense après une victoire (augmente à chaque victoire)
    private static int consumablesRewardQuantity;                                                                       //Nombre de consommables ajoutés dans le cas d'une récompense après une victoire (augmente à chaque victoire)
    private static int enhanceBonusPoints;                                                                              //Nombre de points ajoutés dans le cas de l'amélioration d'un héros (nombre de flèches pour le hunter, efficacité/réduction de coût des sorts du mage ou healer) après une victoire (augmente à chaque victoire)

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

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public static int getCurrentPositionHero() {
        return currentPositionHero;
    }

    public static int getCurrentPositionEnemy() {
        return currentPositionEnemy;
    }

    //Enumération : État du jeu
    public static enum Status{
        START_COMBAT,                                                                                                   //Statut : début d'un combat
        HERO_TURN,                                                                                                      //Statut : tour du joueur (le joueur peut faire des actions)
        ENEMY_TURN,                                                                                                     //Statut : tour de l'ennemi (l'ennemi peut attaquer)
        REWARDS_TIME,                                                                                                   //Statut : fin du combat, attribution des récompenses aux héros
        END_GAME,                                                                                                       //Statut : fin du jeu, le joueur a perdu
    }
    public static Status status;                                                                                        //Variable de statut mis à jour


    //Méthodes
    //Méthode pour initialiser une nouvelle partie
    public static void playGame(){

        if (Game.context != null){                                                                                      //Permet de ne pas lancer de multiples instances du jeu
            throw new RuntimeException("Merci de ne pas ouvrir plusieurs fois le jeu");
        }

        Game.context = new Game();                                                                                      //Création d'un contexte
        //Initialisation des variables de base : au début, les ennemis basiques ont 5 points de vie, le boss a 50 points de vie
        //On initialise le numéro du combat à 0, les points d'armure et de consommables de récompense à 2, le nombre de
        //Consommables de récompense ajouté à 1, le nombre de points de dégâts de récompense à 3 etc.
        Game.context.lifePointBasicEnemy = 5;
        Game.context.lifePointBoss = 50;
        Game.context.combatNumber = 0;
        Game.context.armorRewardPoints = 2;
        Game.context.damagesRewardPoints = 3;
        Game.context.consumablesRewardPoints = 2;
        Game.context.consumablesRewardQuantity = 1;
        Game.context.enhanceBonusPoints = 2;

        Game.context.status = Status.START_COMBAT;                                                                      //Mise à jour du statut : on débute le combat
    }

    //Méthode pour générer un combat (un groupe d'ennemis) en fonction du nombre dé héros actuel dans l'équipe de héros
    public void generateCombat(){
        //Génération d'un chiffre aléatoire pour la probabilité de tomber sur une équipe d'ennemis qui contient un boss
        Random random = new Random();
        int chanceOfBoss = random.nextInt(4 + 1) + 1;

        //Création des ennemis
        for (int i = 0; i < this.heroes.size(); i++){
            this.enemies.add(i, new BasicEnemy(this.lifePointBasicEnemy));
        }

        //Probabilité d'obtenir un boss dans un groupe : on a une chance sur trois de tomber sur un boss
        if (chanceOfBoss == 3){
            System.out.println(this.enemies.size());
            if (this.enemies.size() < 3){
                this.enemies.set(this.enemies.size() -1, new Boss(this.lifePointBoss));
            }else{
                this.enemies.set(this.enemies.size() - 2, new Boss(this.lifePointBoss));
            }
            this.lifePointBoss += 5;                                                                                    //À chaque fois qu'on tombe sur un boss, le nombre de points de vie boss suivant augmente de 5 pour avoir des ennemis plus difficiles à battre
        }

        //Incrementation des points de vie des ennemis pour les prochains combats, les ennemis basiques ont plus de vie de combat en combat
        this.lifePointBasicEnemy++;
    }

    //Méthode pour créer le groupe de héros du joueur
    public static void createHeroGroup(int heroesNumber, ListView<String> selectedHeroes){
        //On transmet le nombre de héros du groupe à créer, pour l'ajout des héros
        for (int i = 0; i < heroesNumber; i++){
            String heroType = (String) selectedHeroes.getItems().get(i);                                                //Récupération du type de héros sélectionné par le joueur
            //Ajout du héros dans la liste de héros en fonction de la sélection du joueur dans la liste d'affichage
            switch (heroType) {
                case "Hunter" -> {
                    Game.context.heroes.add(i, new Hunter());
                }
                case "Healer" -> {
                    Game.context.heroes.add(i, new Healer());
                }
                case "Mage" -> {
                    Game.context.heroes.add(i, new Mage());
                }
                case "Warrior" -> {
                    Game.context.heroes.add(i, new Warrior());
                }
            }
        }
    }

    //Méthode pour l'attaque d'un héros
    public static boolean attack(int indexHeroToHeal){
        if(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof Healer){                      //S'il s'agit d'une attaque de healer, on est dans un cas différent
            //Le healer n'attaque pas d'ennemi (sinon il le guérirait) mais "attaque" un héros pour le guérir (voir la méthode dans la classe Healer)
            Game.context.heroes.get(Game.context.currentPositionHero).attack(null, Game.context.heroes.get(indexHeroToHeal));
            return false;                                                                                               //Le healer ne tue pas d'ennemi, car il ne fait aucun dégât
        }else{                                                                                                          //Pour les autres héros, on attaque l'ennemi en lui faisant des dégâts
            //Le héros actuel attaque l'ennemi selon sa méthode surchargée dans sa classe
            Game.context.heroes.get(Game.context.currentPositionHero).attack(Game.context.enemies.get(Game.context.currentPositionEnemy), null);
            if (Game.context.enemies.get(Game.context.currentPositionEnemy).isDead()){                                  //Si l'ennemi attaqué est mort
                Game.context.enemies.remove(Game.context.currentPositionEnemy);                                         //On supprime l'ennemi de la liste d'ennemis du combat actuel
                if (Game.context.enemies.isEmpty()){                                                                    //Si la liste d'ennemis est vide
                    Game.context.status = Status.REWARDS_TIME;                                                          //Le combat est gagné, et on passe au moment des récompenses de fin de combat
                }else {                                                                                                 //Sinon
                    Random random = new Random();
                    Game.context.status = Status.ENEMY_TURN;                                                            //C'est au tour de l'ennemi
                    Game.context.currentPositionEnemy = random.nextInt(Game.context.enemies.size());                    //On sélectionne un nouvel ennemi qui attaquera au hasard dans la liste
                }
                return true;                                                                                            //L'ennemi est mort, on retourne bien que l'attaque a réussi à le tuer (true)
            }else {
                Game.context.status = Status.ENEMY_TURN;                                                                //Sinon c'est au tour de ce même ennemi
                return false;                                                                                           //L'attaque n'a pas réussi à tuer le robot (false)
            }
        }

    }

    //Méthode pour l'attaque de l'ennemi
    public static boolean attackEnemy(){
        //L'ennemi actuel attaque le héros actuel
        Game.context.enemies.get(Game.context.currentPositionEnemy).attack(Game.context.heroes.get(Game.context.currentPositionHero));
        if (!Game.context.heroes.get(Game.context.currentPositionHero).isDead()){                                       //Si le héros actuel n'est pas mort
            Game.context.status = Status.HERO_TURN;                                                                     //C'est au tour de ce héros
            return false;                                                                                               //L'attaque n'a pas réussi à tuer le héros (false)
        } else {
            Game.context.heroes.remove(Game.context.currentPositionHero);                                               //Sinon, si le héros est mort, on le supprime de la liste des héros
            Random random = new Random();
            if (!Game.context.heroes.isEmpty()) {                                                                       //S'il reste encore un ou des héros dans la liste de hérps
                Game.context.currentPositionHero = random.nextInt(0, Game.context.heroes.size());                 //On choisit un prochain héros actuel (actif) de manière aléatoire dans la liste de héros
                Game.context.status = Status.HERO_TURN;                                                                 //C'est au tour de ce héros
            } else {
                Game.context.status = Status.END_GAME;                                                                  //Sinon, si tous les héros du groupe sont morts, c'est la fin du jeu
            }
            return true;                                                                                                //L'attaque a réussi à tuer le héros (true)
        }
    }

    //Méthode pour que le héros active son armure
    public static void defend(){
        Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).defend();                                   //Le héros met son armure
        Game.context.status = Status.ENEMY_TURN;                                                                        //C'est au tour de l'ennemi
    }

    //Méthode pour retirer les armures de tous les héros du groupe, cette méthode est utilisée avant chaque combat
    public static void undefendAll(){
        for(int i = 0; i < Game.context.getHeroes().size(); i++){
            Game.context.getHeroes().get(i).setIsArmorOn(false);
        }
    }

    //Méthode pour savoir si l'armure est activée
    public static boolean isArmorOn(){
        if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getIsArmorOn()){
            if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getArmor() > 0){
                return true;                                                                                            //Si l'armure est bien mise et qu'il reste encore des points d'armure, l'armure est bien active
            }else {
                return false;                                                                                           //Si l'armure est bien mise et qu'il ne reste pas de points d'armure, l'armure n'est pas active
            }
        }else {
            return false;                                                                                               //Si l'armure n'est pas mise, on retourne false
        }
    }

    //Méthode utilisée dans le cas des récompenses après une victoire pour augmenter le nombre de points d'armure
    public static void enhanceArmor(int cursorHero){
        Game.context.getHeroes().get(cursorHero).setArmor(Game.context.getHeroes().get(cursorHero).getArmor() + Game.context.armorRewardPoints);
        Game.context.armorRewardPoints += 2;                                                                            //À chaque victoire, le nombre de points d'armure ajouté augmente
    }

    //Méthode utilisée dans le cas des récompenses après une victoire pour augmenter le nombre de points de dégâts du héros
    public static void enhanceDamages(int cursorHero){
        Game.context.getHeroes().get(cursorHero).setWeaponDamage(Game.context.getHeroes().get(cursorHero).getWeaponDamage() + Game.context.damagesRewardPoints);
        Game.context.damagesRewardPoints += 3;                                                                          //Idem (voir la méthode précédente)
    }

    //Méthode utilisée dans le cas des récompenses après une victoire pour augmenter le nombre de points que confère les consommables
    public static void enhanceConsumables(int cursorHero){
        if (Game.context.getHeroes().get(cursorHero) instanceof SpellCaster){                                           //S'il s'agit d'un mage ou d'un healer
            for (int u = 0; u < Game.context.getHeroes().get(cursorHero).getPotions().size(); u++){                     //On améliore ses potions
                Game.context.getHeroes().get(cursorHero).getPotions().get(u).addBonusPoints(Game.context.getHeroes().get(cursorHero).getPotions().get(u).giveBonus() + Game.context.consumablesRewardPoints);
            }
        }else {                                                                                                         //Sinon on améliore la nourriture du héros
            for (int u = 0; u < Game.context.getHeroes().get(cursorHero).getLembdas().size(); u++){
                Game.context.getHeroes().get(cursorHero).getLembdas().get(u).addBonusPoints(Game.context.getHeroes().get(cursorHero).getLembdas().get(u).giveBonus() + Game.context.consumablesRewardPoints);
            }
        }
        Game.context.consumablesRewardPoints++;                                                                         //Idem (voir la méthode précédente)
    }

    //Méthode utilisée dans le cas des récompenses après une victoire pour augmenter la quantité de nourriture
    public static void enhanceQuantityFood(int cursorHero){
        for (int u = 0; u < Game.context.consumablesRewardQuantity; u++){                                               //On ajoute les nouveaux aliments autant de fois que l'attribut "consumablesRewardQuantity" l'indique
            Game.context.getHeroes().get(cursorHero).getLembdas().add(new Food("Lembas", 6,3));
        }
        Game.context.consumablesRewardQuantity++;                                                                       //Idem (voir la méthode précédente)
    }

    //Méthode utilisée dans le cas des récompenses après une victoire pour augmenter la quantité de potions
    public static void enhanceQuantityPotions(int cursorHero){
        for (int u = 0; u < Game.context.consumablesRewardQuantity; u++){
            if(Game.context.getHeroes().get(cursorHero) instanceof SpellCaster){                                        //On ajoute les nouvelles potions autant de fois que l'attribut "consumablesRewardQuantity" l'indique dans le cas d'un mage ou d'un healer
                Game.context.getHeroes().get(cursorHero).getPotions().add(new Potion("Elixir", 6, 2));
            }
        }
        Game.context.consumablesRewardQuantity++;                                                                       //Idem (voir la méthode précédente)
    }

    //Méthode utilisée dans le cas des récompenses après une victoire pour augmenter ne nombre de flèches du hunter dans le cas du hunter ou l'efficacité du sort dans le cas du mage ou du healer
    public static void enhanceHero(int cursorHero){
        Game.context.getHeroes().get(cursorHero).enhance(Game.context.enhanceBonusPoints);
        Game.context.enhanceBonusPoints += 2;                                                                           //Idem (voir la méthode précédente)
    }

    //Méthode utilisée dans le cas des récompenses après une victoire pour réduire le coût du mana des sorts dans le cas du mage ou du healer
    public static void reduceManaCost(int cursorHero){
        if(Game.context.getHeroes().get(cursorHero) instanceof SpellCaster){
            ((SpellCaster) Game.context.getHeroes().get(cursorHero)).reduceManaCost();
        }
    }
}