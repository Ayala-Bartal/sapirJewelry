package com.example.ayala.sapirjewelry.entities;

/**
 * Created by ayala on 10/18/2016.
 */

public class Country {

    private String name;
    private int population;
    private int flagResId;
    private boolean isSpecial;

    public Country(String name, int population, int flagResId) {
        this.name = name;
        this.population = population;
        this.flagResId = flagResId;
    }

    public Country(String name, int population, int flagResId, boolean isSpecial) {
        this.name = name;
        this.population = population;
        this.flagResId = flagResId;
        this.isSpecial = isSpecial;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getFlagResId() {
        return flagResId;
    }

    public void setFlagResId(int flagResId) {
        this.flagResId = flagResId;
    }
}
