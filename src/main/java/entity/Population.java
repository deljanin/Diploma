package entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import data.ConfigData;
import data.DataWriter;
import data.IntersectionData;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Population {
    private Vector<Individual> population = null;
    private int population_size = 4;
    private List<IntersectionData> intersectionsData;
    private ConfigData configData;
    private ConfigData configData_generation_copy;
    private int individual_size;
    private String generation_configJson_path;
    private int generation_count;

    public Population(int population_size) {
        this.population_size = population_size;
        this.intersectionsData = loadIntersections();
        this.configData = loadConfig();
        this.configData_generation_copy = new ConfigData(configData);
        this.individual_size = intersectionsData.size();
        this.generation_count = 0;
    }

    public Population(int population_size, List<IntersectionData> intersectionsData, ConfigData configData, Vector<Individual> population, int generation_count){
        this.population_size = population_size;
        this.intersectionsData = intersectionsData;
        this.configData = configData;
        this.configData_generation_copy = new ConfigData(configData);
        this.individual_size = intersectionsData.size();
        this.population = population;
        this.generation_count = generation_count;
    }

    public void initialiseGeneration() {
//      Here we can change the config data
        configData_generation_copy.simulationSpeed = 1.0;
        DataWriter dataWriter = new DataWriter(generation_count);
        dataWriter.population_write(configData_generation_copy);
        generation_configJson_path = "generations" + System.getProperty("file.separator") + generation_count;
        if(population == null) {
            population = new Vector<>();
            for (int i = 0; i < population_size; i++) {
                population.add(new Individual(individual_size, generation_count + "_" + i, intersectionsData,  generation_configJson_path));
            }
        }else{
            for (int i = 0; i < population_size; i++) {
                population.get(i).setIndividual_name(generation_count +"_"+i);
                population.get(i).setGeneration_configJson_path(generation_configJson_path);
                population.get(i).setIndividual_intersectionsJson_path();
            }
        }

//        TODO Here we are changing the individual copies of the IntersectionsData
        population.forEach(Individual::initialise);
        System.out.println();
//        TODO Here they are not changed somehow?????

        for (int i = 0; i < population.size(); i++) {
            for (int j = 0; j < population.get(i).getIntersectionsData_individual_copy().size(); j++) {
                System.out.print(population.get(i).getIntersectionsData_individual_copy().get(j).getType());
            }
            System.out.println();
        }

        population.forEach(i -> dataWriter.individual_write(i.getIndividual_name(),i.getIntersectionsData_individual_copy()));
        this.generation_count++;
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

    public ConfigData loadConfig() {
        ConfigData config = null;
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("simulator/config.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(fileReader);
        String line;
        try {
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
            br.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        config = new Gson().fromJson(resultStringBuilder.toString(), ConfigData.class);

//        try{
//
////            config = new Gson().fromJson(Files.readString(Paths.get("simulator/config.json")), ConfigData.class);
//            } catch(IOException e)
//            {
//                e.printStackTrace();
//            }
        return config;
    }

    public Vector<Individual> getPopulation() {
        return population;
    }
    public List<IntersectionData> getIntersectionsData() {
        return intersectionsData;
    }
    public int getPopulation_size() {
        return population_size;
    }
    public ConfigData getConfigData() {
        return configData;
    }
    public int getIndividual_size() {
        return individual_size;
    }
    public int getGeneration_count() {
        return generation_count;
    }
}
