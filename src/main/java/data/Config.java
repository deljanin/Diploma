package data;

public class Config {
    int population_size;
    int thread_count;
    int max_generations;
    int mutationChance;
    double simulationSpeed;
    int seed;
    int timeInSec;
    String GA;

    public Config(Config config){
        this.population_size = config.getPopulation_size();
        this.thread_count = config.getThread_count();
        this.max_generations = config.getMaxGenerations();
        this.mutationChance = config.getMutationChance();
        this.simulationSpeed = config.getSimulationSpeed();
        this.seed = config.getSeed();
        this.timeInSec = config.getTimeInSec();
        this.GA = config.getGA();
    }
    public Config(){
        this.population_size = 40;
        this.thread_count = Runtime.getRuntime().availableProcessors();
        this.max_generations = 10000;
        this.mutationChance = 10;
        this.simulationSpeed = 1;
        this.seed = 5;
        this.timeInSec = 21600;
        this.GA = "GenericGA";
    }

    public int getPopulation_size() {
        return population_size;
    }

    public void setPopulation_size(int population_size) {
        this.population_size = population_size;
    }

    public int getThread_count() {
        return thread_count;
    }

    public void setThread_count(int thread_count) {
        this.thread_count = thread_count;
    }

    public int getMaxGenerations() {
        return max_generations;
    }

    public void setStopCondition(int max_generations) {
        this.max_generations = max_generations;
    }

    public int getMutationChance() {
        return mutationChance;
    }

    public void setMutationChance(int mutationChance) {
        this.mutationChance = mutationChance;
    }

    public double getSimulationSpeed() {
        return simulationSpeed;
    }

    public void setSimulationSpeed(double simulationSpeed) {
        this.simulationSpeed = simulationSpeed;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getTimeInSec() {
        return timeInSec;
    }

    public void setTimeInSec(int timeInSec) {
        this.timeInSec = timeInSec;
    }

    public String getGA() {
        return GA;
    }

    public void setGA(String GA) {
        this.GA = GA;
    }
}
