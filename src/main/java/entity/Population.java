package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Population {
    private Vector<Individual> population;
    private int generation_size = 4;

    public Population(int generation_size, int individual_size) {
        this.generation_size = generation_size;
        initializeGeneration(individual_size);
        startGeneration();
    }

    public void initializeGeneration(int individual_size) {
        population = new Vector<>();
        for (int i = 0; i < generation_size; i++) {
            population.add(new Individual(individual_size));
        }
    }
    public void startGeneration(){
        population.forEach(Individual::start);
    }

//    private Tuple makeKidsPair(Individual individual1, Individual individual2){
//        ArrayList<Integer> individual1_firstHalf =
//        String s1_fh = s1.getData().substring(0, s1.getData().length()/2);
//        String s2_fh = s2.getData().substring(0, s2.getData().length()/2);
//        String s1_sh = s1.getData().substring(s1.getData().length()/2);
//        String s2_sh = s2.getData().substring(s2.getData().length()/2);
//        return new Tuple(new Individual(s1_fh+s2_sh),new Individual(s2_fh+s1_sh));
//    }

//    public static void newGeneration() {
//        Vector<Individual> newGen = new Vector<>(population.size());
//        Collections.sort(population,new SortByFitness());
//        Collections.reverse(population);
//        for (int i = 0; i < GENERATION_SIZE/2; i=i+2) {
//            newGen.add(kids(population.get(i), population.get(i+1))[0]);
//            newGen.add(kids(population.get(i), population.get(i+1))[1]);
//        }
//        newGen.addAll(population.subList(0,population.size()/2));
//        population = newGen;
//        calculateFitness();
//    }

//    public static void mutate() {
//        for (Individual I: population) {
//            for (int j = 0; j < TARGET.length(); j++) {
//                if (I.getData().charAt(j) != TARGET.charAt(j)){
//                    //System.out.println(I.getData());
//                    int randomNum = ThreadLocalRandom.current().nextInt(0,100);
//                    if (randomNum < 1) {
//                        int randomNum2 = ThreadLocalRandom.current().nextInt(0,ALPHABET.length());
//                        char[] temp =  I.getData().toCharArray();
//                        temp[j] = ALPHABET.charAt(randomNum2);
//                        StringBuilder sb = new StringBuilder();
//                        for (int i = 0; i < temp.length; i++) {
//                            sb.append(temp[i]);
//                        }
//                        I.setData(sb.toString());
//                    }
//                }
//            }
//        }
//    }


}
