
public class PlaneCosts implements Transport {
    @Override
    public double calculateCost(double distance) {
        return 1.0/100.0 * distance*distance;
    }
}
