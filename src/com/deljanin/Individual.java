package com.deljanin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Individual extends Thread{
//    Binary vector ...
    private int fitness;

    public Individual(){
    }
//       Executes a simulation
//        fitness is time orr?

    @Override
    public void run() {
        String path = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        ProcessBuilder builder;
        if (System.getProperty("os.name").startsWith("Windows")) {
            builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"" + path + separator + "simulator\" && java -jar Simulator.jar");
        } else {
            builder = new ProcessBuilder(
                    "bash", "-c", "cd \"" + path + separator + "simulator\" && java -jar Simulator.jar");
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
        this.fitness = Integer.parseInt(out);
        System.out.println("Simulation ticks: " + fitness);
    }
    public void start(){
        new Thread(this).start();
    }

    public int getFitness() {
        return this.fitness;
    }

}
