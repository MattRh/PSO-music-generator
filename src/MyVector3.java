/**
 * AI_music_generator
 * Created by Sergey on 2017-10-27
 */
public class MyVector3 {

    public double x, y, z;

    MyVector3(int minVal, int maxVal) {
        x = Randomizer.getRandomDouble(minVal, maxVal);
        y = Randomizer.getRandomDouble(minVal, maxVal);
        z = Randomizer.getRandomDouble(minVal, maxVal);
    }

    MyVector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(MyVector3 other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public MyVector3 add(MyVector3 other) {
        return new MyVector3(x + other.x, y + other.y, z + other.z);
    }

    public MyVector3 mul(double factor) {
        return new MyVector3(x * factor, y * factor, z * factor);
    }

}
