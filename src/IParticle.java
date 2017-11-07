/**
 * AI_music_generator
 * Created by Sergey on 2017-11-03
 */
public interface IParticle {

    public IParticle[] generatePopulation(int size);

    /**
     * Calculates and saves fitness
     * Where 0 is the best fitness possible and as Math.abs(fitness) rises particle become worse
     *
     * @return New fitness
     */
    public double calculateFitness();

    /**
     * @return Current fitness
     */
    public double getFitness();

    /**
     * Updates particle's velocity based on global best particle
     *
     * @param gBest Global best
     */
    public void updateVelocity(IParticle gBest);

    /**
     * Calculates new particle state based on current velocity vector
     */
    public void updateParticle();

    /**
     * Clones current particle
     * @return Exact clone of current particle
     */
    public IParticle cloneParticle();

}
