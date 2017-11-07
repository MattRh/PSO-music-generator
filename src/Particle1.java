import static java.lang.Math.*;

/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class Particle1 implements IParticle {

    public final int CHORDS_NUMBER = 16;

    public final int MIN_TONE = 48; // Midi note can't be lower than that
    private final int BORDER_TONE = 72;  // It's better for note to be lower than that
    public final int MAX_TONE = BORDER_TONE;//96; // Midi note can't be higher than that

    private final int MAX_START_ABS_VELOCITY = 5;

    private final double INERTIA_COMPONENT = 0.8; // Tendency to save current velocity
    private final double COGNITIVE_COMPONENT = 1.9; // Tendency to return to local best
    private final double SOCIAL_COMPONENT = 0.6; // Tendency to return to global best

    private Tonality tone;

    private MyChord[] chords = new MyChord[CHORDS_NUMBER];
    private MyVector3[] velocities = new MyVector3[CHORDS_NUMBER];
    private double fitness;

    private MyChord[] bestChords;
    private double bestFitness;

    public Particle1() {}

    public Particle1(Tonality tone) {
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
        Tonality generationTone = new Tonality();
        Particle1[] collection = new Particle1[size];
        for(int i = 0; i < size; i++) {
            collection[i] = new Particle1(generationTone);
        }

        return collection;
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
        Particle1 gBestParticle = (Particle1)gBest.cloneParticle();

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

    @Override
    public double calculateFitness() {
        fitness = 0;

        // General fitness calculation for each chord
        for(int i = 0; i < CHORDS_NUMBER; i++) {
            MyChord chord = chords[i];

            // If chord is out of range
            long returnFactor = getReturnFactor(chord);
            if(chord.n1 < MIN_TONE || chord.n1 > MAX_TONE) {
                fitness += pow(returnFactor, 2);
            }

            // Check for tonality
        }

        // Distance between 2 chords should be <= 12
        for(int i = 0; i < CHORDS_NUMBER - 1; i++) {
            MyChord chord = chords[i];
            MyChord nextChord = chords[i + 1];

            double delta = abs(floor(chord.n1) - floor(nextChord.n1));

            if(delta > 12) {
                fitness += pow(delta - 12, 2);
            }
        }

        // There should not be more than 4 equal chords in a row
        for(int i = 0; i < CHORDS_NUMBER - 4; i++) {
            boolean chordsEqual = true;
            MyChord curChord = chords[i];

            for(int j = 1; j < 4; j++) {
                MyChord compareChord = chords[i + j];

                if(!curChord.equals(compareChord)) {
                    chordsEqual = false;
                    break;
                }
            }

            if(chordsEqual) {
                fitness += 10;
            }
        }

        return fitness;
    }

    public int getReturnFactor(MyChord c) {
        int returnFactor = 0;
        returnFactor += getLessOffset(c.n1, MIN_TONE);
        returnFactor += getGreaterOffset(c.n1, MAX_TONE);

        return returnFactor * 2;
    }

    public int getLessOffset(int a1, int a2) {
        if(a1 < a2) {
            return abs(a1 - a2);
        }

        return 0;
    }

    public int getGreaterOffset(int a1, int a2) {
        if(a1 > a2) {
            return abs(a1 - a2);
        }

        return 0;
    }
}
