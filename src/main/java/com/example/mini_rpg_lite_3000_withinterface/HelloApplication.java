package com.example.mini_rpg_lite_3000_withinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Scene scene;
    private Game game;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        stage.setTitle("Mini RPG Lite 3000");
        scene = new Scene(root, 320, 240);
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
