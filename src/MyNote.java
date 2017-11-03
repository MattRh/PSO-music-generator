/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class MyNote {

    public int number;

    MyNote(int minVal, int maxVal) {
        number = Randomizer.getRandomInt(minVal, maxVal);
    }

}
