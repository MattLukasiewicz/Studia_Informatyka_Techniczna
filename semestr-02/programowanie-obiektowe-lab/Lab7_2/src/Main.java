public class Main{
    public static void main(String[] args) {
        //zysk za zrealizowanie zlecenia z dystansem 200 i wynagrodzeniem 10 z wykorzystaniem pociągu
        Transport train = new TrainCosts();
        double profitTrain = Profit.calculateProfit(train, 200, 10);
        System.out.println("Profit using train: " + profitTrain);

        //Które z podanych zleceń da większy zysk
        Transport plane = new PlaneCosts();
        //50 50
        double profitPlane50 = Profit.calculateProfit(plane, 50, 50);
        System.out.println("Profit for distance 50: " + profitPlane50);
        //30 40
        double profitPlane30 = Profit.calculateProfit(plane, 30, 40);
        System.out.println("Profit for distance 30: " + profitPlane30);
        //Odp.Zlecenie 30 40 ma wiekszy zysk

        // Srodek transportu da większe zyski dla zlecenia z dystansem 350 i wynagrodzeniem 4
        double profitPlane350 = Profit.calculateProfit(plane, 350, 45);
        System.out.println("Profit using plane for distance 350: " + profitPlane350);

        double profitTrain350 = Profit.calculateProfit(train, 350, 45);
        System.out.println("Profit using train for distance 350: " + profitTrain350);
        //Odp. Train


    }
}