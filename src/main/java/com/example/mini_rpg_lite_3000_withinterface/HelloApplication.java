package com.example.mini_rpg_lite_3000_withinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Scene scene;
    public static Stage stage;
    private Game game;
    private static int numberOfHeroes;
    private static ListView<String> selectedHeroes = new ListView<>();

    public static void setNumberOfHeroes(int numberOfHeroes) {
        HelloApplication.numberOfHeroes = numberOfHeroes;
    }

    public static void setSelectedHeroes(ListView<String> selectedHeroes) {
        HelloApplication.selectedHeroes = selectedHeroes;
    }

    public static int getNumberOfHeroes() {
        return numberOfHeroes;
    }

    public static ListView<String> getSelectedHeroes() {
        return selectedHeroes;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start.fxml"));
        scene = new Scene(fxmlLoader.load());
        HelloApplication.stage = stage;
        stage.setTitle("Mini RPG Lite 3000");
        stage.setScene(scene);
        stage.show();
        //Game game = new Game();
        //game.allRight();
    }

    static void setRoot(String fxml, Stage stage) throws IOException {
        FXMLLoader fxmlToLoad = new FXMLLoader(HelloApplication.class.getResource(fxml));
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}
