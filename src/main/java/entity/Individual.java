package entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import data.IntersectionData;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individual extends Thread{
    ArrayList<Intersection> intersections;
    private int fitness;
    private String intersections_file;
    private int individual_size;

    public Individual(int individual_size/*, String intersections_file*/){
        this.intersections = new ArrayList<>();
        this.individual_size = individual_size;
        this.intersections_file = intersections_file;
        initialise();
    }
    private void initialise(/*Random random*/) {
        for (int i = 0; i < individual_size; i++) {
            Random r = new Random();
            this.intersections.add(Intersection.values()[r.nextInt(Intersection.values().length)]);
        }
//        System.out.println("Thread: " + this.hashCode());
//        intersections.forEach(i -> System.out.print(i));
//        System.out.println();
    }

    @Override
    public void run() {
        String path = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        ProcessBuilder builder;
        if (System.getProperty("os.name").startsWith("Windows")) {
            builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"" + path + separator + "simulator\" && java -jar Simulator.jar");
        } else {
            builder = new ProcessBuilder(
                    "bash", "-c", "cd \"" + path + separator + "simulator\" && java -jar Simulator.jar");
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

}
