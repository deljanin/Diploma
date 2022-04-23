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
    private Vector<Individual> population = null;
    private int generation_size = 4;
    private List<IntersectionData> intersectionsData;
    private int individual_size;

    public Population(int generation_size) {
        this.generation_size = generation_size;
        this.intersectionsData = loadIntersections();
        this.individual_size = intersectionsData.size();
    }

    public void startGeneration(){
        population.forEach(Individual::start);
    }

    public void initializeGeneration(String generation_name) {
        File generation_folder = new File("generations\\"+generation_name);

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
        if(population == null) {
            population = new Vector<>();
            for (int i = 0; i < generation_size; i++) {
                population.add(new Individual(individual_size, generation_name + "_" + i, intersectionsData, generation_folder.toString()));
            }
        }else{
            for (int i = 0; i < generation_size; i++) {
                population.get(i).setIndividual_name(generation_name +"_"+i);
                population.get(i).setGeneration_folder("generations\\" + generation_name);
            }
            population.forEach(Individual::initialise);
        }
    }

    private Tuple crossover(Individual individual1, Individual individual2){
        int end = individual1.getIntersections_enum().size();
        int half = end/2;
        List<Intersection> individual1_firstHalf = individual1.getIntersections_enum().subList(0,half);
        List<Intersection> individual2_firstHalf = individual2.getIntersections_enum().subList(0,half);

        List<Intersection> individual1_secondHalf = individual1.getIntersections_enum().subList(half,end);
        List<Intersection> individual2_secondHalf = individual2.getIntersections_enum().subList(half,end);

        ArrayList<Intersection> indi1 = new ArrayList<>(individual1_firstHalf);
        indi1.addAll(individual2_secondHalf);
        ArrayList<Intersection> indi2 = new ArrayList<>(individual2_firstHalf);
        indi2.addAll(individual1_secondHalf);
        /*TODO NAME & GEN FOLDER*/
        return new Tuple(
                new Individual(individual_size,
                "ToBeSet",
                        intersectionsData,
                        indi1,
                "ToBeSet"),

                new Individual(individual_size,
                        "ToBeSet",
                        intersectionsData,
                        indi2,
                        "ToBeSet"
                ));
    }

    public void newGeneration(String generation_name) {
        Vector<Individual> newGen = new Vector<>(population.size());
        Collections.sort(population, new IndividualComparator());
        Collections.reverse(population);
        for (int i = 0; i < population.size()/2; i=i+2) {
            Tuple t = crossover(population.get(i), population.get(i+1));
            newGen.add(t.getFirst());
            newGen.add(t.getSecond());
        }
        newGen.addAll(population.subList(0,population.size()/2));
        this.population = newGen;
    }

    public void calculateFitness(String generation_name){
        initializeGeneration(generation_name);
        startGeneration();
    }

    public void mutate() {
        Random r = new Random();
        Intersection[] intersectionsWoutBASIC = makeIntersectionArrWoutSpecific(Intersection.BASIC);
        Intersection[] intersectionsWoutSEMAPHORE = makeIntersectionArrWoutSpecific(Intersection.SEMAPHORE);
        Intersection[] intersectionsWoutROUNDABOUT = makeIntersectionArrWoutSpecific(Intersection.ROUNDABOUT);
        for (int i = 0; i < population.size(); i++) {
            for (int j = 0; j < individual_size; j++) {
                if(ThreadLocalRandom.current().nextInt(0,100)<=10) {
                    switch (population.get(i).getIntersections_enum().get(j)){
                        case BASIC:{
                            population.get(i).getIntersections_enum().set(j,intersectionsWoutBASIC[r.nextInt(intersectionsWoutBASIC.length)]);
                        }
                        case SEMAPHORE:{
                            population.get(i).getIntersections_enum().set(j,intersectionsWoutSEMAPHORE[r.nextInt(intersectionsWoutSEMAPHORE.length)]);
                        }
                        case ROUNDABOUT:{
                            population.get(i).getIntersections_enum().set(j,intersectionsWoutROUNDABOUT[r.nextInt(intersectionsWoutROUNDABOUT.length)]);
                        }
                    }
                }
            }
        }
    }

    private Intersection[] makeIntersectionArrWoutSpecific(Intersection i){
        ArrayList<Intersection> tmp = new ArrayList<>();
        for (int z = 0; z < Intersection.values().length; z++) {
            if (Intersection.values()[z] != i) tmp.add(Intersection.values()[z]);
        }
        return tmp.toArray(new Intersection[0]);
    }

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

    public Vector<Individual> getPopulation() {
        return population;
    }
}
