import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
 * AI_music_generator
 * Created by Sergey on 2017-11-06
 */
public class Tonality {

    private boolean isMajor;
    private int tonic;

    // tone tone semitone tone tone tone semitone
    private int[] majorOffsets = new int[] {2, 2, 1, 2, 2, 2, 1};

    // tone semitone tone tone semitone tone tone
    private int[] minorOffsets = new int[] {2, 1, 2, 2, 1, 2, 2};

    private int sdOffset = 5;
    private int dOffset = 7;

    private int[] triadOffsets;

    Tonality() {
        isMajor = Randomizer.getRandomBoolean();
        tonic = Randomizer.getRandomInt(0, 11);

        if(isMajor) {
            triadOffsets = new int[] {0, 4, 7};
        } else {
            triadOffsets = new int[] {0, 3, 7};
        }
    }

    public boolean isMajor() {
        return isMajor;
    }

    public boolean isMinor() {
        return !isMajor;
    }

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

    public double checkNote(MyNote note) {
        double result = 0;

        int intNote = (int)note.number;

        int tClosest = intNote - ((intNote - tonic) % 12);
        int sdClosest = tClosest + sdOffset;
        int dClosest = tClosest + dOffset;

        double tDiff = abs(tClosest - note.number);
        double sdDiff = abs(sdClosest - note.number);
        double dDiff = abs(dClosest - note.number);

        result += min3(tDiff, sdDiff, dDiff);

        return result;
    }

    private double min3(double a, double b, double c) {
        return min(min(a, b), c);
    }

    @Override
    public String toString() {
        return "" + tonic + (isMajor ? "maj" : "min");
    }
}
