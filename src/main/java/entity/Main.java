package entity;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Population population = new Population(4);
//      TODO needs loop {
        population.initialiseGeneration();
        population.getPopulation().forEach(individual -> executorService.execute(individual));
//        population.newGeneration();
//        population.mutate(10);
//TODO  }

        executorService.shutdown();
    }

}
