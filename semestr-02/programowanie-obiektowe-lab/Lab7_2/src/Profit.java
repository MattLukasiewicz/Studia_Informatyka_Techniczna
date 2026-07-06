public class Profit {
    public static double calculateProfit(Transport transport, double distance, double payment) {
        double cost = transport.calculateCost(distance);
        return payment - cost;
    }
}

