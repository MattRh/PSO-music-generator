import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.io.File;
import java.io.IOException;

/**
 * AI_music_generator
 * Created by Sergey on 2017-10-25
 */
public class Main {


    public static void main(String[] args) throws IOException {
        Player player = new Player();
        /*Note n = new Note("93");
        Chord ch = ();
        System.out.println(ch.toNoteString());
        System.out.println(ch.toString());*/
        //player.play("V0 I[Piano] Eq Ch. | Eq Ch. | Dq Eq Dq Cq   V1 I[Flute] Rw | Rw | GmajQQQ CmajQ");

        Pattern s1 = new Pattern("60q+64q+67q 60q+64q+67q 64q+67q+71q 71q+74q+80q | 60q+64q+67q 60q+64q+67q 60q+64q+67q 60q+64q+67q").setVoice(0);
        Pattern s2 = new Pattern("72h+76h 72h+76h | 76h+80h 76h+80h").setVoice(1);

        s1.add(s2);

        //player.play(s1);

        MidiFileManager.savePatternToMidi(s1, new File("out.mid"));

        /*player.play(""+
                "V0 E5s D#5s | E5s D#5s E5s B4s D5s C5s " +
                "V1 Ri       | Riii                     ");*/
    }

    /*public void musicTranscription(int recordNo, int notesAmount, int tempo, float timeSignature, double[] frequencies, double[] frequencies2, double[] frequencies3, double[] durations) throws IOException, InvalidMidiDataException {
        String barEnd = "| ";
        String currentNoteSymbol;
        String currentNoteDuration;
        String musicString = "";
        musicPath = "";
        float barSize = 0;

        for(int i = 0; i < notesAmount; i++) {
            int noteMidiValue = pitchMatcher(frequencies[i] * 2);
            currentNoteSymbol = getToneString((byte)noteMidiValue);
            currentNoteDuration = getDurationString(durations[i]); // Здесь надо просто всем выдать одинаковую длительность, можно поставить символ q, обозначающий quarter note
            musicString += currentNoteSymbol + currentNoteDuration + " ";
            barSize += durations[i];

            if(barSize == timeSignature) {
                musicString += barEnd;
                barSize = 0;
                System.out.println(musicString);
            }
        }

        createMidiFile(recordNo, tempo);
    }*/

}
