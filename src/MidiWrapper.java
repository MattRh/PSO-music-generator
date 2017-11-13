import org.jfugue.midi.MidiDictionary;
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

    /**
     * Instruments Codes
     *
     * Piano
     * 0 PIANO or ACOUSTIC_GRAND
     * 1 BRIGHT_ACOUSTIC
     * 2 ELECTRIC_GRAND
     * 3 HONKEY_TONK
     * 4 ELECTRIC_PIANO or
     * ELECTRIC_PIANO1
     * 5 ELECTRIC_PIANO2
     * 6 HARPISCHORD
     * 7 CLAVINET
     * Chromatic Percussion
     * 8 CELESTA
     * 9 GLOCKENSPIEL
     * 10 MUSIC_BOX
     * 11 VIBRAPHONE
     * 12 MARIMBA
     * 13 XYLOPHONE
     * 14 TUBULAR_BELLS
     * 15 DULCIMER
     * Organ
     * 16 DRAWBAR_ORGAN
     * 17 PERCUSSIVE_ORGAN
     * 18 ROCK_ORGAN
     * 19 CHURCH_ORGAN
     * 20 REED_ORGAN
     * 21 ACCORIDAN
     * 22 HARMONICA
     * 23 TANGO_ACCORDIAN
     * Guitar
     * 24 GUITAR or NYLON_STRING_GUITAR
     * 25 STEEL_STRING_GUITAR
     * 26 ELECTRIC_JAZZ_GUITAR
     * 27 ELECTRIC_CLEAN_GUITAR
     * 28 ELECTRIC_MUTED_GUITAR
     * 29 OVERDRIVEN_GUITAR
     * 30 DISTORTION_GUITAR
     * 31 GUITAR_HARMONICS
     * Bass
     * 32 ACOUSTIC_BASS
     * 33 ELECTRIC_BASS_FINGER
     * 34 ELECTRIC_BASS_PICK
     * 35 FRETLESS_BASS
     * 36 SLAP_BASS_1
     * 37 SLAP_BASS_2
     * 38 SYNTH_BASS_1
     * 39 SYNTH_BASS_2
     * Strings
     * 40 VIOLIN
     * 41 VIOLA
     * 42 CELLO
     * 43 CONTRABASS
     * 44 TREMOLO_STRINGS
     * 45 PIZZICATO_STRINGS
     * 46 ORCHESTRAL_STRINGS
     * 47 TIMPANI
     * Ensemble
     * 48 STRING_ENSEMBLE_1
     * 49 STRING_ENSEMBLE_2
     * 50 SYNTH_STRINGS_1
     * 51 SYNTH_STRINGS_2
     * 52 CHOIR_AAHS
     * 53 VOICE_OOHS
     * 54 SYNTH_VOICE
     * 55 ORCHESTRA_HIT
     * Brass
     * 56 TRUMPET
     * 57 TROMBONE
     * 58 TUBA
     * 59 MUTED_TRUMPET
     * 60 FRENCH_HORN
     * 61 BRASS_SECTION
     * 62 SYNTHBRASS_1
     * 63 SYNTHBRASS_2
     */

    private final int SPEED_MODE = 0; // 0 - slow, 1 - fast

    private final int HALF_LENGTH = 'h';
    private final int QUARTER_LENGTH = 'q';
    private final int EIGHTH_LENGTH = 'i';

    private final int TEMPO = 120;
    private final int BAR_LENGTH = 4;
    private final char CHORDS_DURATION;
    private final char NOTE_DURATION;

    /**
     * Good combinations:
     * Echoes(102) + String_Ensemble_1(48)
     * Synth_Voice(54) + Brightness(100)
     * Electric_Bass_Finger(33) + String_Ensemble_1(48)
     * Glockenspiel(9) + Music_Box(10)
     * Bird_Tweet(123) + Flute(73)
     */

//    private final int ACCOMPANIMENT_INSTRUMENT_NUMBER = 10;
//    private final int MELODY_INSTRUMENT_NUMBER = 25;

    private final int ACCOMPANIMENT_INSTRUMENT_NUMBER = Randomizer.getRandomInt(0, 127);
    private final int MELODY_INSTRUMENT_NUMBER = Randomizer.getRandomInt(0, 127);

    private final String midiOutput = "output.mid";
    private final String textOutput = "output.txt";

    private MyChord[] chords = new MyChord[0];
    private MyNote[] melody = new MyNote[0];

    private Pattern midiPattern = new Pattern();

    MidiWrapper() {
        if(SPEED_MODE == 0) {
            CHORDS_DURATION = HALF_LENGTH;
            NOTE_DURATION = QUARTER_LENGTH;
        } else {
            CHORDS_DURATION = QUARTER_LENGTH;
            NOTE_DURATION = EIGHTH_LENGTH;
        }
    }

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
     *
     * @throws IOException
     */
    public void doEverything() throws IOException {
        composePattern();

        saveText();
        saveMidi();

        play();
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

    public void composePattern() {
        //MidiDictionary.INSTRUMENT_BYTE_TO_STRING.get(ACCOMPANIMENT_INSTRUMENT_NUMBER);

        Pattern s1 = new Pattern(melodyToString()).setVoice(0).setInstrument(MELODY_INSTRUMENT_NUMBER);
        Pattern s2 = new Pattern(chordsToString()).setVoice(1).setInstrument(ACCOMPANIMENT_INSTRUMENT_NUMBER);

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

    public String[] getInstruments() {
        return new String[] {
                MidiDictionary.INSTRUMENT_BYTE_TO_STRING.get((byte)MELODY_INSTRUMENT_NUMBER) + "(" + MELODY_INSTRUMENT_NUMBER + ")",
                MidiDictionary.INSTRUMENT_BYTE_TO_STRING.get((byte)ACCOMPANIMENT_INSTRUMENT_NUMBER) + "(" + ACCOMPANIMENT_INSTRUMENT_NUMBER + ")"
        };
    }

    public String getInstrument(int ind) {
        return getInstruments()[ind];
    }

    @Override
    public String toString() {
        return midiPattern.toString();
    }

    private String chordsToString() {
        StringBuilder res = new StringBuilder();

        int i = 0;
        int l = chords.length;

        for(MyChord chord : chords) {
            res.append(Math.round(chord.n1))
                    .append(CHORDS_DURATION).append("+")
                    .append(Math.round(chord.n2))
                    .append(CHORDS_DURATION).append("+")
                    .append(Math.round(chord.n3))
                    .append(CHORDS_DURATION).append(" ");

            if((i + 1) % BAR_LENGTH == 0 && i != 0 && i != l) {
                res.append("| ");
            }
            i++;
        }

        return res.toString();
    }

    private String melodyToString() {
        StringBuilder res = new StringBuilder();

        int i = 0;
        int l = melody.length;

        for(MyNote note : melody) {
            res.append(Math.round(note.number)).append(NOTE_DURATION).append(" ");

            if((i + 1) % (BAR_LENGTH * 2) == 0 && i != 0 && i != l) {
                res.append("| ");
            }
            i++;
        }

        return res.toString();
    }

}
