package entity;

import algorithms.*;
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
                "\nPopulation size  = " + config.getPopulation_size() +
                "\nThread count     = " + config.getThread_count() +
                "\nMax generations  = " + config.getMaxGenerations() +
                "\nMutation chance  = " + config.getMutationChance() +
                "\nSimulation speed = " + config.getSimulationSpeed() +
                "\nSimulation seed  = " + config.getSeed() +
                "\nTime in seconds  = " + config.getTimeInSec() +
                "\nGenetic algo     = " + config.getGA()
        );
        Population population = new Population(config);
        GA GA;
        switch (config.getGA()) {
            case "GenericGA":
                GA = new GenericGA();
                break;
            case "TournamentGA":
                GA = new TournamentGA();
                break;
            case "Tournament2PointGA":
                GA = new Tournament2PointGA();
                break;
            case "TournamentUniformGA":
                GA = new TournamentUniformGA();
                break;
            case "RankGA":
                GA = new RankGA();
                break;
            case "Rank2PointGA":
                GA = new Rank2PointGA();
                break;
            case "RankUniformGA":
                GA = new RankUniformGA();
                break;
            default:
                GA = new GenericGA();
        }


        Optimization optimization = new Optimization(config.getThread_count(), population, GA, config.getMaxGenerations(), config.getMutationChance(), config.getGA());
        optimization.Start(debugMode);
    }
}
