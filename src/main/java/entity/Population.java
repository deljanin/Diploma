package entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import data.IntersectionData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Population {
    private Vector<Individual> population;
    private int generation_size = 4;
    private List<IntersectionData> intersectionsData;

    public Population(int generation_size) {
        this.generation_size = generation_size;
        this.intersectionsData = loadIntersections();
        initializeGeneration(intersectionsData.size());
        startGeneration();
    }

    public void initializeGeneration(int individual_size) {
        File generation_folder = new File("generations\\"+"Gen0");


//TODO Check this auto remove!!
        try {
            Files.walk(generation_folder.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(!generation_folder.mkdir()) System.out.println("Failed to create generation folder");
        population = new Vector<>();
        for (int i = 0; i < generation_size; i++) {
            population.add(new Individual(individual_size,"0_"+i, intersectionsData, generation_folder.toString()));
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

    public List<IntersectionData> loadIntersections(){
        Type listType = new TypeToken<ArrayList<IntersectionData>>() {}.getType();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader("simulator/intersections.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(reader, listType);
    }

}
