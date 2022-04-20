package entity;

import java.util.Comparator;

public class IndividualComparator implements Comparator<Individual> {
    @Override
    public int compare(Individual individual1, Individual individual2) {
        return individual1.getFitness() - individual2.getFitness();
    }
}
