import java.util.Random;

/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class PSO<PT extends IParticle> {

    private final int POPULATION_SIZE = 16;
    private final int ITERATIONS = 10000;

    private PT[] population;

    private PT globalBest;
    private double bestFitness;

    PSO() {
        population = (PT[])new Object[POPULATION_SIZE];
    }

    /**
     * @return Best particle
     */
    public PT execute() {
        generatePopulation();

        int rnd = new Random().nextInt(POPULATION_SIZE);
        globalBest = population[rnd];
        bestFitness = globalBest.getFitness();

        for(int i = 0; i < ITERATIONS; i++) {
            int newBestIndex = -1;

            for(int j = 0; j < POPULATION_SIZE; j++) {
                population[j].updateVelocity(globalBest);
                population[j].updateParticle();

                if(population[i].getFitness() >= bestFitness) {
                    newBestIndex = i;

                    // Small hack to search now only values that are larger than that
                    bestFitness = population[i].getFitness();
                }
            }

            if(newBestIndex >= 0) {
                globalBest = (PT) population[i].clone();
                bestFitness = globalBest.getFitness();
            }
        }

        return globalBest;
    }

    private void generatePopulation() {
        for(int i = 0; i < POPULATION_SIZE; i++) {
            population[i] = (PT)new Object();
        }
    }

}
