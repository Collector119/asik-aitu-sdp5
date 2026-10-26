package navigation;

import navigation.context.Navigator;
import navigation.strategies.BusStrategy;
import navigation.strategies.CarStrategy;
import navigation.strategies.CycleStrategy;
import navigation.strategies.WalkStrategy;

public class Main {
    public static void main(String[] args) {
        Navigator navigator = new Navigator(new CarStrategy());

        printTitle("1. By car");
        printBothTrips(navigator);

        navigator.setRouteStrategy(new WalkStrategy());
        printTitle("2. On foot");
        printBothTrips(navigator);

        navigator.setRouteStrategy(new CycleStrategy());
        printTitle("3. By bicycle");
        printBothTrips(navigator);

        navigator.setRouteStrategy(new BusStrategy());
        printTitle("4. By bus");
        printBothTrips(navigator);
    }

    private static void printBothTrips(Navigator navigator) {
        System.out.println(navigator.planRoute("Dormitory", "AITU", 1.5));
        System.out.println(navigator.planRoute("Khan Shatyr", "AITU", 7.0));
    }

    private static void printTitle(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }
}
