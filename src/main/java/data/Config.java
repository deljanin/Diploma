package data;

public class Config {
    int population_size = 4;
    int thread_count = 4;
    int stopCondition = 10000;
    int mutationChance = 10;

    public Config(Config config){
        this.population_size = config.getPopulation_size();
        this.thread_count = config.getThread_count();
        this.stopCondition = config.getStopCondition();
        this.mutationChance = config.getMutationChance();
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

    public int getStopCondition() {
        return stopCondition;
    }

    public void setStopCondition(int stopCondition) {
        this.stopCondition = stopCondition;
    }

    public int getMutationChance() {
        return mutationChance;
    }

    public void setMutationChance(int mutationChance) {
        this.mutationChance = mutationChance;
    }

}