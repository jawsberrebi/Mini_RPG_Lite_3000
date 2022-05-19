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
    private Parent root;
    @FXML
    private Spinner<Integer> spinner = new Spinner<>();
    int currentValue;

    @FXML
    protected void handleBtnStart() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("number-players.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Application.stage.setScene(scene);
        Application.stage.show();
    }

    @FXML
    protected void handleBtnNumberPlayers() throws Exception{
        int numberOfHeroes = spinner.getValue();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("choose-heroes.fxml"));
        root = fxmlLoader.load();
        ChooseHeroesController chooseHeroesController = fxmlLoader.getController();
        chooseHeroesController.setNumberOfHeroes(numberOfHeroes);
        Scene scene = new Scene(root);
        Application.stage.setScene(scene);
        Application.stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        valueFactory.setValue(1);
        spinner.setValueFactory(valueFactory);
        currentValue = spinner.getValue();
        spinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                currentValue = spinner.getValue();
            }

        });


    }

}