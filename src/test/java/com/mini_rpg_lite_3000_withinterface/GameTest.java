package com.mini_rpg_lite_3000_withinterface;

import com.example.mini_rpg_lite_3000_withinterface.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    //L'attribution de récompense fonctionne bien après une victoire
    public void attributionRecompenses(){
        Game game = new Game();
        Game.playGame();
        List<Hero> heroes = new ArrayList<>();
        heroes.add(new Hunter());
        heroes.add(new Warrior());
        heroes.add(new Mage());
        Game.setHeroes(heroes);
        game.generateCombat();
        Game.attack(1);
        assertEquals(Game.Status.REWARDS_TIME, Game.status);
    }

    @Test
    //Si aucun joueur n’est en vie, on a perdu
    public void perduSiAucunJoueurEnVie(){


    }

}
