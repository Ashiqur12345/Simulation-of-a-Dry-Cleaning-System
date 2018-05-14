import java.util.Random;

public class Damage {
    Random rand = new Random();

    boolean findDamage(double p)
    {
        double r = rand.nextDouble();
        if(p >= r)
            return true;
        else
            return false;
    }
}
