package data;

import com.google.gson.Gson;
import entity.Individual;
import entity.Population;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataManager {
    private FileWriter fileWriter;
    private File generation_folder;
    private String separator = System.getProperty("file.separator");
    private int generation_count;
    private FileWriter csvWriter;
    private File statsFile = new File("generations" + separator + "stats.csv");

    public DataManager(int generation_count){
        this.generation_count = generation_count;
        this.generation_folder = new File("generations" + separator + generation_count);

    }

    public DataManager(){
        this.generation_folder = new File("generations" + separator + generation_count);
        create_generations_folder();
        generation_csv_initialise();
    }

    public void population_write(ConfigData data){
        File generation_folder = new File("generations" + separator + generation_count);
        generation_folder.mkdir();
        try {
            fileWriter = new FileWriter(generation_folder + separator + "config.json");
            fileWriter.write(new Gson().toJson(data));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void individual_write(String individual_name, ArrayList<IntersectionData> data){
        File individual_folder = new File(generation_folder + separator + individual_name + separator);
        individual_folder.mkdir();//if(!) System.out.println("Failed to create individual folder");
        try {
            fileWriter = new FileWriter(individual_folder + separator +"intersections.json");
            fileWriter.write(new Gson().toJson(data));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete_generation_files(Population pop){
        String path = System.getProperty("user.dir");
        String generationName = path + separator + "generations" + separator + (pop.getGeneration_count() - 1);
        File f;
        for(Individual i : pop.getPopulation()){
            if(!i.equals(pop.getFittestIndividual())) {
                f = new File(generationName + separator + i.getIndividual_name());
                delete_dir(f);
            }
        }
    }

    private boolean delete_dir(File file){
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                delete_dir(f);
            }
        }
        return file.delete();
    }


    private void generation_csv_initialise() {
        try {
            csvWriter = new FileWriter(statsFile);
            csvWriter.write("generation,population_size,mutation_factor,individual_name,best_fitness\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generation_csv_write(Individual i, Population pop, int mutationChance){
        String data = pop.getGeneration_count()-1 +","
                + pop.getPopulation_size() + ","
                + mutationChance + ","
                + i.getIndividual_name() + ","
                + i.getFitness() + "\n";
        try {
            csvWriter.write(data);
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close_csv_writer(){
        try {
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void create_generations_folder(){
        File generations_folder = new File("generations");
        generations_folder.mkdir();
    }
}
