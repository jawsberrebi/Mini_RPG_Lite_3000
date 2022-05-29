package com.mini_rpg_lite_3000_withinterface;

import com.example.mini_rpg_lite_3000_withinterface.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HeroTest {
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


    //Tests annexe
    @Test
    // Test de la mort des héros
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
        boss.attack(warrior);
        assertTrue(hunter.getLifePoints() == 0);
        assertTrue(warrior.getLifePoints() == 0);
        assertTrue(hunter.isDead());
        assertTrue(warrior.isDead());
    }

    @Test
    //Test consommable
    public void testConsumable(){
        // FOOD
        Hero warrior = new Warrior();
        warrior.displayData();
        int lifePointsBefore = warrior.getLifePoints();
        Food food = new Food("Chocolate", 3);
        List<Food> foods = new ArrayList<>();
        foods.add(food);
        int indexFood = 0;
        warrior.setLembdas(foods);
        warrior.useConsumable(0, true);
        warrior.displayData();
        assertTrue(warrior.getLifePoints() == food.giveBonus() + lifePointsBefore);

    }

    @Test
    //Test des PV héros
    public void testPV(){
        // Test PV ennemi basique
        Hunter hero = new Hunter(3, 5, 3, 10);
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
