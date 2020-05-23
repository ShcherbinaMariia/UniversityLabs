import java.util.Random;

public class ExponentialRNG {

    double lambda;
    Random rng = new Random();

    ExponentialRNG(double lambda) {
        rng.setSeed(1234);
        this.lambda = lambda;
    }

    public double next() {
        return (-1/this.lambda) * Math.log(rng.nextDouble());
    }
}
