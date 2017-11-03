import java.util.ArrayList;
import java.util.Random;

/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class PSO<PT extends IParticle> {

    private final int POPULATION_SIZE = 16;
    private final int ITERATIONS = 10000;

    private ArrayList<PT> population;

    private PT globalBest;
    private double bestFitness;

    PSO() {
        population = new ArrayList<>(POPULATION_SIZE);
    }

    /**
     * @return Best particle
     */
    public PT execute() {
        generatePopulation();

        int rnd = new Random().nextInt(POPULATION_SIZE);
        globalBest = population.get(rnd);
        bestFitness = globalBest.getFitness();

        for(int i = 0; i < ITERATIONS; i++) {
            int newBestIndex = -1;

            for(int j = 0; j < POPULATION_SIZE; j++) {
                population.get(j).updateVelocity(globalBest);
                population.get(j).updateParticle();

                if(population.get(i).getFitness() >= bestFitness) {
                    newBestIndex = i;

                    // Small hack to search now only values that are larger than that
                    bestFitness = population.get(i).getFitness();
                }
            }

            if(newBestIndex >= 0) {
                globalBest = (PT) population.get(i).clone();
                bestFitness = globalBest.getFitness();
            }
        }

        return globalBest;
    }

    private void generatePopulation() {
        for(int i = 0; i < POPULATION_SIZE; i++) {
            population.set(i, (PT)new Object());
        }
    }

}
