package com.example.mini_rpg_lite_3000_withinterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    private static Scene scene;                                                                                         //Scène
    public static Stage stage;                                                                                          //Stage
    private static int numberOfHeroes;                                                                                  //Nombre de héros définis par l'utilisateur
    private static ListView<String> selectedHeroes = new ListView<>();                                                  //Liste de héros sélectionnés par l'utilisateur

    //Getters/Setters
    public static void setNumberOfHeroes(int numberOfHeroes) {
        Application.numberOfHeroes = numberOfHeroes;
    }

    public static void setSelectedHeroes(ListView<String> selectedHeroes) {
        Application.selectedHeroes = selectedHeroes;
    }

    public static int getNumberOfHeroes() {
        return numberOfHeroes;
    }

    public static ListView<String> getSelectedHeroes() {
        return selectedHeroes;
    }

    //Chargement et affichage de la scène de début
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("start.fxml"));                      //Fichier FXML
        scene = new Scene(fxmlLoader.load());                                                                           //Chargement du FXML
        Application.stage = stage;                                                                                      //Création du stage
        stage.setTitle("Mini RPG Lite 3000");                                                                           //Titre
        stage.setScene(scene);                                                                                          //Chargement dans le stage
        stage.show();                                                                                                   //On dévoile le stage
    }

    //Méthode principale de lancement de l'application
    public static void main(String[] args) {
        launch();
    }
}
