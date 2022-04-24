package data;

public class ConfigData {
    public double simulationSpeed;
    public int seed;
    public int numberOfVehicles;

    public ConfigData(ConfigData config){
        this.simulationSpeed = config.getSimulationSpeed();
        this.seed = config.getSeed();
        this.numberOfVehicles = config.getNumberOfVehicles();
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

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(int numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }
}
