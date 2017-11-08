/**
 * AI_music_generator
 * Created by Sergey on 2017-11-08
 */
public class MyVector1 {

    public double x;

    MyVector1(double min, double max) {
        x = Randomizer.getRandomDouble(min, max);
    }

    MyVector1(double num) {
        x = num;
    }

    public MyVector1 add(MyVector1 other) {
        return new MyVector1(x + other.x);
    }

    public MyVector1 add(MyVector1 other1, MyVector1 other2) {
        return new MyVector1(x + other1.x + other2.x);
    }

    public MyVector1 mul(double factor) {
        return new MyVector1(x * factor);
    }

    @Override
    public String toString() {
        return String.format("%6.2f", x);
    }
}
