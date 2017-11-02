import java.util.Random;

/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class PSO<PT> {

    private final int POPULATION_SIZE = 16;
    private final int ITERATIONS = 10000;

    private final double INERTIA_COMPONENT = 1; // Tendency to save current velocity
    private final double COGNITIVE_COMPONENT = 1; // Tendency to return to local best
    private final double SOCIAL_COMPONENT = 1; // Tendency to return to global best

    private PT[] population;

    private PT globalBest;
    private double bestFitness;

    PSO() {
        population = (PT[]) new Object[POPULATION_SIZE];
    }

    /**
     * @return Best particle
     */
    public PT execute() {
        generatePopulation();
        int rnd = new Random().nextInt(POPULATION_SIZE);
        globalBest = population[rnd];

        for(int i = 0; i < ITERATIONS; i++) {
            // Update particles. Find new global best
        }

        return globalBest;
    }

    private void generatePopulation() {
        for(int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = (PT) new Object();
        }
    }

    /**
     * @return Factor in range [0, 1). It is simplest way to get close to range [0, 1]
     */
    private double getRandomFactor() {
        return new Random().nextDouble();
    }

}
