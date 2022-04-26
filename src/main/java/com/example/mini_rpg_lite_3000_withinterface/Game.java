package com.example.mini_rpg_lite_3000_withinterface;

import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    public static Game context;
    private static List<Hero> heroes = new ArrayList<Hero>();
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private int playerTurn;
    public com.example.mini_rpg_lite_3000_withinterface.utils.InputParser InputParser;
    private int lifePointBasicEnemy;
    private int lifePointBoss;
    private static int combatNumber;
    protected static int currentPositionHero;
    protected static int currentPositionEnemy;

    //Getters/Setters
    public static int getCombatNumber() {
        return combatNumber;
    }

    public static List<Hero> getHeroes() {
        return heroes;
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


        Game.context.status = Status.START_COMBAT;
        //Game.context.currentPositionHero = random.nextInt(Game.context.heroes.size());
        //Game.context.currentPositionEnemy = random.nextInt(Game.context.enemies.size());

        //Game.context.combatNumber++;
        //Creation d'une nouvelle equipe et affichage des données sur chaque personnage
        //Game.context.createHeroGroup();
        //Game.context.displayTeam();
        /*
        while (!Game.context.heroes.isEmpty()){

            //Generation d'un nouveau combat
            //generateCombat();
            //System.out.println("Combat numero " + this.combatNumber);
            //System.out.println("");
            //System.out.println("Equipe " + this.combatNumber + " d'ennemis");
            //System.out.println("");
            //displayEnemy();
            //System.out.println("");

            //Combat
            Random random = new Random();

            while (!Game.context.enemies.isEmpty()){ //Tant qu'il reste encore des ennemis
                for (int i = 0; i < Game.context.enemies.size(); i++){
                    while (!Game.context.heroes.isEmpty()){
                        Game.context.status = Status.START_COMBAT;
                        int positionJoueur = random.nextInt(Game.context.heroes.size());
                        int positionEnnemi = random.nextInt(Game.context.enemies.size());
                        Game.context.currentPositionHero = random.nextInt(Game.context.heroes.size());
                        Game.context.currentPositionEnemy = random.nextInt(Game.context.enemies.size());

                        //System.out.println(positionEnnemi);
                        //System.out.println("Ennemi numero " + (positionEnnemi + 1) + " se tient devant vous.");
                        /*
                        System.out.print("Votre ");
                        if (this.heroes.get(positionJoueur) instanceof Hunter){
                            System.out.print("Hunter ");
                        }else if (this.heroes.get(positionJoueur) instanceof Healer){
                            System.out.print("Healer ");
                        }else if (this.heroes.get(positionJoueur) instanceof Mage){
                            System.out.print("Mage ");
                        }else if(this.heroes.get(positionJoueur) instanceof Warrior){
                            System.out.print("Warrior ");
                        }

                        System.out.println("se tient pret.");
                         */

                        //Actions du joueur

                        //int userChoice = choice();
                        int userChoice = 0;
                        /*
                        switch (userChoice){
                            // Cas de l'attaque
                            case 1:

                                //Attaque = guerison du healer
                                if (this.heroes.get(positionJoueur) instanceof Healer){
                                    System.out.println("Quel personnage souhaitez-vous guerir ?");

                                    for (int w = 0; w < this.heroes.size(); w++){
                                        if (this.heroes.get(w) instanceof Hunter){
                                            System.out.println("Entrez " + w + " pour le Hunter");
                                        }else if (this.heroes.get(w) instanceof Healer){
                                            System.out.println("Entrez " + w + " pour le Healer");
                                        }else if (this.heroes.get(w) instanceof Mage){
                                            System.out.println("Entrez " + w + " pour le Mage");
                                        }else if(this.heroes.get(w) instanceof Warrior){
                                            System.out.println("Entrez " + w + " pour le Warrior");
                                        }
                                    }

                                    int numberAction = -1;

                                    do {
                                        numberAction = this.InputParser.questionInt("Entrez le numero");
                                        if (numberAction >= this.heroes.size()){
                                            System.out.println("Entree non valide. Entrez le chiffre correspondant a l'action entre 1 et 3");
                                        }
                                    }while (numberAction >= this.heroes.size());

                                    this.heroes.get(positionJoueur).attack(null, this.heroes.get(numberAction));

                                    //Attaque classique
                                }else {
                                    this.heroes.get(positionJoueur).attack(this.enemies.get(positionEnnemi), null);
                                    System.out.println("L'ennemi a ete attaque");
                                    if (this.enemies.get(positionEnnemi).getLifePoints() <= 0){
                                        System.out.println("Vous avez tue cet ennemi");
                                        //this.enemies.remove(positionEnnemi);
                                    }
                                }

                                break;

                            //Cas de la defense
                            case 2:
                                this.heroes.get(positionJoueur).setIsArmorOn(true);
                                System.out.println("Ce personnage a desormais son armure");
                                break;

                            //Cas de l'utilisation d'un consommable
                            case 3:
                                System.out.println("Informations sur le personnage :");
                                Game.context.heroes.get(positionJoueur).displayData();

                                //int resultChoice = choiceConsumable(this.heroes.get(positionJoueur));
                                int resultChoice = 5;
                                //Cas de l'utilisation d'un lembda
                                if (resultChoice == 1){
                                    if (Game.context.heroes.get(positionJoueur).lembdas.isEmpty()){
                                        System.out.println("Votre personnage n'a plus de lembdas !");
                                    }else {
                                        System.out.println("Le personnage dispose des lembdas suivants : ");
                                        for (int w = 0; w < Game.context.heroes.get(positionJoueur).lembdas.size(); w++){
                                            System.out.print(w + ". ");
                                            Game.context.heroes.get(positionJoueur).lembdas.get(w).display();
                                        }

                                        System.out.println("Entrez le chiffre correspondant a l'action");
                                        int resultChoiceLambda;

                                        do {
                                            resultChoiceLambda = Game.context.InputParser.questionInt("Quel lembda souhaitez-vous consommer ?");
                                            if ((resultChoiceLambda < 0) && (resultChoiceLambda > Game.context.heroes.get(positionJoueur).lembdas.size() - 1)){
                                                System.out.println("Entree non valide. Entrez le chiffre correspondant a l'action");
                                            }
                                        }while ((resultChoiceLambda < 0) && (resultChoiceLambda > Game.context.heroes.get(positionJoueur).lembdas.size() - 1));

                                        Game.context.heroes.get(positionJoueur).useConsumable(Game.context.heroes.get(positionJoueur).lembdas.get(resultChoiceLambda));
                                        Game.context.heroes.get(positionJoueur).lembdas.remove(resultChoiceLambda);
                                        System.out.println("Ce héros a consommé un lembda");
                                        Game.context.heroes.get(positionJoueur).displayData();
                                    }

                                }else if(resultChoice == 2){
                                    if (Game.context.heroes.get(positionJoueur).potions.isEmpty()){
                                        System.out.println("Votre personnage n'a plus de potions !");
                                    }else {
                                        System.out.println("Le personnage dispose des potions suivantes : ");
                                        for (int w = 0; w < Game.context.heroes.get(positionJoueur).potions.size(); w++){
                                            System.out.print(w + ". ");
                                            Game.context.heroes.get(positionJoueur).potions.get(w).display();
                                        }

                                        System.out.println("Entrez le chiffre correspondant a l'action");
                                        int resultChoicePotion;

                                        do {
                                            resultChoicePotion = Game.context.InputParser.questionInt("Quelle potion souhaitez-vous consommer ?");
                                            if ((resultChoicePotion < 0) && (resultChoicePotion > Game.context.heroes.get(positionJoueur).potions.size() - 1)){
                                                System.out.println("Entree non valide. Entrez le chiffre correspondant a l'action");
                                            }
                                        }while ((resultChoicePotion < 0) && (resultChoicePotion > Game.context.heroes.get(positionJoueur).potions.size() - 1));

                                        Game.context.heroes.get(positionJoueur).useConsumable(Game.context.heroes.get(positionJoueur).potions.get(resultChoicePotion));
                                        Game.context.heroes.get(positionJoueur).potions.remove(resultChoicePotion);
                                        System.out.println("Ce héros a consommé une potion");
                                        Game.context.heroes.get(positionJoueur).displayData();
                                    }
                                }
                                break;
                        }


                         */
                        /*
                        if (!this.enemies.get(positionEnnemi).isDead()){
                            //Attaque de l'ennemi
                            System.out.println("L'ennemi attaque !");
                            this.enemies.get(positionEnnemi).attack(this.heroes.get(positionJoueur));

                            //Si le personnage meurt, on le supprime de l'equipe
                            if (this.heroes.get(positionJoueur).isDead()){
                                this.heroes.remove(positionJoueur);
                            }
                        }else {
                            this.enemies.remove(positionEnnemi);
                        }

                        if (this.enemies.isEmpty()){
                            break;
                        }

                    }

                    break;

                }

                System.out.println("Votre equipe est morte...");
                break;
            }
            /*
            if (this.enemies.isEmpty()){
                //Si tous les ennemis sont morts, les personnages ont gagné le combat
                System.out.println("Vous avez gagné le combat " + this.combatNumber + " !");

                //Si les personnages ont gagné le combat, il y a une attribution de récompense
                //A remplir...
            }
            break;




        }
        */

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
        //Scanner scanner = new Scanner(System.in);
        //int heroesNumber = this.InputParser.questionInt("Creation d'une nouvelle equipe, combient voulez-vous de heros ?");
        //String heroType = "";

        for (int i = 0; i < heroesNumber; i++){
            String heroType = (String) selectedHeroes.getItems().get(i);
            switch (heroType) {
                case "Hunter" -> {
                    Game.context.heroes.add(i, new Hunter());
                    System.out.println("Hunter selectionne");
                    System.out.println("");
                }
                case "Healer" -> {
                    heroes.add(i, new Healer());
                    System.out.println("Healer selectionne");
                    System.out.println("");
                }
                case "Mage" -> {
                    heroes.add(i, new Mage());
                    System.out.println("Mage selectionne");
                    System.out.println("");
                }
                case "Warrior" -> {
                    heroes.add(i, new Warrior());
                    System.out.println("Warrior selectionne");
                    System.out.println("");
                }
                default -> {
                    System.out.println("Vous n'avez pas entre un nom valide de type. Ressayez");
                    System.out.println("");
                }
            }
        }

        /*
        for (int i = 0; i < heroesNumber; i++){

            do {
                //heroType = this.InputParser.questionString("Quel est le type du hero du hero numero " + i + " ?");
                //System.out.println(heroType);
                switch (heroType) {
                    case "Hunter" -> {
                        Game.context.heroes.add(i, new Hunter());
                        //System.out.println("Hunter selectionne");
                        //System.out.println("");
                    }
                    case "Healer" -> {
                        heroes.add(i, new Healer());
                        System.out.println("Healer selectionne");
                        System.out.println("");
                    }
                    case "Mage" -> {
                        heroes.add(i, new Mage());
                        System.out.println("Mage selectionne");
                        System.out.println("");
                    }
                    case "Warrior" -> {
                        heroes.add(i, new Warrior());
                        System.out.println("Warrior selectionne");
                        System.out.println("");
                    }
                    default -> {
                        System.out.println("Vous n'avez pas entre un nom valide de type. Ressayez");
                        System.out.println("");
                    }
                }
            }while ((!"Hunter".equals(heroType)) && (!"Healer".equals(heroType)) && (!"Mage".equals(heroType)) && (!"Warrior".equals(heroType)));
        }
         */

    }

    public static boolean attack(int indexHeroToHeal){
        if(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof Healer){
            Game.context.heroes.get(Game.context.currentPositionHero).attack(null, Game.context.heroes.get(indexHeroToHeal));
            return false;
        }else{
            Game.context.heroes.get(Game.context.currentPositionHero).attack(Game.context.enemies.get(Game.context.currentPositionEnemy), null);
            if (Game.context.enemies.get(Game.context.currentPositionEnemy).isDead()){
                Game.context.enemies.remove(Game.context.currentPositionEnemy);
                if (Game.context.enemies.isEmpty()){
                    System.out.println("Couscous");
                    Game.context.status = Status.START_COMBAT;
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

    public static void eat(){

    }

    private void displayTeam(){
        for (int i = 0; i < this.heroes.size(); i++){
            this.heroes.get(i).displayData();
        }
    }

    private void displayEnemy(){
        for (int i = 0; i < this.enemies.size(); i++){
            if (this.enemies.get(i) instanceof BasicEnemy){
                System.out.print("Ennemi basique : ");
                this.enemies.get(i).displayData();
            }else if (this.enemies.get(i) instanceof Boss){
                System.out.print("Boss : ");
                this.enemies.get(i).displayData();
            }
        }
    }

    private int choice(){
        System.out.println("Liste d'actions possibles : ");
        System.out.println("1. Attaquer l'ennemi");
        System.out.println("2. Se defendre");
        System.out.println("3. Utiliser un consommable");
        System.out.println("Entrez le chiffre correspondant a l'action");
        int numberAction = 0;

        do {
            numberAction = this.InputParser.questionInt("Que souhaitez-vous faire ?");
            if ((numberAction < 1) && (numberAction > 3)){
                System.out.println("Entree non valide. Entrez le chiffre correspondant a l'action entre 1 et 3");
            }
        }while ((numberAction < 1) && (numberAction > 3));

        return numberAction;
    }

    private int choiceConsumable(Object hero){
        int numberAction = -1;

        if ((hero instanceof Mage) || (hero instanceof Healer)){
            System.out.println("Liste d'actions possibles : ");
            System.out.println("1. Utiliser un lambda");
            System.out.println("2. Boire une potion");

            System.out.println("Entrez le chiffre correspondant a l'action");
            numberAction = 0;

            do {
                numberAction = this.InputParser.questionInt("Que souhaitez-vous faire ?");
                if ((numberAction < 1) && (numberAction > 2)){
                    System.out.println("Entree non valide. Entrez le chiffre correspondant a l'action entre 1 et 2");
                }
            }while ((numberAction < 1) && (numberAction > 2));

        }else {
            numberAction = 1;
        }

        return numberAction;
    }

    private void choicesAfterVictory(){
        for (int i = 0; i < this.heroes.size(); i++){
            System.out.println("Que souhaitez-vous faire pour le " + this.heroes.get(i).displayType());
            System.out.println("");
            System.out.println("1. Augmenter son armure");
            System.out.println("2. Augmenter les degats de son arme");
            System.out.print("3. Augmenter l'efficacite de la nourriture");
            if ((this.heroes.get(i) instanceof Mage) || (this.heroes.get(i) instanceof Healer)){
                System.out.print(" et de la potion");
            }
            System.out.println("");
            System.out.print("4. Augmenter le nombre de nourriture");
            if (this.heroes.get(i) instanceof SpellCaster){
                System.out.print(" et de potions");
            }
            System.out.println("");
            if (this.heroes.get(i) instanceof Hunter){
                System.out.println("5. Augmenter le nombre de fleches");
            }else if (this.heroes.get(i) instanceof SpellCaster){
                System.out.println("7. Diminuer le cout en mana");
                System.out.println("6. Augmenter l'efficacite des sorts");
            }

            System.out.println("Entrez le chiffre correspondant a l'action");
            int numberAction = 0;

            do {
                numberAction = this.InputParser.questionInt("Que souhaitez-vous faire ?");
                if(this.heroes.get(i) instanceof Hunter){

                }
                if ((numberAction < 1) && (numberAction > 4)){
                    System.out.println("Entree non valide. Entrez le chiffre correspondant a l'action entre 1 et 2");
                }
            }while ((numberAction < 1) && (numberAction > 2));

            //Mettre un switch puis executer les choix
        }
    }
}
