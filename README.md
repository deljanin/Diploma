This is my diploma project, it is about optimization of road network topologies with genetic algorithms.
I am using a road network simulator that my colleague, and I developed. I am optimizing the network by changing intersection types (i.e. roundabout to semaphore or so) with the help of a genetic algorithm.
In each generation, I am running multiple Individuals (Simulations) in parallel to test the "fitness" of the road network. Then I'm selecting, crossing over and mutating individuals to develop the following generation.
Until I hopefully end up with a more optimized network then the one we currently have, as this is a heuristic approach to solving such a problem.
