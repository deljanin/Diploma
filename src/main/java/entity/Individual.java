package entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import data.IntersectionData;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individual extends Thread{
    ArrayList<Intersection> intersections;
    private int fitness;
    private int individual_size;
    private String individual_name;
    private ArrayList<IntersectionData> intersectionsData_individual;
    private String individual_intersectionsJson_path;
    private String generation_folder;

    public Individual(int individual_size, String individual_name, List<IntersectionData> intersectionsData, String generation_folder){
        this.intersections = new ArrayList<>();
        this.individual_size = individual_size;
//TODO MAYBE THIS List copy is doing something strange
        this.intersectionsData_individual = new ArrayList<>(List.copyOf(intersectionsData));
        this.individual_name = individual_name;
        this.generation_folder = generation_folder;
        initialise();
    }
    private void initialise(/*Random random*/) {
        for (int i = 0; i < individual_size; i++) {
            Random r = new Random();
            this.intersections.add(Intersection.values()[r.nextInt(Intersection.values().length)]);
        }
        File individual_folder = new File(generation_folder + "\\" + individual_name + "\\");
        if(!individual_folder.mkdir()) System.out.println("Failed to create individual folder");

        for (int i = 0; i < intersectionsData_individual.size(); i++) {//
            IntersectionData tmp = intersectionsData_individual.get(i);
            tmp.type = type_converter(intersections.get(i));
            intersectionsData_individual.set(i, tmp);
        }

        try {
            individual_intersectionsJson_path = individual_folder+"\\intersections.json";
            Files.writeString(Path.of(individual_intersectionsJson_path),new Gson().toJson(intersectionsData_individual));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        String path = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        ProcessBuilder builder;

        if (System.getProperty("os.name").startsWith("Windows")) {
            builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"" + path + separator + "simulator\" && java -jar Simulator.jar false config.json ..\\" + individual_intersectionsJson_path);
        } else {
            builder = new ProcessBuilder(
                    "bash", "-c", "cd \"" + path + separator + "simulator\" && java -jar Simulator.jar false config.json ../" + individual_intersectionsJson_path);
        }
        builder.redirectErrorStream(true);
        Process p = null;
        String out = null;
        try {
            p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            out = r.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fitness = Integer.parseInt(out);
        System.out.println("Simulation ticks: " + fitness);
    }

    public void start(){
        new Thread(this).start();
    }

    public int getFitness() {
        return this.fitness;
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
}
