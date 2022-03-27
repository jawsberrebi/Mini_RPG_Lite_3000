package com.example.mini_rpg_lite_3000_withinterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    public Game game;
    @FXML
    private Button btnStart, btnnumberPlayes;
    @FXML
    private Thread thread;
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;
    @FXML
    private Spinner<Integer> spinner;

    @FXML
    protected void handleBtnStart() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("number-players.fxml"));
        Stage stage = (Stage) btnStart.getScene().getWindow();
        stage.setScene(new Scene(root, 320, 240));
    }
    @FXML
    protected void handleSpinner(){
        //https://youtu.be/9XJicRt_FaI?t=8946
    }
}