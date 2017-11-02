/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class MyVector3 {

    public int x, y, z;

    MyVector3(int minVal, int maxVal) {
        x = Randomizer.getRandomInt(minVal, maxVal);
        y = Randomizer.getRandomInt(minVal, maxVal);
        z = Randomizer.getRandomInt(minVal, maxVal);
    }

    MyVector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(MyVector3 other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public void add(MyVector3 other) {
        x += other.x;
        y += other.y;
        z += other.z;
    }

    public void sub(MyVector3 other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
    }

    public void mul(double factor) {
        x *= factor;
        y *= factor;
        z *= factor;
    }

}
