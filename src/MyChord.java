/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class MyChord {

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

    public void add(MyChord other) {
        n1 -= other.n1;
        n2 -= other.n2;
        n3 -= other.n3;
    }

    public void sub(MyChord other) {
        n1 -= other.n1;
        n2 -= other.n2;
        n3 -= other.n3;
    }

    public void set(MyVector3 other) {
        n1 = other.x;
        n2 = other.y;
        n3 = other.z;
    }

    public void add(MyVector3 other) {
        n1 += other.x;
        n2 += other.y;
        n3 += other.z;
    }

    public void sub(MyVector3 other) {
        n1 -= other.x;
        n2 -= other.y;
        n3 -= other.z;
    }

    public void mul(double factor) {
        n1 *= factor;
        n2 *= factor;
        n3 *= factor;
    }

    public MyVector3 toVector() {
        return new MyVector3(n1, n2, n3);
    }

}
