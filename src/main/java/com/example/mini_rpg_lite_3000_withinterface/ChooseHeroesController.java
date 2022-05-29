package com.example.mini_rpg_lite_3000_withinterface;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseHeroesController implements Initializable {
    @FXML
    public ListView<String> proposedHeroes = new ListView<>();                                                          //Liste de héros à sélectionner
    @FXML
    private ListView<String> selectedHeroes = new ListView<>();                                                         //Héros sélectionnés par l'utilisateur

    private String[] heroes = {"Warrior", "Hunter", "Healer", "Mage"};                                                  //Texte à afficher dans la liste des héros à sélectionner

    private String currentHero;                                                                                         //Héros actuel sélectionné par l'utilisateur
    private int numberOfHeroes;                                                                                         //Nombre de héros définit par l'utilisateur dans l'équipe
    private int countHeroes;                                                                                            //Compteur de héros sélectionnés actuellement (pour comparer avec le nombre de héros choisis précédemment)

    //Méthode pour le bouton play (qui lance l'écran de jeu principal)
    @FXML
    protected void handleBtnPlay() throws Exception{
        if (this.countHeroes == this.numberOfHeroes){                                                                   //Si le joueur a bien sélectionné le nombre de héros qu'il a défini à l'écran d'avant au préalable
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("game.fxml"));                   //Chargement du FXML dans une variable
            Application.setNumberOfHeroes(this.numberOfHeroes);                                                         //On passe dans l'application globale le nombre de héros défini par l'utilisateur
            Application.setSelectedHeroes(this.selectedHeroes);                                                         //On passe dans l'application globale les héros choisis
            //Lancement de la nouvelle scene
            Scene scene = new Scene(fxmlLoader.load());
            Application.stage.setScene(scene);
            Application.stage.show();
        }
    }

    //Méthode d'initialisation du contrôleur
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.countHeroes = 0;                                                                                           //À l'initialisation, l'utilisateur a pour instant choisi aucun héros
        this.proposedHeroes.getItems().addAll(this.heroes);                                                             //Chargement du tableau des noms de héros (en String) dans la liste affichable
        this.proposedHeroes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {       //Évènement initialisé pour la sélection des héros
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentHero = proposedHeroes.getSelectionModel().getSelectedItem();                                     //Héros actuellement sélectionné
                if (countHeroes < numberOfHeroes){                                                                      //Si le nombre de héros sélectionné actuellement est inférieur au nombre de héros définit précédemment par l'utilisateur
                    selectedHeroes.getItems().add(currentHero);                                                         //On ajoute les héros sélectionnés dans la liste affichable des héros sélectionnés
                    countHeroes++;                                                                                      //Incrémentation du nombre de héros sélectionnés à chaque sélection de héros
                }
            }
        });
    }

    //Getters/Setters
    public void setNumberOfHeroes(int numberOfHeroes){
        this.numberOfHeroes = numberOfHeroes;
    }
}
