package com.deljanin;

import java.util.Vector;

public class Population {
    private Vector<Individual> population;
    private int generation_size = 4;

    public Population(int generation_size) {
        this.generation_size = generation_size;
        initializeGeneration();
        startGeneration();
    }

    public void initializeGeneration() {
        population = new Vector<>();
        for (int i = 0; i < generation_size; i++) {
            population.add(new Individual());
        }
    }
    public void startGeneration(){
        population.forEach(Individual::start);
    }
}
