package algorithms;

import entity.Population;

public abstract class GA {
    public abstract Population crossover(Population population);
    public abstract Population mutate(Population population, int mutation_chance);
    public abstract Population select(Population population);
}
