package com.example.mini_rpg_lite_3000_withinterface;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class NumberPlayersController implements Initializable {
    @FXML
    private Parent root;                                                                                                //Sert à charger le FXML
    @FXML
    private Spinner<Integer> spinner = new Spinner<>();                                                                 //Spinner pour définir le nombre de héros
    int currentValue;                                                                                                   //Valeur actuelle du héros

    //Méthode pour lancer l'écran de définition du nombre de héros
    @FXML
    protected void handleBtnStart() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("number-players.fxml"));
        Scene scene = new Scene(fxmlLoader.load());                                                                     //Chargement du FXML, définition de la scène
        Application.stage.setScene(scene);
        Application.stage.show();                                                                                       //On dévoile le stage
    }

    //Méthode pour lancer de choix des héros
    @FXML
    protected void handleBtnNumberPlayers() throws Exception{
        int numberOfHeroes = spinner.getValue();                                                                        //On récupère la valeur du spinner
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("choose-heroes.fxml"));
        root = fxmlLoader.load();                                                                                       //On charge le FXML
        ChooseHeroesController chooseHeroesController = fxmlLoader.getController();
        chooseHeroesController.setNumberOfHeroes(numberOfHeroes);                                                       //On envoie la valeur du nombre de héros au contrôleur qui gère l'écran de sélection des héros
        Scene scene = new Scene(root);                                                                                  //Chargement du FXML, définition de la scène
        Application.stage.setScene(scene);
        Application.stage.show();                                                                                       //On dévoile le stage
    }

    //Méthode pour initialiser le contrôleur
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);    //On définit la fourchette de valeurs dans laquelle l'utilisateur peut choisir le nombre de héros
        valueFactory.setValue(1);                                                                                       //La valeur initialisant (dès l'affichage) le spinner est 1
        spinner.setValueFactory(valueFactory);
        currentValue = spinner.getValue();                                                                              //On récupère la valeur actuelle du spinner dans l'attribut chargé à cet effet
        spinner.valueProperty().addListener(new ChangeListener<Integer>() {                                             //Évènement gérant le spinner
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                currentValue = spinner.getValue();                                                                      //Mise à jour de la valeur actuelle du spinner actionné par l'utilisateur
            }
        });
    }
}