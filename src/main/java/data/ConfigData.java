package data;

public class ConfigData {
    public double simulationSpeed;
    public int seed;
    public int timeInSec;

    public ConfigData(ConfigData config){
        this.simulationSpeed = config.getSimulationSpeed();
        this.seed = config.getSeed();
        this.timeInSec = config.getTimeInSec();
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
}
