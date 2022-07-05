package entity;

import algorithms.GA;
import data.DataManager;

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
    private DataManager csvWriter;

    public Optimization(int parties, Population population, GA ga, int stopCondition ){/* simulator config, stop condition ....*/
        this.parties = parties;
        this.stopCondition = stopCondition;
        this.iteration = 0;
        this.population = population;
        this.ga = ga;
        this.generation_count = 0;
        csvWriter = new DataManager();
    }

    public Population Start(){
//        TODO Fix infinite threads generation
        ExecutorService executorService = Executors.newFixedThreadPool(parties);

        int mutationChance = 10;

        List<Callable<Object>> callables = new ArrayList<>();
        List<Future<Object>> futures = null;
        for (int i = 0; i< stopCondition; i++) {
            population.initialiseGeneration();

            for (Individual individual: population.getPopulation()){
                callables.add(Executors.callable(individual));
            }

            try {
                futures = executorService.invokeAll(callables);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Future<Object> f : futures){
                System.out.println(f.isDone());
            }
//            System.out.println("Population ended.");

            csvWriter.generation_csv_write(population.getFittestIndividualSORTED(),population, mutationChance);
//            Deletes previous generation. DO NOT MOVE ANYWHERE!!!!
            csvWriter.delete_generation_files(population);

//            System.out.println("Pop size: " + population.getPopulation().size());
            this.population = ga.select(population);
//            System.out.println("Pop size: " + population.getPopulation().size());
            this.population = ga.crossover(population);
//            System.out.println("Pop size: " + population.getPopulation().size());
            this.population = ga.mutate(population, mutationChance);


        }

        csvWriter.close_csv_writer();
        executorService.shutdown();
        return population;
    }
}
