package entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Executor extends Thread{
    private String separator = System.getProperty("file.separator");
    private String generation_configJson_path;
    private String individual_intersectionsJson_path;
    private Individual individual;
    private boolean debugMode;

    public Executor(boolean debugMode){
        this.debugMode = debugMode;
    }

    public void initialise(Individual individual){
        this.individual = individual;
        generation_configJson_path = individual.getGeneration_configJson_path();
        individual_intersectionsJson_path = individual.getIndividual_intersectionsJson_path();
    }

    @Override
    public void run() {
//        if(debugMode) System.out.println(this.getName()+ " started");
        String path = System.getProperty("user.dir");
        ProcessBuilder builder;
        String command = "cd \"" + path + separator + "simulator\" && java -jar Simulator.jar false .." + separator + generation_configJson_path + separator + "config.json" + " .." + separator + individual_intersectionsJson_path;
//        if(debugMode) System.out.println(command);
        if (System.getProperty("os.name").startsWith("Windows")) {
            builder = new ProcessBuilder(
                    "cmd.exe", "/c", command);
        } else {
            builder = new ProcessBuilder(
                    "bash", "-c", command);
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
        try {
            individual.setFitness(Integer.parseInt(out));
        }catch (NumberFormatException e){
            individual.setFitness(Integer.MAX_VALUE);
        }
        if(debugMode) System.out.println(this.getName()+ " " + individual.getIndividual_name() + " Simulation time: " + individual.getFitness());
    }

    public void start(){
        new Thread(this).start();
    }

}
