package entity;


import data.IntersectionData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individual{
    private ArrayList<Intersection> intersections_enum;
    private int fitness;
    private final int individual_size;
    private String individual_name;
    private ArrayList<IntersectionData> intersectionsData_individual_copy;
    private String individual_intersectionsJson_path;
    private String generation_configJson_path;

    String separator = System.getProperty("file.separator");

//    New Individual
    public Individual(int individual_size, String individual_name, List<IntersectionData> intersectionsData, String generation_configJson_path){
        this.individual_size = individual_size;
        this.intersectionsData_individual_copy = new ArrayList<>();
        for(IntersectionData id: intersectionsData){
            this.intersectionsData_individual_copy.add(new IntersectionData(id));
        }
        this.individual_name = individual_name;
        this.generation_configJson_path = generation_configJson_path;
        setIndividual_intersectionsJson_path();
        randomize_intersection();
    }

    public Individual(int individual_size, String individual_name, List<IntersectionData> intersectionsData, ArrayList<Intersection> intersections_enum, String generation_configJson_path){
        this.individual_size = individual_size;
        this.intersectionsData_individual_copy = new ArrayList<>();
        for(IntersectionData id: intersectionsData){
            this.intersectionsData_individual_copy.add(new IntersectionData(id));
        }
        this.individual_name = individual_name;
        this.intersections_enum = intersections_enum;
        this.generation_configJson_path = generation_configJson_path;
        setIndividual_intersectionsJson_path();
    }


    public void initialise() {
        for (int i = 0; i < this.intersectionsData_individual_copy.size(); i++) {
            if(this.intersectionsData_individual_copy.get(i).type != 0 && intersectionsData_individual_copy.get(i).type != 5){
                this.intersectionsData_individual_copy.get(i).setType(type_converter(intersections_enum.get(i)));
            }
        }
    }

    private void randomize_intersection(){
        this.intersections_enum = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < individual_size; i++) {
            this.intersections_enum.add(Intersection.values()[r.nextInt(Intersection.values().length)]);
        }
    }

    public void setIndividual_intersectionsJson_path(){
        this.individual_intersectionsJson_path = generation_configJson_path + separator + individual_name + separator +"intersections.json";
    }

    public int getFitness() {
        return this.fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int type_converter(Intersection i){
        switch (i){
            case BASIC -> {
                return 1;
            }
            case SEMAPHORE -> {
                return 2;
            }
            case ROUNDABOUT -> {
                return 3;
            }

        }
        System.out.println("Type conversion error");
        return 1;
    }

    public ArrayList<Intersection> getIntersections_enum() {
        return intersections_enum;
    }

    public void setIndividual_name(String individual_name) {
        this.individual_name = individual_name;
    }

    public String getGeneration_configJson_path() {
        return generation_configJson_path;
    }

    public void setGeneration_configJson_path(String generation_configJson_path) {
        this.generation_configJson_path = generation_configJson_path;
    }

    public ArrayList<IntersectionData> getIntersectionsData_individual_copy() {
        return intersectionsData_individual_copy;
    }

    public String getIndividual_name() {
        return individual_name;
    }

    public String getIndividual_intersectionsJson_path() {
        return individual_intersectionsJson_path;
    }

    public void print() {
        for (int i = 0; i < intersections_enum.size(); i++) {
            if(i==(intersections_enum.size()/2)) System.out.print(" ");
            System.out.print(type_converter(intersections_enum.get(i)));
        }
        System.out.println();
    }
}