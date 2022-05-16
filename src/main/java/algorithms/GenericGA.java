package algorithms;

import entity.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static entity.Intersection.*;

public class GenericGA extends GA{

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
                        "ToBeSet"),

                new Individual(pop.getIntersectionsData().size(),
                        "ToBeSet",
                        pop.getIntersectionsData(),
                        indi2,
                        "ToBeSet"
                ));
    }
    @Override
    public Population crossover(Population pop) {
        Vector<Individual> newGen = new Vector<>(pop.getPopulation().size());
        Collections.sort(pop.getPopulation(), new IndividualComparator());
        for (int i = 0; i < pop.getPopulation().size()/2; i=i+2) {
            Tuple t = crossoverPair(pop.getPopulation().get(i), pop.getPopulation().get(i+1), pop);
            newGen.add(t.getFirst());
            newGen.add(t.getSecond());
        }
        newGen.addAll(pop.getPopulation().subList(0,pop.getPopulation().size()/2));
//        newGen.forEach(i -> System.out.println(i.getIntersections_enum()));
        return new Population(pop.getGeneration_size(),pop.getIntersectionsData(), pop.getConfigData(), newGen);
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

        Intersection[] intersectionsWoutBASIC = makeIntersectionArrWoutSpecific(BASIC);
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
        return new Population(pop.getPopulation().size(), pop.getIntersectionsData(), pop.getConfigData() ,mutatedPopulation);
    }

//    TODO What is select supposed to do?
    @Override
    public Population select(Population population) {

        return null;
    }
}
