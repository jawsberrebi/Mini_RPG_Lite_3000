package com.example.mini_rpg_lite_3000_withinterface;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

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
    private Button useConsumableBtn;
    @FXML
    private Label actualState;
    @FXML
    private ListView<String> heroesToHeal = new ListView<>();
    @FXML
    private Button healBtn;
    @FXML
    private ImageView armorImg;
    @FXML
    private Button foodBtn;
    @FXML
    private Button potionBtn;
    @FXML
    private ListView consumables;
    @FXML
    private Button consumeBtn;
    @FXML
    private Button enhanceArmorBtn;
    @FXML
    private Button enhanceDammagesBtn;
    @FXML
    private Button enhanceConsummablesBtn;
    @FXML
    private Button enhanceQuantityConsumablesBtn;
    @FXML
    private Button enhanceQuantityFoodBtn;
    @FXML
    private Button enhanceQuantityPotionBtn;
    @FXML
    private Button enhanceQuantifiableWeaponBtn;

    private boolean resultAttackHero;

    private boolean attackResultEnemy;

    private int indexHeroToHeal;

    private int indexConsumables;

    @FXML
    public void initialize(){
        this.currentHero.setVisible(false);
        this.currentEnemy.setVisible(false);
        this.heroData.setVisible(false);
        this.enemyData.setVisible(false);
        this.healBtn.setVisible(false);
        this.armorImg.setVisible(false);
        this.consumables.setVisible(false);
        this.foodBtn.setVisible(false);
        this.potionBtn.setVisible(false);
        this.consumeBtn.setVisible(false);
        this.whatToDo.setText("Que faire ?");
        hideActions();
        hideActionsAfterVictory();
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
                hideActionsAfterVictory();
                Game.context.undefendAll();
                this.armorImg.setVisible(Game.context.isArmorOn());
                this.consumables.setVisible(false);
                this.consumeBtn.setVisible(false);
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
                this.armorImg.setVisible(Game.context.isArmorOn());
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
                this.consumables.setVisible(false);
                this.consumeBtn.setVisible(false);
                this.armorImg.setVisible(Game.context.isArmorOn());
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

            case REWARDS_TIME:
                hideActions();
                this.currentHero.setVisible(false);
                this.currentEnemy.setVisible(false);
                this.gameBtn.setVisible(false);
                this.heroData.setVisible(false);
                this.enemyData.setVisible(false);
                this.armorImg.setVisible(false);
                this.whatToDo.setVisible(true);
                this.whatToDo.setText("Le combat a été gagné ! Que souhaitez-vous faire ?");
                displayActionsAfterVictory();
                break;
            case END_GAME:
                hideActions();
                this.gameBtn.setVisible(false);
                this.currentHero.setVisible(false);
                this.currentEnemy.setVisible(false);
                this.heroData.setVisible(false);
                this.enemyData.setVisible(false);
                this.armorImg.setVisible(false);
                this.consumables.setVisible(false);
                this.consumeBtn.setVisible(false);
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
        this.armorImg.setVisible(Game.context.isArmorOn());
        if (Game.context.status == Game.Status.HERO_TURN){
            if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof Healer){
                healerAttack();
            }else {
                this.resultAttackHero = Game.context.attack(-1);
            }
            if (!this.resultAttackHero){
                this.enemyData.setText(Game.context.getEnemies().get(Game.context.getCurrentPositionEnemy()).displayData());
            }
            this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
            hideActions();

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

    @FXML
    public void handleBtnDefend(){
        Game.context.defend();
        this.armorImg.setVisible(Game.context.isArmorOn());
        hideActions();
        this.gameBtn.setVisible(true);
    }

    @FXML
    public void handleBtnUseConsumable(){
        hideActions();
        if (Game.context.getHeroes().get(Game.context.getCurrentPositionHero()) instanceof SpellCaster){
            this.foodBtn.setVisible(true);
            this.potionBtn.setVisible(true);
        }else {
            handleBtnFood();
        }
    }

    @FXML
    public void handleBtnFood(){
        this.useConsumableBtn.setVisible(false);
        this.foodBtn.setVisible(false);
        this.potionBtn.setVisible(false);
        this.consumables.getItems().clear();
        for (int i = 0; i < Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getLembdas().size(); i++){
            this.consumables.getItems().add(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getLembdas().get(i).display());
        }
        this.consumables.setVisible(true);
        this.consumeBtn.setText("Manger");
        this.consumeBtn.setVisible(true);
        consumablesSelection();
    }

    @FXML
    public void handleBtnPotion(){
        this.useConsumableBtn.setVisible(false);
        this.foodBtn.setVisible(false);
        this.potionBtn.setVisible(false);
        this.consumables.getItems().clear();
        for (int i = 0; i < Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getPotions().size(); i++){
            this.consumables.getItems().add(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).getPotions().get(i).display());
        }
        this.consumables.setVisible(true);
        this.consumeBtn.setText("Boire");
        this.consumeBtn.setVisible(true);
        consumablesSelection();
    }

    @FXML
    public void handleBtnConsume(){
        consumablesSelection();
        if(this.consumeBtn.getText().equals("Manger")){
            Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).useConsumable(this.indexConsumables, true);
        }else {
            Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).useConsumable(this.indexConsumables, false);
        }
        Game.context.status = Game.Status.ENEMY_TURN;
        this.consumables.setVisible(false);
        this.consumeBtn.setVisible(false);
        this.heroData.setText(Game.context.getHeroes().get(Game.context.getCurrentPositionHero()).displayData());
        this.gameBtn.setVisible(true);
    }

    private void healerAttack(){
        this.heroesToHeal.setVisible(true);
        this.healBtn.setVisible(true);
        this.heroesToHeal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                indexHeroToHeal = heroesToHeal.getSelectionModel().getSelectedIndex();
            }
        });
    }

    @FXML
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
        this.useConsumableBtn.setVisible(true);
    }

    private void hideActions(){
        this.whatToDo.setVisible(false);
        this.attackBtn.setVisible(false);
        this.defendBtn.setVisible(false);
        this.useConsumableBtn.setVisible(false);
    }

    private void refreshListHeroesToHeal(){
        this.heroesToHeal.getItems().clear();
        for (int i = 0; i < Game.context.getHeroes().size(); i++){
            this.heroesToHeal.getItems().add(Game.context.getHeroes().get(i).displayType());
        }
    }

    private void consumablesSelection(){
        this.consumables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                indexConsumables = consumables.getSelectionModel().getSelectedIndex();
            }
        });
    }

    private void displayActionsAfterVictory(){
        this.enhanceArmorBtn.setVisible(true);
        this.enhanceDammagesBtn.setVisible(true);
        this.enhanceConsummablesBtn.setVisible(true);
        this.enhanceQuantityConsumablesBtn.setVisible(true);
        this.enhanceQuantifiableWeaponBtn.setVisible(true);
    }

    @FXML
    public void handleBtnEnhanceArmor(){
        hideActionsAfterVictory();
        Game.context.enhanceArmor();
        Game.context.status = Game.Status.START_COMBAT;
        this.gameBtn.setVisible(true);
    }

    private void hideActionsAfterVictory(){
        this.enhanceArmorBtn.setVisible(false);
        this.enhanceDammagesBtn.setVisible(false);
        this.enhanceConsummablesBtn.setVisible(false);
        this.enhanceQuantityConsumablesBtn.setVisible(false);
        this.enhanceQuantityFoodBtn.setVisible(false);
        this.enhanceQuantityPotionBtn.setVisible(false);
        this.enhanceQuantifiableWeaponBtn.setVisible(false);
    }
}
