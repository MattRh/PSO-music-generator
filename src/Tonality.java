import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.round;

/**
 * Some tonality. Generates randomly, Allows to check chords and notes
 * <p>
 * Created by Sergey on 2017-11-06
 */
public class Tonality {

    /**
     * General tonality data
     */
    private final boolean isMajor;
    private final int tonic;

    // tone tone semitone tone tone tone semitone
    private final int[] majorOffsets = new int[] {2, 2, 1, 2, 2, 2, 1};

    // tone semitone tone tone semitone tone tone
    private final int[] minorOffsets = new int[] {2, 1, 2, 2, 1, 2, 2};

    private final int sdOffset = 5; // Subdominan offset from tonica
    private final int dOffset = 7; // Dominant offset from tonica

    private final int[] triadOffsets; // Note offsets in triad

    /**
     * Generates arbitrary tonality
     */
    Tonality() {
        tonic = Randomizer.getRandomInt(0, 11);
        isMajor = Randomizer.getRandomBoolean();

        if(isMajor) {
            triadOffsets = new int[] {0, 4, 7};
        } else {
            triadOffsets = new int[] {0, 3, 7};
        }
    }

    /**
     * Sets constants of tonality
     *
     * @param tonic
     * @param isMajor
     */
    Tonality(int tonic, boolean isMajor) {
        this.tonic = tonic;
        this.isMajor = isMajor;

        if(isMajor) {
            triadOffsets = new int[] {0, 4, 7};
        } else {
            triadOffsets = new int[] {0, 3, 7};
        }
    }

    /**
     * Check chord to fit in tonality
     *
     * @param chord Chord to check
     * @return Distance from "good" chord state
     */
    public double checkChord(MyChord chord) {
        double result = 0;

        int note1 = (int)chord.n1;
        int shiftedNote1 = note1 - tonic; // Like it is C

        int tClosest = note1 - (shiftedNote1 % 12);
        int sdClosest = tClosest + sdOffset;
        int dClosest = tClosest + dOffset;

        double tDiff = abs(tClosest - chord.n1);
        double sdDiff = abs(sdClosest - chord.n1);
        double dDiff = abs(dClosest - chord.n1);

        double curDiff;
        int ideal1, ideal2;

        if(tDiff <= sdDiff && tDiff <= dDiff) {
            curDiff = tDiff;

            ideal1 = tClosest + triadOffsets[1];
            ideal2 = tClosest + triadOffsets[2];
        } else if(sdDiff <= dDiff) {
            curDiff = sdDiff;

            ideal1 = sdClosest + triadOffsets[1];
            ideal2 = sdClosest + triadOffsets[2];
        } else {
            curDiff = dDiff;

            ideal1 = dClosest + triadOffsets[1];
            ideal2 = dClosest + triadOffsets[2];
        }

        result += curDiff; // Distance to tonic/sub-dominant/dominant
        result += abs(ideal1 - chord.n2);
        result += abs(ideal2 - chord.n3);

        return result;
    }

    /**
     * Check note to fit in tonality and match chord
     *
     * @param note  Note to check
     * @param chord Chord to comply with
     * @return Distance from "good" note
     */
    public double checkNote(MyNote note, MyChord chord) {
        int intNote = (int)note.number;

        // Finding closest tonica/subdominant/dominant
        int tClosest = intNote - ((intNote - tonic) % 12) + ((int)(round(chord.n1) - tonic) % 12);
        int sdClosest = tClosest + triadOffsets[1];
        int dClosest = tClosest + triadOffsets[2];

        double tDiff = abs(tClosest - note.number);
        double sdDiff = abs(sdClosest - note.number);
        double dDiff = abs(dClosest - note.number);

        return min3(tDiff, sdDiff, dDiff);
    }

    /**
     * @return Minimum value between given three values
     */
    private double min3(double a, double b, double c) {
        return min(min(a, b), c);
    }

    @Override
    public String toString() {
        return "" + tonic + (isMajor ? "maj" : "min");
    }

}
