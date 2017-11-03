import java.io.IOException;

/**
 * AI_music_generator
 * Created by Sergey on 2017-10-25
 */
public class Main {


    public static void main(String[] args) throws IOException {
        MidiWrapper midiWrapper = new MidiWrapper();

        PSO pso1 = new PSO<Particle1>();
        Particle1 chordSequence = (Particle1)pso1.execute();

        // TODO: make generation of melody
        /*PSO pso2 = new PSO<Particle2>();
        Particle2 melodySequence = (Particle2) pso2.execute();*/

        midiWrapper.setChords(chordSequence.getChords());
        //midiWrapper.setMelody(melodySequence.getNotes());

        midiWrapper.doEverything();
    }

}
