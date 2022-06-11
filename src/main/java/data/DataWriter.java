package data;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {
    private FileWriter fileWriter;
    private File generation_folder;
    private String separator = System.getProperty("file.separator");
    private int generation_count;

    public DataWriter(int generation_count){
        this.generation_count = generation_count;
        generation_folder = new File("generations" + separator + generation_count);
    }

    public void population_write(String data){
        File generation_folder = new File("generations" + separator + generation_count);
        if(!generation_folder.mkdir()) System.out.println("Failed to create generation folder");
        try {
            fileWriter = new FileWriter(generation_folder + separator + "config.json");
            fileWriter.write(new Gson().toJson(data));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void individual_write(String individual_name, String data){
        File individual_folder = new File(generation_folder + separator + individual_name + separator);
        if(!individual_folder.mkdir()) System.out.println("Failed to create individual folder");

        try {
            fileWriter = new FileWriter(individual_folder + separator +"intersections.json");
            fileWriter.write(new Gson().toJson(data));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
