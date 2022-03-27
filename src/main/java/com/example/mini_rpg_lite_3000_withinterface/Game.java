package com.example.mini_rpg_lite_3000_withinterface;
import com.example.mini_rpg_lite_3000_withinterface.utils.InputParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private List<Hero> heroes = new ArrayList<Hero>();
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private int playerTurn;
    public com.example.mini_rpg_lite_3000_withinterface.utils.InputParser InputParser;
    private int lifePointBasicEnemy;
    private int lifePointBoss;
    private int combatNumber;

    public void allRight(){
        this.lifePointBasicEnemy = 5;
        this.lifePointBoss = 50;
        this.combatNumber = 0;
        generateCombat();
    }

    public void playGame(){
        this.lifePointBasicEnemy = 5;
        this.lifePointBoss = 50;
        this.combatNumber = 0;

        System.out.println("Mini RPG Lite 3000");
        System.out.println("Nouvelle partie");

        //Creation d'une nouvelle equipe et affichage des données sur chaque personnage
        createHeroGroup();
        displayTeam();

        System.out.println("");
        System.out.println("");



        while (!this.heroes.isEmpty()){

            //Generation d'un nouveau combat
            generateCombat();
            this.combatNumber++;
            System.out.println("Combat numero " + this.combatNumber);
            System.out.println("");
            System.out.println("Equipe " + this.combatNumber + " d'ennemis");
            System.out.println("");
            displayEnemy();
            System.out.println("");

            //Combat
            Random random = new Random();

            while (!this.enemies.isEmpty()){ //Tant qu'il reste encore des ennemis
                for (int i = 0; i < this.enemies.size(); i++){
                    while (!this.heroes.isEmpty()){
                        int positionJoueur = random.nextInt(this.heroes.size());
                        int positionEnnemi = random.nextInt(this.enemies.size());
                        System.out.println(positionEnnemi);
                        System.out.println("Ennemi numero " + (positionEnnemi + 1) + " se tient devant vous.");
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

                        //Actions du joueur
                        int userChoice = choice();

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
                                this.heroes.get(positionJoueur).displayData();

                                int resultChoice = choiceConsumable(this.heroes.get(positionJoueur));

                                //Cas de l'utilisation d'un lembda
                                if (resultChoice == 1){
                                    if (this.heroes.get(positionJoueur).lembdas.isEmpty()){
                                        System.out.println("Votre personnage n'a plus de lembdas !");
                                    }else {
                                        System.out.println("Le personnage dispose des lembdas suivants : ");
                                        for (int w = 0; w < this.heroes.get(positionJoueur).lembdas.size(); w++){
                                            System.out.print(w + ". ");
                                            this.heroes.get(positionJoueur).lembdas.get(w).display();
                                        }

                                        System.out.println("Entrez le chiffre correspondant a l'action");
                                        int resultChoiceLambda;

                                        do {
                                            resultChoiceLambda = this.InputParser.questionInt("Quel lembda souhaitez-vous consommer ?");
                                            if ((resultChoiceLambda < 0) && (resultChoiceLambda > this.heroes.get(positionJoueur).lembdas.size() - 1)){
                                                System.out.println("Entree non valide. Entrez le chiffre correspondant a l'action");
                                            }
                                        }while ((resultChoiceLambda < 0) && (resultChoiceLambda > this.heroes.get(positionJoueur).lembdas.size() - 1));

                                        this.heroes.get(positionJoueur).useConsumable(this.heroes.get(positionJoueur).lembdas.get(resultChoiceLambda));
                                        this.heroes.get(positionJoueur).lembdas.remove(resultChoiceLambda);
                                        System.out.println("Ce héros a consommé un lembda");
                                        this.heroes.get(positionJoueur).displayData();
                                    }

                                }else if(resultChoice == 2){
                                    if (this.heroes.get(positionJoueur).potions.isEmpty()){
                                        System.out.println("Votre personnage n'a plus de potions !");
                                    }else {
                                        System.out.println("Le personnage dispose des potions suivantes : ");
                                        for (int w = 0; w < this.heroes.get(positionJoueur).potions.size(); w++){
                                            System.out.print(w + ". ");
                                            this.heroes.get(positionJoueur).potions.get(w).display();
                                        }

                                        System.out.println("Entrez le chiffre correspondant a l'action");
                                        int resultChoicePotion;

                                        do {
                                            resultChoicePotion = this.InputParser.questionInt("Quelle potion souhaitez-vous consommer ?");
                                            if ((resultChoicePotion < 0) && (resultChoicePotion > this.heroes.get(positionJoueur).potions.size() - 1)){
                                                System.out.println("Entree non valide. Entrez le chiffre correspondant a l'action");
                                            }
                                        }while ((resultChoicePotion < 0) && (resultChoicePotion > this.heroes.get(positionJoueur).potions.size() - 1));

                                        this.heroes.get(positionJoueur).useConsumable(this.heroes.get(positionJoueur).potions.get(resultChoicePotion));
                                        this.heroes.get(positionJoueur).potions.remove(resultChoicePotion);
                                        System.out.println("Ce héros a consommé une potion");
                                        this.heroes.get(positionJoueur).displayData();
                                    }
                                }
                                break;
                        }

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
            if (this.enemies.isEmpty()){
                //Si tous les ennemis sont morts, les personnages ont gagné le combat
                System.out.println("Vous avez gagné le combat " + this.combatNumber + " !");

                //Si les personnages ont gagné le combat, il y a une attribution de récompense
                //A remplir...
            }
            break;

        }

    }

    public void generateCombat(){
        Random random = new Random();
        int chanceOfBoss = random.nextInt(4 + 1) + 1;

        //Creation des ennemis
        for (int i = 0; i < this.heroes.size(); i++){
            this.enemies.add(i, new BasicEnemy(this.lifePointBasicEnemy));
        }

        //Probabilite d'obtenir un boss dans un groupe
        if (chanceOfBoss == 3){
            System.out.println(this.enemies.size());
            this.enemies.set(this.enemies.size() - 2, new Boss(this.lifePointBoss));
            this.lifePointBoss += 10;
        }

        //Incrementation des points de vie des ennemis pour les prochaines parties
        this.lifePointBasicEnemy++;
    }

    private void createHeroGroup(){
        Scanner scanner = new Scanner(System.in);
        int heroesNumber = this.InputParser.questionInt("Creation d'une nouvelle equipe, combient voulez-vous de heros ?");
        String heroType = "";
        for (int i = 0; i < heroesNumber; i++){
            do {
                heroType = this.InputParser.questionString("Quel est le type du hero du hero numero " + i + " ?");
                System.out.println(heroType);
                if (heroType.equals("Hunter")){
                    heroes.add(i, new Hunter());
                    System.out.println("Hunter selectionne");
                    System.out.println("");
                }else if(heroType.equals("Healer")){
                    heroes.add(i, new Healer());
                    System.out.println("Healer selectionne");
                    System.out.println("");
                }else if (heroType.equals("Mage")){
                    heroes.add(i, new Mage());
                    System.out.println("Mage selectionne");
                    System.out.println("");
                }else if(heroType.equals("Warrior")){
                    heroes.add(i, new Warrior());
                    System.out.println("Warrior selectionne");
                    System.out.println("");
                }else{
                    System.out.println("Vous n'avez pas entre un nom valide de type. Ressayez");
                    System.out.println("");
                }
            }while ((!"Hunter".equals(heroType)) && (!"Healer".equals(heroType)) && (!"Mage".equals(heroType)) && (!"Warrior".equals(heroType)));
        }
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
