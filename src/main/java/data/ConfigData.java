package data;

public class ConfigData {
    public double simulationSpeed;
    public int seed;

    public ConfigData(ConfigData config){
        this.simulationSpeed = config.getSimulationSpeed();
        this.seed = config.getSeed();
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

}
