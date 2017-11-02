/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class Particle1 {

    public final int CHORDS_NUMBER = 16;

    private final int MIN_TONE = 42; // Midi note can't be lower than that
    private final int BORDER_TONE = 76;  // It's better for note to be lower than that
    private final int MAX_TONE = 96; // Midi note can't be higher than that

    private MyChord[] chords = new MyChord[CHORDS_NUMBER];
    private MyVector3[] velocities = new MyVector3[CHORDS_NUMBER];
    private double fitness;

    private MyChord[] bestChords;
    private double bestFitness;

    private Particle1() {
        // TODO: generate random chords

        // TODO: generate random velocities
    }

    public double calculateFitness() {
        // TODO: fitness calculation

        return fitness;
    }

    public void setVelocities(MyVector3[] velocities) {
        this.velocities = velocities;
    }

    public double getFitness() {
        return fitness;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public MyChord[] getChords() {
        return chords;
    }

}
