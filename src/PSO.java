/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class PSO {

    public static int POPULATION_SIZE = 64;
    private int ITERATIONS = 250000;

    private final double PRECISION = 0.4;

    private IParticle globalBest;
    private double bestFitness;

    PSO() {

    }

    PSO(int popSize, int iterCount) {
        POPULATION_SIZE = popSize;
        ITERATIONS = iterCount;
    }

    /**
     * @return Best particle
     */
    public IParticle execute(IParticle[] population) throws Exception {
        int newBestIndex = 0;
        for(int i = 1; i < POPULATION_SIZE; i++) {
            if(population[i].getFitness() < population[newBestIndex].getFitness()) {
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
                curParticle.updateParticle();

                if(curParticle.getFitness() < bestFitness) {
                    newBestIndex = j;

                    // Small hack to search now only values that are larger than that
                    bestFitness = curParticle.getFitness();
                }

                //System.out.println(i + " (" + j + ") " + population[j]);
            }

            if(newBestIndex >= 0) {
                globalBest = population[newBestIndex].cloneParticle();
                // bestFitness is already updated
            }

            //System.out.println(i + " gbest " + globalBest.toString());

            if(i % (ITERATIONS / 10) == 0) {
                System.out.println("- PSO step#" + i + "/" + ITERATIONS + " completed: " + bestFitness);
                //System.out.println(i + " gbest " + globalBest.toString());
            }

            if(bestFitness <= PRECISION) {
                System.out.println("!!!Solution found!!! (step: " + i + ")");
                break;
            }
        }

        /*for(IParticle p: population) {
            System.out.println(p.toString());
        }*/

        return globalBest;
    }

    public double getBestFitness() {
        return bestFitness;
    }
}

