package entity;

import algorithms.GenericGA;

public class Main {
    public static void main(String[] args) {

        //*PARSE ARGS*//

        Population population = new Population(10);
        GenericGA genericGA = new GenericGA();
        Optimization optimization = new Optimization(4,population,genericGA, 10000);
        optimization.Start();
    }
}
