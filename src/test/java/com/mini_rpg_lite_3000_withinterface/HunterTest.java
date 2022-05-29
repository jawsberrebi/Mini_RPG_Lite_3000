package com.mini_rpg_lite_3000_withinterface;

import com.example.mini_rpg_lite_3000_withinterface.BasicEnemy;
import com.example.mini_rpg_lite_3000_withinterface.Enemy;
import com.example.mini_rpg_lite_3000_withinterface.Hunter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HunterTest {

    @Test
    //La quantité de flèches diminue quand on tire
    public void testFleches(){
        Hunter hunter = new Hunter();
        Enemy basicEnemy = new BasicEnemy(50);
        hunter.displayData();
        for (int i = 0; i < 10; i++){
            int arrowsBefore = hunter.getArrows();
            hunter.attack(basicEnemy, null);
            //A chaque attaque le Hunter perd une fleche
            assertTrue(hunter.getArrows() == arrowsBefore - 1);
        }

    }

}
