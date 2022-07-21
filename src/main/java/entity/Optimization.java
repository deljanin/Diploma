package entity;

import algorithms.GA;
import data.DataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class Optimization  {
    private Population population;
    private GA ga;
    private int parties;
    private int iteration;
    private int stopCondition;
    private DataManager dataManager;
    private int mutationChance;
    private ArrayBlockingQueue<Individual> queue;
    private String algorithm;

    public Optimization(int parties, Population population, GA ga, int stopCondition, int mutationChance, String algorithm){
        if(parties == 0) parties = Runtime.getRuntime().availableProcessors();
        this.parties = parties;
        this.stopCondition = stopCondition;
        this.iteration = 0;
        this.population = population;
        this.ga = ga;
        this.mutationChance = mutationChance;
        queue = new ArrayBlockingQueue<>(population.getPopulation_size());
        dataManager = new DataManager();
        this.algorithm = algorithm;
    }

    public Population Start(boolean debugMode){
        ExecutorService executorService = Executors.newFixedThreadPool(parties);
        List<Callable<Object>> callableExecutors = new ArrayList<>(parties);
        List<Executor> executors = new ArrayList<>(parties);
        List<Future<Object>> futures = null;

        for (int i = 0; i < parties; i++) {
            executors.add(new Executor(debugMode));
        }


        for (int i = 0; i< stopCondition; i++) {
            population.initialiseGeneration();
            queue.addAll(population.getPopulation());
            int limit = parties;
            while (!queue.isEmpty()){
                if(limit > queue.size()) limit = queue.size();
                for (int j = 0; j < limit; j++) {
                    executors.get(j).initialise(queue.poll());
                }
                for (int j = 0; j < limit; j++) {
                    callableExecutors.add(Executors.callable(executors.get(j)));
                }
                try {
                    futures = executorService.invokeAll(callableExecutors);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Future<Object> f : futures){
                    try {
                        f.get();                        // Serves like a barrier of sort
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
//                    if(debugMode) System.out.println(f.isDone());
                }
                callableExecutors.clear();
            }
            if(debugMode) System.out.println(queue.size());
            if(debugMode) System.out.println("Population ended.");

            dataManager.generation_csv_write(population.getFittestIndividualSORTED(), population, mutationChance, algorithm);
//            Deletes previous generation. DO NOT MOVE ANYWHERE!!!!
            dataManager.delete_generation_files(population);

            if(debugMode) System.out.println("Pop size before: " + population.getPopulation().size());
            this.population = ga.select(population);
            if(debugMode) System.out.println("Pop size after: " + population.getPopulation().size());
            if(debugMode) population.getPopulation().get(0).printIndividualIntersections();
            if(debugMode) population.getPopulation().get(1).printIndividualIntersections();
            this.population = ga.crossover(population);
            if(debugMode) population.getPopulation().get(0).printIndividualIntersections();
            if(debugMode) population.getPopulation().get(1).printIndividualIntersections();
            this.population = ga.mutate(population, mutationChance);
//            if(debugMode) population.getPopulation().get(0).printIndividualIntersections();
        }

        dataManager.close_csv_writer();
        executorService.shutdown();
        return population;
    }
}
