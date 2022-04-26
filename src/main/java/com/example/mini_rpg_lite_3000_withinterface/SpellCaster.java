package com.example.mini_rpg_lite_3000_withinterface;

import java.util.ArrayList;

public abstract class SpellCaster extends Hero{
    protected int manaPoints;
    protected int costManaPoints;

    public SpellCaster(){
        super();
        this.potions = new ArrayList<>();
        this.potions.add(new Potion("Nectar", 2));
        this.potions.add(new Potion("Nectar", 2));
        this.potions.add(new Potion("Elixir", 5));
    }

    public abstract void spelling(Enemy enemy, Hero hero);

    @Override
    public String displayData() {
        StringBuilder ptn = new StringBuilder();
        for (Potion potion : this.potions) {
            ptn.append(potion.display()).append("\n");
        }
        return "Points de mana : " + this.manaPoints + "\n" +
                "Cout actuel du mana : " + this.costManaPoints + "\n" +
                super.displayData() + ptn;
    }

    @Override
    public void useConsumable(Consumable consumable) {
        if (consumable instanceof Potion){
            this.manaPoints += consumable.giveBonus();
        }
        super.useConsumable(consumable);
    }

    public int getManaPoints() {
        return this.manaPoints;
    }

    public int getCostManaPoints() {
        return this.costManaPoints;
    }

    public void addManaPoints(int bonusPoints){
        this.manaPoints += bonusPoints;
    }
}
