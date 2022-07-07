package data;

public class Config {
    int stopCondition = 10000;
    int mutationChance = 10;

    public Config(Config config){
        this.stopCondition = config.getStopCondition();
        this.mutationChance = config.getMutationChance();
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
