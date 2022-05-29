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
        game.setHeroes(heroes);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new BasicEnemy(1));
        enemies.add(new BasicEnemy(1));
        game.setEnemies(enemies);
        Game.attack(0);
        assertEquals(Game.Status.REWARDS_TIME, Game.status);
    }

    @Test
    //Si aucun joueur n’est en vie, on a perdu
    public void perduSiAucunJoueurEnVie(){
        Game game = new Game();
        Game.playGame();
        List<Hero> heroes = new ArrayList<>();
        heroes.add(new Hunter());
        heroes.add(new Warrior());
        heroes.add(new Mage());
        game.setHeroes(heroes);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new BasicEnemy(1));
        enemies.add(new BasicEnemy(1));
        game.setEnemies(enemies);
        for (int i = 0; i < 100; i++) {
            Game.attackEnemy();
        }
        assertEquals(Game.Status.END_GAME, Game.status);
    }

}
