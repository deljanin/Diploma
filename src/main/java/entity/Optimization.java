package entity;

import algorithms.GA;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Optimization {
    private Population population;
    private GA ga;
    private int generation_count;

    public Optimization(Population population, GA ga /* simulator config, stop condition ....*/){
        this.population = population;
        this.ga = ga;
        this.generation_count = 0;
    }

    public Population Start(){
//        Handles writing to disk
//        Implements a barrier and loops until stop condition is met
//        In every iteration calls the 3 methods from GA algorithm
//        Returns modified population at the end

//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//      TODO needs loop {
        population.initialiseGeneration();
//        population.getPopulation().forEach(executorService::execute);
//        TESTING:
        population.getPopulation().forEach(i -> i.start());
        System.out.println(population.getPopulation().get(0).getIntersections_enum());
        this.population = ga.crossover(population);
        System.out.println(population.getPopulation().get(0).getIntersections_enum());
        this.population = ga.mutate(population, 10);
        System.out.println(population.getPopulation().get(0).getIntersections_enum());
//        population.crossover();
//        population.mutate(10);
//TODO  }

//        executorService.shutdown();



        return population;
    }



}
