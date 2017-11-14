import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 * Particle for accompaniment
 * <p>
 * Created by Sergey on 2017-10-27
 */
public class Particle1 implements IParticle {

    public final int CHORDS_NUMBER = 16;

    public final int MIN_TONE = 48; // Midi note can't be lower than that
    public final int MAX_TONE = 84; // Midi note can't be higher than that

    private final double MAX_START_ABS_VELOCITY = 3.5;
    private final double MAX_ABS_VELOCITY = 10;

    public static double INERTIA_COMPONENT = 0.825; // Tendency to save current velocity
    public static double COGNITIVE_COMPONENT = 2.3; // Tendency to return to local best
    public static double SOCIAL_COMPONENT = 0.4; // Tendency to return to global best

    private Tonality tone;

    private MyChord[] chords = new MyChord[CHORDS_NUMBER];
    private MyVector3[] velocities = new MyVector3[CHORDS_NUMBER];
    private double fitness;

    private MyChord[] bestChords;
    private double bestFitness;

    public Particle1() {}

    public Particle1(Tonality tone) throws Exception {
        this.tone = tone;

        regenerate();
    }

    public Particle1(Tonality tone, MyChord[] chords, MyVector3[] velocities, double fitness) {
        this.tone = tone;

        this.chords = chords;
        bestChords = chords;

        this.velocities = velocities;

        this.fitness = fitness;
        bestFitness = fitness;
    }

    public void regenerate() throws Exception {
        double vecDeltaAbs = MAX_START_ABS_VELOCITY;

        for(int i = 0; i < CHORDS_NUMBER; i++) {
            chords[i] = new MyChord(MIN_TONE, MAX_TONE);
            velocities[i] = new MyVector3(-vecDeltaAbs, vecDeltaAbs);
        }

        calculateFitness();

        bestChords = chords.clone();
        bestFitness = fitness;
    }

    public IParticle[] generatePopulation(int size, Tonality tone) throws Exception {
        this.tone = tone;
        Particle1[] collection = new Particle1[size];
        for(int i = 0; i < size; i++) {
            collection[i] = new Particle1(tone);
        }

        return collection;
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    @Override
    public void updateVelocity(IParticle gBest) {
        Particle1 gBestParticle = (Particle1)gBest.cloneParticle();

        for(int i = 0; i < CHORDS_NUMBER; i++) {
            MyVector3 component1 = velocities[i].mul(INERTIA_COMPONENT);
            MyVector3 component2 = bestChords[i].subMul(chords[i], COGNITIVE_COMPONENT * Randomizer.getRandomFactor());
            MyVector3 component3 = gBestParticle.getChord(i).subMul(chords[i], SOCIAL_COMPONENT * Randomizer.getRandomFactor());

            velocities[i] = component1.add(component2, component3);
            velocities[i].applyLimit(MAX_ABS_VELOCITY);
        }
    }

    @Override
    public void updateParticle() throws Exception {
        for(int i = 0; i < CHORDS_NUMBER; i++) {
            chords[i] = chords[i].sumWith(velocities[i]);
        }

        calculateFitness();

        if(fitness < bestFitness) {
            bestChords = chords.clone();
            bestFitness = fitness;
        }
    }

    @Override
    public IParticle cloneParticle() {
        return new Particle1(tone, chords.clone(), velocities.clone(), fitness);
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

    @Override
    public double calculateFitness() throws Exception {
        fitness = 0;

        // General fitness calculation for each chord
        for(int i = 0; i < CHORDS_NUMBER; i++) {
            MyChord chord = chords[i];

            // If chord is out of range
            if(chord.n1 < MIN_TONE || chord.n1 > MAX_TONE) {
                fitness += pow(getReturnFactor(chord) * 2, 2) * 4;
            }

            fitness += tone.checkChord(chord);
        }

        // Distance between 2 chords should be <= 12
        for(int i = 0; i < CHORDS_NUMBER - 1; i++) {
            MyChord chord = chords[i];
            MyChord nextChord = chords[i + 1];

            double delta = abs(((int)chord.n1) - ((int)nextChord.n1));

            if(delta > 12) {
                fitness += pow((abs(chord.n1 - nextChord.n1) - 12), 2) * 2;
            }
        }

        // There should not be more than 4 equal chords in a row
        for(int i = 0; i < CHORDS_NUMBER - 3; i++) {
            boolean chordsEqual = true;
            MyChord curChord = chords[i];

            for(int j = 1; j < 3; j++) {
                MyChord compareChord = chords[i + j];

                if(!curChord.equals(compareChord)) {
                    chordsEqual = false;
                    break;
                }
            }

            if(chordsEqual) {
                fitness += 200;
            }
        }

        if(fitness < 0) {
            throw new Exception("Fitness is below 0: " + fitness);
        }

        return fitness;
    }

    public double getReturnFactor(MyChord c) {
        double returnFactor = 0;
        returnFactor += getLessOffset(c.n1, MIN_TONE);
        returnFactor += getGreaterOffset(c.n1, MAX_TONE);

        return returnFactor * 2;
    }

    public double getLessOffset(double a1, double a2) {
        if(a1 < a2) {
            return abs(a1 - a2);
        }

        return 0;
    }

    public double getGreaterOffset(double a1, double a2) {
        if(a1 > a2) {
            return abs(a1 - a2);
        }

        return 0;
    }

}
