package algorithms;

import entity.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RankGA extends GA{

    private Tuple crossoverPair(Individual individual1, Individual individual2, Population pop){
        int end = individual1.getIntersections_enum().size();
        int half = end/2;
        List<Intersection> individual1_firstHalf = individual1.getIntersections_enum().subList(0,half);
        List<Intersection> individual2_firstHalf = individual2.getIntersections_enum().subList(0,half);

        List<Intersection> individual1_secondHalf = individual1.getIntersections_enum().subList(half,end);
        List<Intersection> individual2_secondHalf = individual2.getIntersections_enum().subList(half,end);

        ArrayList<Intersection> indi1 = new ArrayList<>(individual1_firstHalf);
        indi1.addAll(individual2_secondHalf);
        ArrayList<Intersection> indi2 = new ArrayList<>(individual2_firstHalf);
        indi2.addAll(individual1_secondHalf);
        return new Tuple(
                new Individual(pop.getIntersectionsData().size(),
                        "ToBeSet",
                        pop.getIntersectionsData(),
                        indi1,
                        individual1.getGeneration_configJson_path()
                ),

                new Individual(pop.getIntersectionsData().size(),
                        "ToBeSet",
                        pop.getIntersectionsData(),
                        indi2,
                        individual1.getGeneration_configJson_path()
                ));
    }
    @Override
    public Population crossover(Population pop) {
        Vector<Individual> newGen = new Vector<>(pop.getPopulation().size());
        for (int i = 0; i < pop.getPopulation().size(); i=i+2) {
            Tuple t = crossoverPair(pop.getPopulation().get(i), pop.getPopulation().get(i+1), pop);
            newGen.add(t.getFirst());
            newGen.add(t.getSecond());
        }
        newGen.addAll(pop.getPopulation().subList(0,pop.getPopulation().size()));
        pop.setPopulation(newGen);
        return pop;
//        return new Population(pop.getPopulation_size(), pop.getIntersectionsData(), pop.getConfigData(), newGen, pop.getGeneration_count());
    }

    private Intersection[] makeIntersectionArrWoutSpecific(Intersection i){
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
                if(ThreadLocalRandom.current().nextInt(0,100)<=mutation_chance) {
                    switch (pop.getPopulation().get(i).getIntersections_enum().get(j)){
                        case BASIC:{
                            mutatedPopulation.get(i).getIntersections_enum().set(j,intersectionsWoutBASIC[r.nextInt(intersectionsWoutBASIC.length)]);
                        }
                        case SEMAPHORE:{
                            mutatedPopulation.get(i).getIntersections_enum().set(j,intersectionsWoutSEMAPHORE[r.nextInt(intersectionsWoutSEMAPHORE.length)]);
                        }
                        case ROUNDABOUT:{
                            mutatedPopulation.get(i).getIntersections_enum().set(j,intersectionsWoutROUNDABOUT[r.nextInt(intersectionsWoutROUNDABOUT.length)]);
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
        Vector<Individual> newGen = new Vector<>(popSize/2);

//        pop.getPopulation().forEach(i -> System.out.print(i.getFitness() + " "));
//        System.out.println();
//        for (int i = 0; i < popSize; i++) {
//            int r = 1, s = 1;
//
//            for (int j = 0; j < popSize; j++)
//            {
//                if (j != i && pop.getPopulation().get(j).getFitness() < pop.getPopulation().get(i).getFitness())
//                    r += 1;
//
//                if (j != i && pop.getPopulation().get(j).getFitness() == pop.getPopulation().get(i).getFitness())
//                    s += 1;
//            }
//            // Use formula to obtain  rank
//            pop.getPopulation().get(i).setFitness(r + (float)(s - 1) / (float) 2);
//        }
//
//        pop.getPopulation().forEach(i -> System.out.print(i.getFitness() + " "));
//        System.out.println();


//        Collections.sort(pop.getPopulation(), new IndividualComparator());
//        for (int i = 0; i < popSize / 2; i++) {
//            newGen.add(pop.getPopulation().get(i));
//        }

//        Collections.sort(newGen, new IndividualComparator());

        float generationFitness = 0;
        for (int i = 0; i < popSize; i++) {
            generationFitness += pop.getPopulation().get(i).getFitness();
        }





        System.out.println("Pop size inside GA class: " + newGen.size());
        pop.setPopulation(newGen);
        pop.getPopulation().forEach(i -> System.out.print(i.getFitness() + " "));
        System.out.println();
        return pop;
    }
}
