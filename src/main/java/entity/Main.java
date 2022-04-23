package entity;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        int i = 0;
        String gen_name = "Gen"+i;
        Population population = new Population(4);
        population.calculateFitness(gen_name);
        population.getPopulation().forEach(individual -> executorService.execute(individual));
        population.newGeneration(gen_name);
        i++;
        while(i<3){
            gen_name = "Gen"+i;
            population.newGeneration(gen_name);
            population.calculateFitness(gen_name);
            population.getPopulation().forEach(individual -> executorService.execute(individual));
            population.mutate(10);
            i++;
        }
        executorService.shutdown();
    }

}
