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

    Tonality() {
        isMajor = Randomizer.getRandomBoolean();
        tonic = Randomizer.getRandomInt(0, 11);

        // Идея в том, чтобы сделать массив с расстоянием каждой ноты до ближайшей ноты в тональности
        // и еще один массив, просто содержащий всю послдовательность тональности
    }

    public boolean isMajor() {
        return isMajor;
    }

    public boolean isMinor() {
        return !isMajor;
    }

}
