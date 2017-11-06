/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class MyChord {

    // TODO: convert notes from int to double
    public int n1, n2, n3;

    MyChord(int minVal, int maxVal) {
        n1 = Randomizer.getRandomInt(minVal, maxVal);
        n2 = Randomizer.getRandomInt(minVal, maxVal);
        n3 = Randomizer.getRandomInt(minVal, maxVal);
    }

    MyChord(int n1, int n2, int n3) {
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }

    public void set(MyChord other) {
        n1 = other.n1;
        n2 = other.n2;
        n3 = other.n3;
    }

    public void set(MyVector3 other) {
        n1 = (int)other.x;
        n2 = (int)other.y;
        n3 = (int)other.z;
    }

    public MyChord add(MyVector3 other) {
        return new MyChord(n1 + (int)other.x, n2 + (int)other.y, n3 + (int)other.z);
    }

    public void sumWith(MyVector3 other) {
        n1 += (int)other.x;
        n2 += (int)other.y;
        n3 += (int)other.z;
    }

    public MyChord sub(MyChord other) {
        return new MyChord(n1 - other.n1, n2 - other.n2, n3 - other.n3);
    }

    public MyVector3 subMul(MyChord other, double coeff) {
        return new MyVector3(coeff * (n1 - other.n1), coeff * (n2 - other.n2), coeff * (n3 - other.n3));
    }

    public MyChord mul(double factor) {
        return new MyChord((int)(n1 * factor), (int)(n2 * factor), (int)(n3 * factor));
    }

    public MyVector3 toVector() {
        return new MyVector3(n1, n2, n3);
    }

    @Override
    public String toString() {
        return "[" + n1 + " " + n2 + " " + n3 + "]";
    }
}
