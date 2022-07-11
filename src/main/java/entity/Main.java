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
        boolean debugMode = false;
        if(args.length != 0){
            configPath = args[0];
            debugMode = Boolean.parseBoolean(args[1]);
        }
        Config config;
        try {
            config = new Gson().fromJson(Files.readString(Paths.get(configPath)), Config.class);
        } catch (IOException e) {
            config = new Config();
        }
        System.out.println("Config parameters are: " +
                "\nStop condition  = " + config.getStopCondition() +
                "\nSimulation time = " + config.getTimeInSec() +
                "\nPopulation size = " + config.getPopulation_size()
        );
        Population population = new Population(config);
        GenericGA genericGA = new GenericGA();
        Optimization optimization = new Optimization(config.getThread_count(), population, genericGA, config.getStopCondition(), config.getMutationChance());
        optimization.Start(debugMode);
    }
}
