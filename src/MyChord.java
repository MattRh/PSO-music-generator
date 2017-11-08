/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class MyChord {

    public double n1, n2, n3;

    MyChord(int minVal, int maxVal) {
        n1 = Randomizer.getRandomDouble(minVal, maxVal);
        n2 = Randomizer.getRandomDouble(minVal, maxVal);
        n3 = Randomizer.getRandomDouble(minVal, maxVal);
    }

    MyChord(double n1, double n2, double n3) {
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }

    public MyChord add(MyVector3 other) {
        return new MyChord(n1 + other.x, n2 + other.y, n3 + other.z);
    }

    public MyChord sumWith(MyVector3 other) {
        return new MyChord(n1 + other.x, n2 + other.y, n3 + other.z);
    }

    public MyChord sub(MyChord other) {
        return new MyChord(n1 - other.n1, n2 - other.n2, n3 - other.n3);
    }

    public MyVector3 subMul(MyChord other, double coeff) {
        return new MyVector3(coeff * (n1 - other.n1), coeff * (n2 - other.n2), coeff * (n3 - other.n3));
    }

    public MyChord mul(double factor) {
        return new MyChord(n1 * factor, n2 * factor, n3 * factor);
    }

    public boolean equals(MyChord other) {
        return ((int)n1) == ((int)other.n1) && ((int)n2) == ((int)other.n2) && ((int)n3) == ((int)other.n3);
    }

    public MyVector3 toVector() {
        return new MyVector3(n1, n2, n3);
    }

    @Override
    public String toString() {
        return String.format("[%.2f %.2f %.2f]", n1, n2, n3);
    }
}
