package com.example.mini_rpg_lite_3000_withinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameController {

    @FXML
    private int combatNumber;
    @FXML
    Label combatNumberString;
    @FXML
    Label currentHero;
    @FXML
    Label heroData;
    @FXML
    Label currentEnemy;
    @FXML
    Label enemyData;
    @FXML
    Label whatToDo;
    @FXML
    Button gameBtn;
    @FXML
    Button attackBtn;
    @FXML
    Button defendBtn;
    @FXML
    Button useConsumable;

    @FXML
    public void initialize(){
        this.currentHero.setVisible(false);
        this.currentEnemy.setVisible(false);
        this.whatToDo.setText("Que faire ?");
        hideActions();
        System.out.println(HelloApplication.getNumberOfHeroes());
        Game.createHeroGroup(HelloApplication.getNumberOfHeroes(), HelloApplication.getSelectedHeroes());
        Game.playGame();
        this.combatNumber = 0;
        this.combatNumberString.setText(Integer.toString(this.combatNumber));
        System.out.println(combatNumber);

    }
    @FXML
    public void updateBtn(){
        switch (Game.context.status){
            case START_COMBAT:
                this.combatNumber++;
                this.combatNumberString.setText(Integer.toString(this.combatNumber));
                this.gameBtn.setVisible(false);
                this.currentHero.setVisible(true);
                this.currentEnemy.setVisible(true);
                this.currentHero.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayType());
                this.currentEnemy.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayType());
                this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
                this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());
                displayActions();
                Game.context.status = Game.Status.HERO_TURN;
                break;
            case HERO_TURN:
                this.gameBtn.setVisible(false);
                displayActions();
                break;
            case ENEMY_TURN:
                this.whatToDo.setVisible(true);
                this.whatToDo.setText("L'ennemi a attaqué !");
                Game.context.status = Game.Status.HERO_TURN;
                this.gameBtn.setText("Continuer le combat");
                break;

        }
    }

    @FXML
    public void handleBtnAttack(){
        if (Game.context.status == Game.Status.HERO_TURN){
            if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof Healer){
                healerActions();
            }
            Game.context.attack();
            this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
            this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());
            hideActions();
            if (Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).isDead()){
                this.enemyData.setText("L'ennemi est mort !");
            }
            Game.context.status = Game.Status.ENEMY_TURN;
            if (Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).isDead()){
                this.gameBtn.setText("Attaque du prochain ennemi");
            }else {
                this.gameBtn.setText("Attaque de l'ennemi");
            }

            this.gameBtn.setVisible(true);
        }
    }

    public void healerActions(){
        //Compléter le code
    }

    private void displayActions(){
        this.whatToDo.setVisible(true);
        this.whatToDo.setText("Que faire ?");
        this.attackBtn.setVisible(true);
        this.defendBtn.setVisible(true);
        this.useConsumable.setVisible(true);
    }

    private void hideActions(){
        this.whatToDo.setVisible(false);
        this.attackBtn.setVisible(false);
        this.defendBtn.setVisible(false);
        this.useConsumable.setVisible(false);
    }
}
