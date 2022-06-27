package entity;

import algorithms.GenericGA;

public class Main {
    public static void main(String[] args) {
        int stopCondition = 10000;

        if(args.length != 0 ){
            stopCondition = Integer.parseInt(args[0]);
        }
        //*PARSE ARGS*//

        Population population = new Population(4); //Take divisible by 4 to work for basicGA
        GenericGA genericGA = new GenericGA();
        Optimization optimization = new Optimization(4,population,genericGA, stopCondition);
        optimization.Start();
    }
}
