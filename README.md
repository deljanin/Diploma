Before going into this project learn how genetic algorithms work, at this website so you can understand the jist of what I was trying to do: https://towardsdatascience.com/introduction-to-genetic-algorithms-including-example-code-e396e98d8bf3 

I am using a road network simulator that my colleague, and I developed. ( This is my fitness function. ) I am optimizing the network by changing intersection types (i.e. roundabout to semaphore or so) with the help of a genetic algorithm.
In each generation, I am running multiple Individuals (City topologies) in parallel to test the "fitness" of the road network. Then I'm selecting, crossing over, and mutating individuals to develop the next generation.
Until I hopefully end up with a more optimized network than the one we currently have, as this is a heuristic approach to solving such a problem.

To see the code on your device follow these steps:
1. Make sure you have the latest version of JAVA installed. ( https://www.oracle.com/java/technologies/downloads/ )
2. Make sure you have some editor installed, I recommend IntelliJ IDEA. ( https://www.jetbrains.com/idea/download/ )
3. Make sure you have git installed. ( https://git-scm.com/downloads )
4. Open a terminal/cmd that can use git. Then run this command: git clone https://github.com/deljanin/diploma.git
5. Open the new directory with an Editor. You can see the code here. I don't recommend running the code as it is a CLI program.

To run the program continue with:
6. Select the latest version of JAVA JDK in the settings. ( Version 19. as of now. )
7. Find the Main() function in: diploma/src/main/java/entity/Main.java
8. Right-click anywhere on the code panel and run the program from there, or just find a way to run this program from Main().
