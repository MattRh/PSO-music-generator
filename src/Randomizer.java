import java.util.Random;

/**
 * AI_music_generator
 * Created by Sergey on 2017-11-03
 */
public class Randomizer {

    public static int getRandomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public static double getRandomDouble(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    /**
     * @return Factor in range [0, 1). It is simplest way to get close to range [0, 1]
     */
    public static double getRandomFactor() {
        return new Random().nextDouble();
    }

    public static boolean getRandomBoolean() {
        return new Random().nextBoolean();
    }

}
