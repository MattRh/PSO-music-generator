import java.util.ArrayList;
import java.util.Arrays;

/**
 * AI_music_generator
 * Created by Sergey on 2017-10-25
 */
public class Main {


    public static void main(String[] args) throws Exception {
        //findBestFactors();

        MidiWrapper midiWrapper = new MidiWrapper();

        Tonality tonality = new Tonality();
        System.out.println(tonality.toString());

        PSO pso1 = new PSO();
        IParticle[] population1 = new Particle1().generatePopulation(PSO.POPULATION_SIZE, tonality);

        System.out.println("Start PSO#1");
        long startTime = System.nanoTime();
        Particle1 chordSequence = (Particle1)pso1.execute(population1);
        double runTime = (double)(System.nanoTime() - startTime);
        System.out.println("End PSO#1. Run for " + runTime / (1000 * 1000 * 1000) + "s");

        // TODO: make generation of melody
//        PSO pso2 = new PSO<Particle2>();
//        Particle2 melodySequence = (Particle2) pso2.execute();

        System.out.println("Setting MidiWrapper");
        midiWrapper.setChords(chordSequence.getChords());
        //midiWrapper.setMelody(melodySequence.getNotes());

        System.out.println(chordSequence.toString());

        midiWrapper.composePattern();
        midiWrapper.play();

        //System.out.println("Saving everything");
        //midiWrapper.doEverything();
    }

    private static void findBestFactors() throws Exception {
        ArrayList<Double[]> results = new ArrayList<>(1000); // lf0, lf1, lf2, avgBestFitness

        double fitnessSum;
        double step = 0.2;
        int trials = 14;
        int counter = 0;

        for(double i = 0.6; i <= 1; i += step / 2, counter++) {
            for(double j = 0.2; j < 2.6; j += step, counter++) {
                for(double k = 0.2; k < 2.6; k += step, counter++) {
                    fitnessSum = 0;
                    for(int l = 0; l < trials; l++) {
                        Tonality tonality = new Tonality();
                        PSO pso = new PSO();

                        Particle1.INERTIA_COMPONENT = i;
                        Particle1.COGNITIVE_COMPONENT = j;
                        Particle1.SOCIAL_COMPONENT = k;

                        IParticle[] population1 = new Particle1().generatePopulation(PSO.POPULATION_SIZE, tonality);
                        pso.execute(population1);

                        fitnessSum += pso.getBestFitness();
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
