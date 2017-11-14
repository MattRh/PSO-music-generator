/**
 * Simple note
 * <p>
 * Created by Sergey on 2017-10-27
 */
public class MyNote {

    public double number;

    MyNote(double min, double max) {
        number = Randomizer.getRandomDouble(min, max);
    }

    MyNote(double num) {
        number = num;
    }

    public MyNote add(MyVector1 other) {
        return new MyNote(number + other.x);
    }

    public MyNote sumWith(MyVector1 other) {
        return new MyNote(number + other.x);
    }

    public MyNote sub(MyNote other) {
        return new MyNote(number - other.number);
    }

    public MyVector1 subMul(MyNote other, double coeff) {
        return new MyVector1(coeff * (number - other.number));
    }

    public MyNote mul(double factor) {
        return new MyNote(number * factor);
    }

    public boolean equals(MyNote other) {
        return ((int)number) == ((int)other.number);
    }

    public MyVector1 toVector() {
        return new MyVector1(number);
    }

    @Override
    public String toString() {
        return String.format("%.2f", number);
    }

}
