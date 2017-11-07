/**
 * AI_music_generator
 * Created by Sergey on 2017-11-03
 */

// TODO: fill the class
public class Particle2 implements IParticle {


    Particle2() {
        // TODO: fill the method
    }

    @Override
    public IParticle[] generatePopulation(int size) {
        Particle2[] collection = new Particle2[size];
        for(int i = 0; i < size; i++) {
            collection[i] = new Particle2();
        }

        return collection;
    }

    @Override
    public double calculateFitness() {
        // TODO: fill the method
        return 0;
    }

    @Override
    public double getFitness() {
        // TODO: fill the method
        return 0;
    }

    @Override
    public void updateVelocity(IParticle gBest) {
        // TODO: fill the method
    }

    @Override
    public void updateParticle() {
        // TODO: fill the method
    }

    @Override
    public IParticle cloneParticle() {
        return null;
    }

}
