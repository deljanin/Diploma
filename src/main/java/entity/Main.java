package entity;

import algorithms.GenericGA;

public class Main {
    public static void main(String[] args) {
        Population population = new Population(4);
        GenericGA genericGA = new GenericGA();
        Optimization optimization = new Optimization(population,genericGA);
        optimization.Start();
    }
}
