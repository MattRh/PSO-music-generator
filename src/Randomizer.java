import java.util.Random;

/**
 * Random values generator wrapper
 * <p>
 * Created by Sergey on 2017-11-03
 */
public class Randomizer {

    /**
     * Generates random integer in range
     *
     * @param min Min value
     * @param max Max value
     * @return random in [min, max]
     */
    public static int getRandomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    /**
     * Generates random double in range
     *
     * @param min Min value
     * @param max Max value
     * @return random in [min, max)
     */
    public static double getRandomDouble(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    /**
     * @return Factor in range [0, 1). It is simplest way to get close to range [0, 1]
     */
    public static double getRandomFactor() {
        return Math.random();
    }

    /**
     * @return Random boolean
     */
    public static boolean getRandomBoolean() {
        return new Random().nextBoolean();
    }

}
