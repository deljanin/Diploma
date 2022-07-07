package entity;

import algorithms.GenericGA;
import com.google.gson.Gson;
import data.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        String configPath = "config.json";
        if(args.length != 0 ){
            configPath = args[0];
        }
        Config config = null;
        try {
            config = new Gson().fromJson(Files.readString(Paths.get(configPath)), Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Population population = new Population(4); //Take divisible by 4 to work for basicGA
        GenericGA genericGA = new GenericGA();
        Optimization optimization = new Optimization(4,population,genericGA, config.getStopCondition(), config.getMutationChance());
        optimization.Start();
    }

//    TODO Thread Error
}
