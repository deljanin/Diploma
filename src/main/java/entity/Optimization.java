package entity;

import algorithms.GA;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Optimization  {
    private Population population;
    private GA ga;
    private int generation_count;
    private int parties;
    private int iteration;
    private int stopCondition;

    public Optimization(int parties, Population population, GA ga, int stopCondition ){/* simulator config, stop condition ....*/
        this.parties = parties;
        this.stopCondition = stopCondition;
        this.iteration = 0;
        this.population = population;
        this.ga = ga;
        this.generation_count = 0;
    }


    public Population Start(){
//        Implements a barrier and loops until stop condition is met
//        In every iteration calls the 3 methods from GA algorithm
//        Returns modified population at the end

//        ExecutorService executorService = Executors.newFixedThreadPool(parties);
//
//        for (int i = 0; i< stopCondition; i++) {
//            population.initialiseGeneration();
//
//            List<Callable<Object>> callables = new ArrayList<>();
//            for (Individual individual: population.getPopulation()){
//                callables.add(Executors.callable(individual));
//            }
//            try {
//                List<Future<Object>> futures = executorService.invokeAll(callables);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Population ended.");
//        }

//      TODO needs loop {

//        TESTING:
        population.initialiseGeneration();
        population.getPopulation().forEach(i -> i.start());
//        System.out.println(population.getPopulation().get(0).getIntersections_enum());
//        this.population = ga.crossover(population);
//        System.out.println(population.getPopulation().get(0).getIntersections_enum());
//        this.population = ga.mutate(population, 10);
//        System.out.println(population.getPopulation().get(0).getIntersections_enum());
//        population.crossover();
//        population.mutate(10);
//TODO  }
        return population;
    }
}
