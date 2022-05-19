package com.example.mini_rpg_lite_3000_withinterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    private static Scene scene;
    public static Stage stage;
    private Game game;
    private static int numberOfHeroes;
    private static ListView<String> selectedHeroes = new ListView<>();

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

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("start.fxml"));
        scene = new Scene(fxmlLoader.load());
        Application.stage = stage;
        stage.setTitle("Mini RPG Lite 3000");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml, Stage stage) throws IOException {
        FXMLLoader fxmlToLoad = new FXMLLoader(Application.class.getResource(fxml));
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}
