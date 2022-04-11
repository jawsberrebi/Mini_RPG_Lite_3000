package com.example.mini_rpg_lite_3000_withinterface;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseHeroesController implements Initializable {
    @FXML
    private Parent root;
    @FXML
    private ListView<String> proposedHeroes = new ListView<>();
    @FXML
    private ListView<String> selectedHeroes = new ListView<>();

    String[] heroes = {"Warrior", "Hunter", "Healer", "Mage"};

    String currentHero;
    private int numberOfHeroes;
    private int countHeroes;

    @FXML
    protected void handleBtnPlay() throws Exception{
        if (this.countHeroes == this.numberOfHeroes){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game.fxml"));
            HelloApplication.setNumberOfHeroes(this.numberOfHeroes);
            HelloApplication.setSelectedHeroes(this.selectedHeroes);
            //this.root = fxmlLoader.load();
            /*
            GameController gameController = fxmlLoader.getController();
            gameController.setSelectedHeroes(this.selectedHeroes);
            gameController.setNumberOfHeroes(this.numberOfHeroes);
            //Game.createHeroGroup(this.numberOfHeroes, this.selectedHeroes); //A METTRE DANS CONTROLLEUR GAME
             */
            Scene scene = new Scene(fxmlLoader.load());
            HelloApplication.stage.setScene(scene);
            HelloApplication.stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.countHeroes = 0;
        this.proposedHeroes.getItems().addAll(this.heroes);
        this.proposedHeroes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentHero = proposedHeroes.getSelectionModel().getSelectedItem();
                if (countHeroes < numberOfHeroes){
                    selectedHeroes.getItems().add(currentHero);
                    countHeroes++;
                }

            }

        });
    }

    public void setNumberOfHeroes(int numberOfHeroes){
        this.numberOfHeroes = numberOfHeroes;
    }
}
