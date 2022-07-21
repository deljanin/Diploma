package algorithms;

import entity.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TournamentUniformGA extends GA{
    //    for (int i = 0; i <a.size(); i++) {
//        if(Math.random() < crossoverProbability){
//            int tmp = a.get(i);
//            a.set(i, b.get(i));
//            b.set(i, tmp);
//        }
//    }

    private Tuple crossoverPair(Individual individual1, Individual individual2, Population pop){
        ArrayList<Intersection> child1 = new ArrayList<>(pop.getIndividual_size());
        ArrayList<Intersection> child2 = new ArrayList<>(pop.getIndividual_size());
        Random r = new Random();
//        int count = 0;
        for (int i = 0; i < pop.getIndividual_size(); i++) {
            if( r.nextFloat() < 0.5){
                child1.add(individual2.getIntersections_enum().get(i));
                child2.add(individual1.getIntersections_enum().get(i));
//                count++;
            }else{
                child1.add(individual1.getIntersections_enum().get(i));
                child2.add(individual2.getIntersections_enum().get(i));
            }
        }
//            System.out.println(count+ " " + pop.getIndividual_size());

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
        //1.Select k individuals from the population and perform a tournament amongst them
        //2.Select the best individual from the k individuals
        //3. Repeat process 1 and 2 until you have the desired amount of population

        Vector<Individual> newGen = new Vector<>(pop.getPopulation().size()/2);

        int tournamentSize = 5;
        Vector<Individual> tournamentIndividuals = new Vector<>(tournamentSize);
        for (int i = 0; i < pop.getPopulation_size()/2; i++) {
            Collections.shuffle(pop.getPopulation());
            for (int j = 0; j < tournamentSize; j++) {
                tournamentIndividuals.add(pop.getPopulation().get(j));
            }
//            tournamentIndividuals.forEach(ind -> System.out.print(ind.getFitness() + " "));
//            System.out.println();
            Collections.sort(tournamentIndividuals,new IndividualComparator());
//            System.out.println(tournamentIndividuals.get(0).getFitness());
            pop.getPopulation().remove(tournamentIndividuals.get(0));
            newGen.add(tournamentIndividuals.get(0));
            tournamentIndividuals.clear();
        }
        pop.setPopulation(newGen);

        return pop;
//        return new Population(pop.getPopulation_size(), pop.getIntersectionsData(), pop.getConfigData(), newGen, pop.getGeneration_count());
    }
}

