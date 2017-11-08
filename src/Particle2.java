/**
 * AI_music_generator
 * Created by Sergey on 2017-11-03
 */

public class Particle2 implements IParticle {

    public final int NOTES_NUMBER = 16;

    private final int BORDER_TONE = 72;  // It's better for note to be lower than that
    public final int MIN_TONE = BORDER_TONE;//48; // Midi note can't be lower than that
    public final int MAX_TONE = 96; // Midi note can't be higher than that

    private final int MAX_START_ABS_VELOCITY = 3;

    public static double INERTIA_COMPONENT = 0.825; // Tendency to save current velocity
    public static double COGNITIVE_COMPONENT = 2.32; // Tendency to return to local best
    public static double SOCIAL_COMPONENT = 0.735; // Tendency to return to global best

    private Tonality tone;
    private MyChord[] chords;

    private MyNote[] notes = new MyNote[NOTES_NUMBER];
    private MyVector1[] velocities = new MyVector1[NOTES_NUMBER];
    private double fitness;

    private MyNote[] bestNotes;
    private double bestFitness;

    public Particle2() {

    }

    public Particle2(Tonality tone, MyChord[] chords) throws Exception {
        this.tone = tone;
        this.chords = chords;

        regenerate();
    }

    public void regenerate() throws Exception {
        int vecDeltaAbs = MAX_START_ABS_VELOCITY;

        for(int i = 0; i < NOTES_NUMBER; i++) {
            notes[i] = new MyNote(MIN_TONE, MAX_TONE);
            velocities[i] = new MyVector1(-vecDeltaAbs, vecDeltaAbs);
        }

        calculateFitness();

        bestNotes = notes.clone();
        bestFitness = fitness;
    }

    public Particle2(Tonality tone, MyNote[] notes, MyVector1[] velocities, double fitness, MyChord[] chords) {
        this.tone = tone;

        this.notes = notes;
        bestNotes = notes;

        this.velocities = velocities;

        this.fitness = fitness;
        bestFitness = fitness;

        this.chords = chords;
    }

    public IParticle[] generatePopulation(int size, Tonality tone, MyChord[] chords) throws Exception {
        Particle2[] collection = new Particle2[size];
        for(int i = 0; i < size; i++) {
            collection[i] = new Particle2(tone, chords);
        }

        return collection;
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public MyNote[] getNotes() {
        return notes;
    }

    public MyNote getNote(int index) {
        return notes[index];
    }

    @Override
    public void updateVelocity(IParticle gBest) {
        Particle2 gBestParticle = (Particle2)gBest.cloneParticle();

        for(int i = 0; i < NOTES_NUMBER; i++) {
            MyVector1 component1 = velocities[i].mul(INERTIA_COMPONENT);
            MyVector1 component2 = bestNotes[i].subMul(notes[i], COGNITIVE_COMPONENT * Randomizer.getRandomFactor());
            MyVector1 component3 = gBestParticle.getNote(i).subMul(notes[i], SOCIAL_COMPONENT * Randomizer.getRandomFactor());

            velocities[i] = component1.add(component2, component3);
        }
    }

    @Override
    public void updateParticle() throws Exception {
        for(int i = 0; i < NOTES_NUMBER; i++) {
            notes[i] = notes[i].sumWith(velocities[i]);
        }

        calculateFitness();

        if(fitness < bestFitness) {
            bestNotes = notes.clone();
            bestFitness = fitness;
        }
    }

    @Override
    public IParticle cloneParticle() {
        return new Particle2(tone, notes.clone(), velocities.clone(), fitness, chords);
    }

    @Override
    public String toString() {
        String res = Double.toString(fitness) + "\n";

        for(int i = 0; i < NOTES_NUMBER; i++) {
            res += notes[i].toString() + " > " + velocities[i].toString() + "\n";
        }

        return res;
    }

    @Override
    public double calculateFitness() {
        // TODO: fill the method

        return 0;
    }

}
