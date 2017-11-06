/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class PSO {

    public static final int POPULATION_SIZE = 20;
    private final int ITERATIONS = 5000;

    private IParticle globalBest;
    private double bestFitness;

    PSO() {

    }

    /**
     * @return Best particle
     */
    public IParticle execute(IParticle[] population) {
        int newBestIndex = 0;
        for(int i = 1; i < POPULATION_SIZE; i++) {
            if(population[i].getFitness() > population[newBestIndex].getFitness()) {
                newBestIndex = i;
            }
        }
        globalBest = population[newBestIndex].cloneParticle();
        bestFitness = globalBest.getFitness();

        for(int i = 0; i < ITERATIONS; i++) {
            newBestIndex = -1;

            for(int j = 0; j < POPULATION_SIZE; j++) {
                IParticle curParticle = population[j];

                curParticle.updateVelocity(globalBest);
                curParticle.updateParticle(globalBest);

                if(curParticle.getFitness() > bestFitness) {
                    newBestIndex = j;

                    // Small hack to search now only values that are larger than that
                    bestFitness = curParticle.getFitness();
                }

                //System.out.println(i + " () " + population[j]);
            }

            if(newBestIndex >= 0) {
                globalBest = population[newBestIndex].cloneParticle();
                // bestFitness is already updated
            }

            System.out.println(i + " gbest " + globalBest.toString());

            if(i % (ITERATIONS / 10) == 0) {
                System.out.println("- PSO step#" + i + " completed");
            }
        }

        return globalBest;
    }

}

