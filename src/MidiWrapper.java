import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * AI_music_generator
 * Created by Sergey on 2017-10-30
 */
public class MidiWrapper {

    private final int TEMPO = 120;
    private final char CHORDS_DURATION = 'q';
    private final char NOTE_DURATION = 'h';
    private final int BAR_LENGTH = 4;

    private final String midiOutput = "output.mid";
    private final String textOutput = "output.txt";

    private MyChord[] chords = new MyChord[0];
    private MyNote[] melody = new MyNote[0];

    private Pattern midiPattern = new Pattern();

    MidiWrapper() {}

    MidiWrapper(MyChord[] chords) {
        this();
        setChords(chords);
    }

    MidiWrapper(MyNote[] melody) {
        this();
        setMelody(melody);
    }

    MidiWrapper(MyChord[] chords, MyNote[] melody) {
        this();
        setParams(chords, melody);
    }

    /**
     * Firstly plays music and then saves it to the midi file
     * @throws IOException
     */
    public void doEverything() throws IOException {
        //play();

        //saveMidi();
        saveText();
    }

    public void setParams(MyChord[] chords, MyNote[] melody) {
        setChords(chords);
        setMelody(melody);
    }

    public void setChords(MyChord[] chords) {
        this.chords = chords;
    }

    public void setMelody(MyNote[] melody) {
        this.melody = melody;
    }

    private void composePattern() {
        Pattern s1 = new Pattern(chordsToString()).setVoice(0);
        Pattern s2 = new Pattern(melodyToString()).setVoice(1);

        s1.add(s2).setTempo(TEMPO);

        midiPattern = s1;
    }

    public void play() {
        Player player = new Player();
        player.play(midiPattern);
    }

    public void saveMidi() throws IOException {
        MidiFileManager.savePatternToMidi(midiPattern, new File(midiOutput));
    }

    public void saveText() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(textOutput));
        writer.write(midiPattern.toString());
        writer.close();
    }

    @Override
    public String toString() {
        return midiPattern.toString();
    }

    private String chordsToString() {
        String res = "";

        int i = 0;
        int l = chords.length;

        for(MyChord chord : chords) {
            res += chord.n1 + CHORDS_DURATION + "+" + chord.n2 + CHORDS_DURATION + "+" + chord.n3 + CHORDS_DURATION + " ";
            if(i % BAR_LENGTH == 0 && i != 0 && i != l) {
                res += "| ";
            }
            i++;
        }

        return res;
    }

    private String melodyToString() {
        String res = "";

        int i = 0;
        int l = melody.length;

        for(MyNote note : melody) {
            res += note.number + NOTE_DURATION + " ";
            if(i % BAR_LENGTH / 2 == 0 && i != 0 && i != l) {
                res += "| ";
            }
            i++;
        }

        return res;
    }

}
