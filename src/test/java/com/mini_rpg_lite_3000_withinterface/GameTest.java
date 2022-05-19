package com.mini_rpg_lite_3000_withinterface;

import com.example.mini_rpg_lite_3000_withinterface.*;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    //Si aucun joueur n’est en vie, on a perdu
    @Test
    public void perduSiAucunJoueurEnVie(){
        String[] heroesNames = {"Hunter", "Warrior", "Mage"};
        ListView<String> heroesList = new ListView<>();
        heroesList.getItems().addAll(heroesNames);
        Game game = new Game();
        game.createHeroGroup(3, heroesList);

        Boss boss = new Boss(10);

        game.attackEnemy();

    }

}
