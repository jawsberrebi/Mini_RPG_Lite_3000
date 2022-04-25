package com.example.mini_rpg_lite_3000_withinterface;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.Random;

public class GameController {

    @FXML
    private int combatNumber;
    @FXML
    private Label combatNumberString;
    @FXML
    private Label currentHero;
    @FXML
    private Label heroData;
    @FXML
    private Label currentEnemy;
    @FXML
    private Label enemyData;
    @FXML
    private Label whatToDo;
    @FXML
    private Button gameBtn;
    @FXML
    private Button attackBtn;
    @FXML
    private Button defendBtn;
    @FXML
    private Button useConsumable;
    @FXML
    private Label actualState;
    @FXML
    private ListView<String> heroesToHeal = new ListView<>();
    @FXML
    private Button healBtn;

    private boolean resultAttackHero;

    private boolean attackResultEnemy;

    private int indexHeroToHeal;

    @FXML
    public void initialize(){
        this.currentHero.setVisible(false);
        this.currentEnemy.setVisible(false);
        this.heroData.setVisible(false);
        this.enemyData.setVisible(false);
        this.healBtn.setVisible(false);
        this.whatToDo.setText("Que faire ?");
        hideActions();
        System.out.println(HelloApplication.getNumberOfHeroes());
        Game.createHeroGroup(HelloApplication.getNumberOfHeroes(), HelloApplication.getSelectedHeroes());
        Game.playGame();
        this.combatNumber = 0;
        this.combatNumberString.setText(Integer.toString(this.combatNumber));
        refreshListHeroesToHeal();
        this.heroesToHeal.setVisible(false);
        System.out.println(combatNumber);
    }

    @FXML
    public void updateBtn() throws IOException {
        switch (Game.context.status){
            case START_COMBAT:
                this.combatNumber++;
                this.actualState.setVisible(true);
                if (this.combatNumber >= 2){
                    this.actualState.setText("Tous les ennemis du combat sont morts");
                }
                this.combatNumberString.setText(Integer.toString(this.combatNumber));
                Game.context.generateCombat();
                Random random = new Random();
                Game.context.currentPositionHero = random.nextInt(Game.context.getHeroes().size());
                Game.context.currentPositionEnemy = random.nextInt(Game.context.getEnemies().size());
                this.gameBtn.setText("Commencer un nouveau combat");
                this.gameBtn.setVisible(false);
                this.currentHero.setVisible(true);
                this.currentEnemy.setVisible(true);
                this.heroData.setVisible(true);
                this.enemyData.setVisible(true);
                this.currentHero.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayType());
                this.currentEnemy.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayType());
                this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
                this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());
                refreshListHeroesToHeal();
                displayActions();
                Game.context.status = Game.Status.HERO_TURN;
                break;
            case HERO_TURN:
                this.actualState.setVisible(false);
                this.actualState.setVisible(false);
                this.currentEnemy.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayType());
                this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());
                if (this.resultAttackHero){
                    System.out.println("Entré");
                    this.currentEnemy.setText("L'ennemi est mort !");
                    this.enemyData.setVisible(false);
                }
                this.currentHero.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayType());
                this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
                this.gameBtn.setVisible(false);
                refreshListHeroesToHeal();
                displayActions();
                break;
            case ENEMY_TURN:
                this.actualState.setVisible(false);
                this.resultAttackHero = false;
                this.enemyData.setVisible(true);
                this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());
                this.currentEnemy.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayType());
                this.attackResultEnemy = Game.context.attackEnemy();
                if (this.attackResultEnemy){
                    this.currentHero.setText("Le Héro est mort !");
                    this.heroData.setText("");
                }else {
                    this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
                }
                refreshListHeroesToHeal();
                this.whatToDo.setVisible(true);
                this.whatToDo.setText("L'ennemi a attaqué !");
                this.gameBtn.setText("Continuer le combat");
                break;
            case END_GAME:
                hideActions();
                this.gameBtn.setVisible(false);
                this.currentHero.setVisible(false);
                this.currentEnemy.setVisible(false);
                this.currentHero.setVisible(false);
                this.currentEnemy.setVisible(false);
                this.heroData.setVisible(false);
                this.enemyData.setVisible(false);
                this.actualState.setVisible(true);
                this.actualState.setText("Tous les héros sont morts");
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("end.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                HelloApplication.stage.setScene(scene);
                HelloApplication.stage.show();
                break;
        }
    }

    @FXML
    public void handleBtnAttack(){
        if (Game.context.status == Game.Status.HERO_TURN){
            if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof Healer){
                Game.context.status = Game.Status.HEAL_TURN;
                healerAttack();
            }else {
                this.resultAttackHero = Game.context.attack(-1);
            }
            if (!this.resultAttackHero){
                this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());
            }
            this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
            hideActions();

            //Game.context.status = Game.Status.ENEMY_TURN;
            if (this.resultAttackHero){
                this.gameBtn.setText("Attaque du prochain ennemi");
                this.currentEnemy.setText("L'ennemi est mort !");
                this.enemyData.setVisible(false);
            }else {
                this.gameBtn.setText("Attaque de l'ennemi");
            }
            refreshListHeroesToHeal();
            if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof Healer){
                this.gameBtn.setVisible(false);
            }else{
                this.gameBtn.setVisible(true);
            }
        }
    }

    public void healerAttack(){
        this.heroesToHeal.setVisible(true);
        this.healBtn.setVisible(true);
        this.heroesToHeal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                indexHeroToHeal = heroesToHeal.getSelectionModel().getSelectedIndex();
            }
        });
    }

    public void handleBtnHeal(){
        if (this.indexHeroToHeal >= 0){
            Game.context.attack(this.indexHeroToHeal);
            this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
            this.healBtn.setVisible(false);
            this.heroesToHeal.setVisible(false);
            this.gameBtn.setVisible(true);
            Game.context.status = Game.Status.ENEMY_TURN;
        }
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

    private void refreshListHeroesToHeal(){
        this.heroesToHeal.getItems().clear();
        for (int i = 0; i < Game.context.getHeroes().size(); i++){
            this.heroesToHeal.getItems().add(Game.context.getHeroes().get(i).displayType());
        }
    }
}
