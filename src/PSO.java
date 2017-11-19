/**
 * PSO executor
 * <p>
 * Created by Sergey on 2017-10-27
 */
public class PSO {

    /**
     * General PSO parameters
     */
    public static int POPULATION_SIZE = 64;
    public static int ITERATIONS = 100000;

    /**
     * Precision of calculations. When global best fitness < PRECISION, then calculations stops
     */
    private final double PRECISION = 0.4;

    /**
     * Current population, that we are working on
     */
    private IParticle[] population;

    /**
     * Storage for best particle data
     */
    private IParticle globalBest;
    private double bestFitness;

    PSO() {}

    /**
     * @param popSize   POPULATION_SIZE
     * @param iterCount ITERATIONS
     */
    PSO(int popSize, int iterCount) {
        POPULATION_SIZE = popSize;
        ITERATIONS = iterCount;
    }

    /**
     * Executes PSO on given population
     *
     * @param population Population of particles to work on
     * @return Best particle after iterations exceeds and answer id found
     */
    public IParticle execute(IParticle[] population) throws Exception {
        this.bestFitness = Double.MAX_VALUE;
        this.population = population;

        defineBestParticle();

        for(int i = 0; i < ITERATIONS; i++) {
            // Calculate fitness for each particle
            for(int j = 0; j < POPULATION_SIZE; j++) {
                IParticle curParticle = population[j];

                curParticle.updateVelocity(globalBest);
                curParticle.updateParticle();
            }

            // Find best one
            defineBestParticle();

            // Show progress
            if(i % (ITERATIONS / 10) == 0) {
                System.out.println("- PSO step#" + i + "/" + ITERATIONS + " completed: " + bestFitness);
                //System.out.println(i + " gbest " + globalBest.toString());
            }

            // If solution is found then stop
            if(bestFitness <= PRECISION) {
                System.out.println("!!!Solution found!!! (step: " + i + ")");
                break;
            }
        }

        return globalBest;
    }

    /**
     * Finds current best particle or leaves old best particle unchanged
     */
    private void defineBestParticle() {
        int newBestIndex = -1;

        for(int i = 1; i < POPULATION_SIZE; i++) {
            if(population[i].getFitness() < bestFitness) {
                newBestIndex = i;

                // Small hack to search now only values that are larger than that
                bestFitness = population[i].getFitness();
            }
        }

        if(newBestIndex >= 0) {
            // bestFitness is already updated, so we don't need to set it again
            globalBest = population[newBestIndex].cloneParticle();
        }
    }

    /**
     * @return Best fitness value
     */
    public double getBestFitness() {
        return bestFitness;
    }

}

