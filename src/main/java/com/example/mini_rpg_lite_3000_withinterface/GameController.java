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
    Label currentEnemy;
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
        hideActions();
        System.out.println(HelloApplication.getNumberOfHeroes());
        Game.createHeroGroup(HelloApplication.getNumberOfHeroes(), HelloApplication.getSelectedHeroes());
        Game.playGame();
        this.combatNumber = 0;
        this.combatNumberString.setText(Integer.toString(this.combatNumber));
        System.out.println(combatNumber);

    }

    public void updateBtn(){
        switch (Game.context.status){
            case START_COMBAT:
                this.combatNumber++;
                this.combatNumberString.setText(Integer.toString(this.combatNumber));
                this.gameBtn.setVisible(false);
                this.currentHero.setText(Game.context.getHeroes().get(0).displayType());
                this.currentEnemy.setText(Game.context.getEnemies().get(0).displayType());
                displayActions();

        }
    }

    private void displayActions(){
        this.currentHero.setVisible(true);
        this.currentEnemy.setVisible(true);
        this.whatToDo.setVisible(true);
        this.attackBtn.setVisible(true);
        this.defendBtn.setVisible(true);
        this.useConsumable.setVisible(true);
    }

    private void hideActions(){
        this.currentHero.setVisible(false);
        this.currentEnemy.setVisible(false);
        this.whatToDo.setVisible(false);
        this.attackBtn.setVisible(false);
        this.defendBtn.setVisible(false);
        this.useConsumable.setVisible(false);
    }
}
