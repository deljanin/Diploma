package algorithms;

import entity.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RankUniformGA extends GA {

    private Tuple crossoverPair(Individual individual1, Individual individual2, Population pop){
        ArrayList<Intersection> child1 = new ArrayList<>(pop.getIndividual_size());
        ArrayList<Intersection> child2 = new ArrayList<>(pop.getIndividual_size());
        Random r = new Random();
        for (int i = 0; i < pop.getIndividual_size(); i++) {
            if( r.nextFloat() < 0.5){
                child1.add(individual2.getIntersections_enum().get(i));
                child2.add(individual1.getIntersections_enum().get(i));
            }else{
                child1.add(individual1.getIntersections_enum().get(i));
                child2.add(individual2.getIntersections_enum().get(i));
            }
        }

        return new Tuple(
                new Individual(pop.getIntersectionsData().size(),
                        "ToBeSet",
                        pop.getIntersectionsData(),
                        child1,
                        individual1.getGeneration_configJson_path()
                ),

                new Individual(pop.getIntersectionsData().size(),
                        "ToBeSet",
                        pop.getIntersectionsData(),
                        child2,
                        individual1.getGeneration_configJson_path()
                ));
    }

    @Override
    public Population crossover(Population pop) {
        Vector<Individual> newGen = new Vector<>(pop.getPopulation().size());
        for (int i = 0; i < pop.getPopulation().size(); i = i + 2) {
            Tuple t = crossoverPair(pop.getPopulation().get(i), pop.getPopulation().get(i + 1), pop);
            newGen.add(t.getFirst());
            newGen.add(t.getSecond());
        }
        newGen.addAll(pop.getPopulation().subList(0, pop.getPopulation().size()));
        pop.setPopulation(newGen);
        return pop;
//        return new Population(pop.getPopulation_size(), pop.getIntersectionsData(), pop.getConfigData(), newGen, pop.getGeneration_count());
    }

    private Intersection[] makeIntersectionArrWoutSpecific(Intersection i) {
        ArrayList<Intersection> tmp = new ArrayList<>();
        for (int z = 0; z < Intersection.values().length; z++) {
            if (Intersection.values()[z] != i) tmp.add(Intersection.values()[z]);
        }
        return tmp.toArray(new Intersection[0]);
    }


    @Override
    public Population mutate(Population pop, int mutation_chance) {
        Random r = new Random();
        Vector<Individual> mutatedPopulation = new Vector<>(pop.getPopulation());

        Intersection[] intersectionsWoutBASIC = makeIntersectionArrWoutSpecific(Intersection.BASIC);
        Intersection[] intersectionsWoutSEMAPHORE = makeIntersectionArrWoutSpecific(Intersection.SEMAPHORE);
        Intersection[] intersectionsWoutROUNDABOUT = makeIntersectionArrWoutSpecific(Intersection.ROUNDABOUT);
        for (int i = 0; i < pop.getPopulation().size(); i++) {
            for (int j = 0; j < pop.getIndividual_size(); j++) {
                if (ThreadLocalRandom.current().nextInt(0, 100) <= mutation_chance) {
                    switch (pop.getPopulation().get(i).getIntersections_enum().get(j)) {
                        case BASIC: {
                            mutatedPopulation.get(i).getIntersections_enum().set(j, intersectionsWoutBASIC[r.nextInt(intersectionsWoutBASIC.length)]);
                        }
                        case SEMAPHORE: {
                            mutatedPopulation.get(i).getIntersections_enum().set(j, intersectionsWoutSEMAPHORE[r.nextInt(intersectionsWoutSEMAPHORE.length)]);
                        }
                        case ROUNDABOUT: {
                            mutatedPopulation.get(i).getIntersections_enum().set(j, intersectionsWoutROUNDABOUT[r.nextInt(intersectionsWoutROUNDABOUT.length)]);
                        }
                    }
                }
            }
        }
        pop.setPopulation(mutatedPopulation);
        return pop;
//        return new Population(pop.getPopulation().size(), pop.getIntersectionsData(), pop.getConfigData() ,mutatedPopulation, pop.getGeneration_count());
    }

    @Override
    public Population select(Population pop) {
        int popSize = pop.getPopulation_size();
        Vector<Individual> newGen = new Vector<>(popSize / 2);

        // Sweep through all elements in A
        // for each element count the number
        // of less than and equal elements
        // separately in r and s
        pop.getPopulation().forEach(i -> System.out.print(i.getFitness() + " "));
        System.out.println();
        for (int i = 0; i < popSize; i++) {
            int r = 1, s = 1;

            for (int j = 0; j < popSize; j++) {
                if (j != i && pop.getPopulation().get(j).getFitness() < pop.getPopulation().get(i).getFitness())
                    r += 1;

                if (j != i && pop.getPopulation().get(j).getFitness() == pop.getPopulation().get(i).getFitness())
                    s += 1;
            }
            // Use formula to obtain  rank
            pop.getPopulation().get(i).setFitness(r + (float) (s - 1) / (float) 2);
        }

        pop.getPopulation().forEach(i -> System.out.print(i.getFitness() + " "));
        System.out.println();


        Collections.sort(pop.getPopulation(), new IndividualComparator());
        for (int i = 0; i < popSize / 2; i++) {
            newGen.add(pop.getPopulation().get(i));
        }

//        Collections.sort(newGen, new IndividualComparator());

        System.out.println("Pop size inside GA class: " + newGen.size());
        pop.setPopulation(newGen);
        pop.getPopulation().forEach(i -> System.out.print(i.getFitness() + " "));
        System.out.println();
        return pop;
    }
}
