/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class Particle1 implements IParticle {

    public final int CHORDS_NUMBER = 16;

    private final int MIN_TONE = 48; // Midi note can't be lower than that
    private final int BORDER_TONE = 72;  // It's better for note to be lower than that
    private final int MAX_TONE = 96; // Midi note can't be higher than that

    private final int MAX_START_ABS_VELOCITY = 3;

    private final double INERTIA_COMPONENT = 0.6; // Tendency to save current velocity
    private final double COGNITIVE_COMPONENT = 1.2; // Tendency to return to local best
    private final double SOCIAL_COMPONENT = 0.2; // Tendency to return to global best

    private MyChord[] chords = new MyChord[CHORDS_NUMBER];
    private MyVector3[] velocities = new MyVector3[CHORDS_NUMBER];
    private double fitness;

    private MyChord[] bestChords;
    private double bestFitness;

    public Particle1() {
        regenerate();
    }

    public Particle1(MyChord[] chords, MyVector3[] velocities, double fitness) {
        this.chords = chords;
        bestChords = chords;

        this.velocities = velocities;

        this.fitness = fitness;
        bestFitness = fitness;
    }

    public void regenerate() {
        int vecDeltaAbs = MAX_START_ABS_VELOCITY;

        for(int i = 0; i < CHORDS_NUMBER; i++) {
            chords[i] = new MyChord(MIN_TONE, MAX_TONE);
            velocities[i] = new MyVector3(-vecDeltaAbs, vecDeltaAbs);
        }

        calculateFitness();

        bestChords = chords.clone();
        bestFitness = fitness;
    }

    @Override
    public IParticle[] generatePopulation(int size) {
        Particle1[] collection = new Particle1[size];
        for(int i = 0; i < size; i++) {
            collection[i] = new Particle1();
        }

        return collection;
    }

    @Override
    public double calculateFitness() {
        fitness = 0;
        for(int i = 0; i < CHORDS_NUMBER; i++) {
            MyChord c = chords[i];

            int returnFactor = 0;
            returnFactor += magic1(c.n1, MIN_TONE);
            returnFactor += magic2(c.n1, MAX_TONE);
            returnFactor += magic1(c.n2, MIN_TONE);
            returnFactor += magic2(c.n2, MAX_TONE);
            returnFactor += magic1(c.n3, MIN_TONE);
            returnFactor += magic2(c.n3, MAX_TONE);

            if(returnFactor > 0) {
                fitness -= Math.pow(returnFactor, 10);
            } else {
                fitness += 12 - (c.n1 % 12);
                fitness += 12 - ((c.n2 - 3) % 12);
                fitness += 12 - ((c.n3 - 7) % 12);
            }
        }
        // TODO: fitness calculation

        return fitness;
    }

    public int magic1(int a1, int a2) {
        if(a1 <= a2) {
            return 1 + a2 - a1;
        }

        return 0;
    }

    public int magic2(int a1, int a2) {
        if(a1 >= a2) {
            return 1 + a1 - a2;
        }

        return 0;
    }

    public void setVelocities(MyVector3[] velocities) {
        this.velocities = velocities;
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    @Override
    public void updateVelocity(IParticle gBest) {
        Particle1 gBestParticle = (Particle1)gBest;

        for(int i = 0; i < CHORDS_NUMBER; i++) {
            MyVector3 component1 = velocities[i].mul(INERTIA_COMPONENT);
            MyVector3 component2 = bestChords[i].subMul(chords[i], COGNITIVE_COMPONENT * Randomizer.getRandomFactor());
            MyVector3 component3 = gBestParticle.getChord(i).subMul(chords[i], SOCIAL_COMPONENT * Randomizer.getRandomFactor());

            velocities[i] = component1.add(component2, component3);
        }
    }

    @Override
    public void updateParticle() {
        for(int i = 0; i < CHORDS_NUMBER; i++) {
            chords[i].sumWith(velocities[i]);
        }

        calculateFitness();

        if(fitness > bestFitness) {
            bestChords = chords.clone();
            bestFitness = fitness;
        }
    }

    @Override
    public IParticle cloneParticle() {
        return new Particle1(chords.clone(), velocities.clone(), fitness);
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public MyChord[] getChords() {
        return chords;
    }

    public MyChord getChord(int ind) {
        return chords[ind];
    }

    @Override
    public String toString() {
        String res = Double.toString(fitness) + "\n";

        for(int i = 0; i < CHORDS_NUMBER; i++) {
            res += chords[i].toString() + " > " + velocities[i].toString() + "\n";
        }

        return res;
    }
}
