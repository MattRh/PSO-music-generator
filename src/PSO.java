import java.util.Random;

/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class PSO {

    public static final int POPULATION_SIZE = 1;
    private final int ITERATIONS = 10;

    private IParticle globalBest;
    private double bestFitness;

    PSO() {

    }

    /**
     * @return Best particle
     */
    public IParticle execute(IParticle[] population) {
        int rnd = new Random().nextInt(POPULATION_SIZE);
        globalBest = population[rnd];
        bestFitness = globalBest.getFitness();

        for(int i = 0; i < ITERATIONS; i++) {
            int newBestIndex = -1;

            for(int j = 0; j < POPULATION_SIZE; j++) {
                population[j].updateVelocity(globalBest);
                population[j].updateParticle();

                if(population[j].getFitness() > bestFitness) {
                    newBestIndex = j;

                    // Small hack to search now only values that are larger than that
                    bestFitness = population[j].getFitness();
                }

                System.out.println(i + " () " + population[j]);
            }

            if(newBestIndex >= 0) {
                globalBest = population[newBestIndex].cloneParticle();
                // bestFitness is already updated
            }

            if(i % (ITERATIONS / 10) == 0) {
                System.out.println("- PSO step#" + i + " completed");
            }
        }

        return globalBest;
    }

}
