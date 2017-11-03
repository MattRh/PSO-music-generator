import java.io.IOException;

/**
 * AI_music_generator
 * Created by Sergey on 2017-10-25
 */
public class Main {


    public static void main(String[] args) throws IOException {
        MidiWrapper midiWrapper = new MidiWrapper();

        PSO pso1 = new PSO();
        IParticle[] population1 = new Particle1().generatePopulation(PSO.POPULATION_SIZE);

        System.out.println("Start PSO#1");
        long startTime = System.nanoTime();
        Particle1 chordSequence = (Particle1)pso1.execute(population1);
        double runTime = (double) (System.nanoTime() - startTime);
        System.out.println("End PSO#1. Run for " + runTime / (1000 * 1000 * 1000) + "s");

        // TODO: make generation of melody
        /*PSO pso2 = new PSO<Particle2>();
        Particle2 melodySequence = (Particle2) pso2.execute();*/

        System.out.println("Setting MidiWrapper");
        midiWrapper.setChords(chordSequence.getChords());
        //midiWrapper.setMelody(melodySequence.getNotes());

        for(MyChord c : chordSequence.getChords()) {
            System.out.println(c.toString());
        }

        System.out.println("Saving everything");
        midiWrapper.doEverything();
    }

}
