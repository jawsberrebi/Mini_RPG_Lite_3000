package com.mini_rpg_lite_3000_withinterface;

import com.example.mini_rpg_lite_3000_withinterface.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeroTest {

    //L'attribution de récompense fonctionne bien après une victoire

    @Test
    //On inflige bien les dégâts dans la bonne fourchette de dégâts
    public void infligeDegats(){
        Hunter hunter = new Hunter();
        Enemy basicEnemy = new BasicEnemy(10);
        int lifePointsBeforeAttack = basicEnemy.getLifePoints();
        hunter.attack(basicEnemy, null);
        assertEquals(lifePointsBeforeAttack - hunter.getWeaponDamage(), basicEnemy.getLifePoints());
    }

    @Test
    //Si l’on prend plus de dégâts que l’on a de PV, on meurt
    public void plusDeDegatsQueDePV(){
        Hunter hunter = new Hunter(2,5,2,10);
        hunter.displayData();
        Enemy basicEnemy = new BasicEnemy(50);
        basicEnemy.attack(hunter);
        hunter.displayData();
        assertTrue(hunter.isDead());

        Warrior warrior = new Warrior();
        warrior.displayData();
        Enemy boss = new Boss(50);
        boss.attack(warrior);
        boss.attack(warrior);
        warrior.displayData();
        assertTrue(warrior.isDead());

        Mage mage = new Mage();
        mage.displayData();
        boss.attack(mage);
        mage.displayData();
        assertTrue(mage.isDead());

        Healer healer = new Healer();
        healer.displayData();
        boss.attack(healer);
        healer.displayData();
        assertTrue(healer.isDead());
    }

    @Test
    // Test de la mort
    public void testMort(){
        Hero hunter = new Hunter();
        Hero warrior = new Warrior();
        hunter.displayData();
        warrior.displayData();
        Enemy boss = new Boss(100);
        Enemy basicEnemy = new BasicEnemy(10);
        basicEnemy.attack(hunter);
        basicEnemy.attack(hunter);
        basicEnemy.attack(hunter);
        basicEnemy.attack(hunter);
        basicEnemy.attack(hunter);
        basicEnemy.attack(hunter);
        basicEnemy.attack(hunter);
        boss.attack(warrior);
        hunter.displayData();
        warrior.displayData();
        assertTrue(hunter.getLifePoints() == 0);
        assertTrue(warrior.getLifePoints() == 0);
        assertTrue(hunter.isDead());
        assertTrue(warrior.isDead());
    }

    @Test
    //Test consommable
    public void testConsumable(){
        // FOOD
        Hero warrior = new Healer();
        warrior.displayData();
        int lifePointsBefore = warrior.getLifePoints();
        Food food = new Food("Chocolate", 3);
        int indexFood = 0;
        warrior.useConsumable(0, true);
        warrior.displayData();
        assertTrue(warrior.getLifePoints() == food.giveBonus() + lifePointsBefore);

    }

    @Test
    //Test
    public void testPV(){
        // Test PV ennemi basique
        Hunter hero = new Hunter(10, 5, 3, 10);
        BasicEnemy basicEnemy = new BasicEnemy(5);
        Boss boss = new Boss(100);
        hero.attack(basicEnemy, null);
        assertTrue(basicEnemy.getLifePoints() == 2);
        assertTrue(hero.getArrows() == 9);

        //Test PV hero
        boss.attack(hero);
        assertTrue(hero.isDead());

    }


}
