package com.mini_rpg_lite_3000_withinterface;

import com.example.mini_rpg_lite_3000_withinterface.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSpellCaster {


    @Test
    //La quantité de mana diminue après avoir lancé un sort
    public void baisseMana(){
        SpellCaster mage = new Mage();
        mage.displayData();
        Enemy basicEnemy = new BasicEnemy(20);
        int manaBefore = mage.getManaPoints();
        mage.attack(basicEnemy, null);
        assertTrue(mage.getManaPoints() == manaBefore - mage.getCostManaPoints());
        mage.attack(basicEnemy, null);
    }

    @Test
    //Si l’on n’a pas assez de mana, on ne peut pas lancer le sort
    public void pasAssezDeMana(){
        SpellCaster mage = new Mage();
        mage.displayData();
        Enemy basicEnemy = new BasicEnemy(20);
        int manaBefore = mage.getManaPoints();
        mage.attack(basicEnemy, null);
        System.out.println("PREMIERE ATTAQUE");
        System.out.println(basicEnemy.getLifePoints());
        mage.displayData();
        assertTrue(mage.getManaPoints() == manaBefore - mage.getCostManaPoints());
        mage.attack(basicEnemy, null);
        System.out.println("DEUXIEME ATTAQUE");
        System.out.println(basicEnemy.getLifePoints());
        mage.displayData();
        mage.attack(basicEnemy, null);
        System.out.println("TROISIEME ATTAQUE");
        System.out.println(basicEnemy.getLifePoints());
        mage.displayData();
        mage.attack(basicEnemy, null);
        System.out.println("QUATRIEME ATTAQUE");
        System.out.println(basicEnemy.getLifePoints());
        mage.displayData();
        assertTrue(mage.getManaPoints() == 0);
        mage.attack(basicEnemy, null);
        System.out.println("CINQUIEME ATTAQUE");
        System.out.println(basicEnemy.getLifePoints());
        mage.displayData();
        Enemy boss = new Boss(100);
        mage.attack(boss, null);
        System.out.println("SIXIEME ATTAQUE");
        System.out.println(boss.getLifePoints());

        assertTrue(mage.getManaPoints() == 0);
        assertTrue(basicEnemy.getLifePoints() == 0);
        assertTrue(boss.getLifePoints() == 100);


        SpellCaster healer = new Healer();
        healer.displayData();
        Hero basicHero = new Warrior();
        System.out.println(basicHero.getLifePoints());
        int lifeBefore = basicHero.getLifePoints();
        manaBefore = healer.getManaPoints();
        healer.attack(null, basicHero);
        System.out.println("PREMIERE ATTAQUE");
        System.out.println(basicHero.getLifePoints());
        healer.displayData();
        assertTrue(healer.getManaPoints() == manaBefore - healer.getCostManaPoints());
        healer.attack(null, basicHero);
        System.out.println("DEUXIEME ATTAQUE");
        System.out.println(basicHero.getLifePoints());
        healer.displayData();
        healer.attack(null, basicHero);
        System.out.println("TROISIEME ATTAQUE");
        Hero hunter = new Hunter();
        int lifeBeforeHunter = hunter.getLifePoints();
        healer.attack(null, hunter);
        System.out.println("QUATRIEME ATTAQUE");
        System.out.println(hunter.getLifePoints());

        assertTrue(healer.getManaPoints() == 0);
        assertTrue(basicHero.getLifePoints() > lifeBefore);
        assertTrue(hunter.getLifePoints() == lifeBeforeHunter);


    }
}
