
public class TrainCosts implements Transport {
    @Override
    public double calculateCost(double distance) {
        return 1.0/20.0 *distance;
    }
}
