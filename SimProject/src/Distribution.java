import java.util.Random;

public class Distribution {

    Random rand = new Random();
    double geometric(double p){
        int t = 1;
        while(true){
            double r = rand.nextDouble();
            if(r <= p){
                break;
            }
            else
                t += 1;
        }
        return t;
    }

    int exponential(){
        return (int) (4*rand.nextDouble()+8);
    }

    public static int Geometric(int mean){

        double probability = (double) 1/mean;

        int trail = 1;
        while(!Bernoulli(probability))trail++;
        return trail;

    }


    public static boolean Bernoulli(double probability){

        Random random = new Random();
        double cumulitive_probability = random.nextDouble();
        return (cumulitive_probability<probability)?true:false;
    }

}
