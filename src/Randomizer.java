import java.util.Random;

/**
 * AI_music_generator
 * Created by Sergey on 2017-11-03
 */
public class Randomizer {

    public static int getRandomInt(int min, int max) {
        return min + new Random().nextInt(max);
    }

}
