package entity;

public class Main {

    public static void main(String[] args) {
//        int i = 0;
//        String gen_name = "Gen"+i;
        Population population = new Population(4);
        population.calculateFitness("Gen0");
        System.out.println(population.getPopulation().get(0).getIntersections_enum());
        population.mutate();
        System.out.println(population.getPopulation().get(0).getIntersections_enum());
//        population.calculateFitness(gen_name);
//        i++; gen_name = "Gen"+i;
//        population.newGeneration(gen_name);
//        while(i<1){
//            System.out.println(i);
//            population.newGeneration(gen_name);
//            population.calculateFitness(gen_name);
////            TODO Make sure the thread is finished before running the next generation!!!!
////            population.mutate();
//            i++;
//        }
    }

}
