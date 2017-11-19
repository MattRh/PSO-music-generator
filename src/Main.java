import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.pow;

/**
 * "Simple" music generation using PSO
 * <p>
 * Created by Sergey on 2017-10-25
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // Generate initial tonality
        Tonality tonality = new Tonality(9, false);
        System.out.println("Tonality: " + tonality.toString() + "\n");

        // Generates accompaniment
        Particle1 chordSequence = findAccompaniment(tonality);
        System.out.println();

        // Generates melody for pregenerated accompaniment
        Particle2 melodySequence = findMelody(tonality, chordSequence);
        System.out.println();

        // Process notes and chords into midi
        midiFunc(chordSequence, melodySequence);
    }

    public static Particle1 findAccompaniment(Tonality tone) throws Exception {
        PSO pso1 = new PSO();
        IParticle[] population1 = new Particle1().generatePopulation(PSO.POPULATION_SIZE, tone);

        System.out.println("Start PSO#1");
        long startTime = System.nanoTime();

        Particle1 chordSequence = (Particle1)pso1.execute(population1);

        double runTime = (double)(System.nanoTime() - startTime);
        System.out.println("End PSO#1. Run for " + runTime / pow(10, 9) + "s");

        return chordSequence;
    }

    public static Particle2 findMelody(Tonality tone, Particle1 chords) throws Exception {
        PSO pso2 = new PSO();
        IParticle[] population2 = new Particle2().generatePopulation(PSO.POPULATION_SIZE, tone, chords.getChords());

        System.out.println("Start PSO#2");
        long startTime = System.nanoTime();

        Particle2 noteSequence = (Particle2)pso2.execute(population2);

        double runTime = (double)(System.nanoTime() - startTime);
        System.out.println("End PSO#2. Run for " + runTime / pow(10, 9) + "s");

        return noteSequence;
    }

    public static void midiFunc(Particle1 chords, Particle2 notes) throws IOException {
        System.out.println("Setting MidiWrapper");
        MidiWrapper midiWrapper = new MidiWrapper(chords.getChords(), notes.getNotes());

        System.out.println(midiWrapper.getInstrument(0) + " + " + midiWrapper.getInstrument(1));
        System.out.println(midiWrapper.toString());

        midiWrapper.saveMidi();
        midiWrapper.play();

        /*System.out.println("Saving everything");
        midiWrapper.doEverything();*/
    }

    /**
     * Helps to find best factors for PSO
     *
     * @throws Exception
     */
    private static void findBestFactors() throws Exception {
        ArrayList<Double[]> results = new ArrayList<>(1000); // lf0, lf1, lf2, avgBestFitness

        double fitnessSum;
        double step = 0.2;
        int trials = 8;
        int counter = 0;

        for(double i = 0.0; i <= 1; i += step, counter++) {
            for(double j = 0.2; j < 2.6; j += step, counter++) {
                for(double k = 0.2; k < 2.6; k += step, counter++) {
                    fitnessSum = 0;
                    for(int l = 0; l < trials; l++) {
                        Tonality tonality = new Tonality();
                        PSO pso = new PSO(16, 10000);

                        Particle1.INERTIA_COMPONENT = i;
                        Particle1.COGNITIVE_COMPONENT = j;
                        Particle1.SOCIAL_COMPONENT = k;

                        IParticle[] population = new Particle2().generatePopulation(PSO.POPULATION_SIZE, tonality, null);
                        pso.execute(population);

                        fitnessSum += pso.getBestFitness();
                        //System.out.println(pso.getBestFitness());
                    }

                    results.add(new Double[] {
                            i, j, k, fitnessSum / trials
                    });

                    System.out.println(
                            String.format("%4d", counter) + ": " +
                                    String.format("%.1f", i) + " " +
                                    String.format("%.1f", j) + " " +
                                    String.format("%.1f", k) + " " +
                                    String.format("%.1f", fitnessSum / trials)
                    );
                }
            }
        }

        int minIndex = 0;
        counter = 0;
        double minFitness = results.get(0)[3];
        for(Double[] arr : results) {
            if(arr[3] < minFitness) {
                minIndex = counter;
                minFitness = arr[3];
            }

            counter++;
        }

        System.out.println(Arrays.toString(results.get(minIndex)));
    }

}
