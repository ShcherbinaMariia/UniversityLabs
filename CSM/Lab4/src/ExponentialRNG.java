public class ExponentialRNG {

    double lambda;

    ExponentialRNG(double lambda) {
        this.lambda = lambda;
    }

    public double next() {
        return (-1/this.lambda) * Math.log(Math.random());
    }
}
