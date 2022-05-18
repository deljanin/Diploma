package entity;

import com.google.gson.Gson;
import data.IntersectionData;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Individual extends Thread{
    private ArrayList<Intersection> intersections_enum;
    private int fitness;
    private final int individual_size;
    private String individual_name;
    private ArrayList<IntersectionData> intersectionsData_individual_copy;
    private String individual_intersectionsJson_path;
    private String generation_configJson_path;
    private String generation_folder;
    String separator = System.getProperty("file.separator");
    private CyclicBarrier cyclicBarrier;

    public Individual(int individual_size, String individual_name, List<IntersectionData> intersectionsData, String generation_folder, String generation_configJson_path){
        this.individual_size = individual_size;
        this.intersectionsData_individual_copy = new ArrayList<>(List.copyOf(intersectionsData));
        this.individual_name = individual_name;
        this.generation_folder = generation_folder;
        this.generation_configJson_path = generation_configJson_path;
        randomize_intersection();
        initialise();
    }

    public Individual(int individual_size, String individual_name, List<IntersectionData> intersectionsData, ArrayList<Intersection> intersections_enum, String generation_folder){
        this.individual_size = individual_size;
        this.intersectionsData_individual_copy = new ArrayList<>(List.copyOf(intersectionsData));
        this.individual_name = individual_name;
        this.generation_folder = generation_folder;
        this.intersections_enum = intersections_enum;
    }

    public void initialise() {
        File individual_folder = new File(generation_folder + separator + individual_name + separator);
        if(!individual_folder.mkdir()) System.out.println("Failed to create individual folder");

        for (int i = 0; i < intersectionsData_individual_copy.size(); i++) {
            if(intersectionsData_individual_copy.get(i).type != 0) intersectionsData_individual_copy.get(i).setType(type_converter(intersections_enum.get(i)));
        }

        try {
            individual_intersectionsJson_path = individual_folder+ separator +"intersections.json";
            Files.writeString(Path.of(individual_intersectionsJson_path),new Gson().toJson(intersectionsData_individual_copy));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void randomize_intersection(){
        this.intersections_enum = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < individual_size; i++) {
            this.intersections_enum.add(Intersection.values()[r.nextInt(Intersection.values().length)]);
        }
    }

    @Override
    public void run() {
        System.out.println(this.getName()+ " started");
        String path = System.getProperty("user.dir");

        ProcessBuilder builder;
        if (System.getProperty("os.name").startsWith("Windows")) {
            builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"" + path + separator + "simulator\" && java -jar Simulator.jar false .." + separator + generation_configJson_path  + " .." + separator + individual_intersectionsJson_path);
        } else {
            builder = new ProcessBuilder(
                    "bash", "-c", "cd \"" + path + separator + "simulator\" && java -jar Simulator.jar false config.json .." + separator + generation_configJson_path  + " .." + separator + individual_intersectionsJson_path);
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
        System.out.println(individual_name+" Simulation ticks: " + fitness);
        try {
            this.cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
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

    public ArrayList<Intersection> getIntersections_enum() {
        return intersections_enum;
    }

    public void setIndividual_name(String individual_name) {
        this.individual_name = individual_name;
    }

    public void setGeneration_folder(String generation_folder) {
        this.generation_folder = generation_folder;
    }

    public void setGeneration_configJson_path(String generation_configJson_path) {
        this.generation_configJson_path = generation_configJson_path;
    }

    public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }
}