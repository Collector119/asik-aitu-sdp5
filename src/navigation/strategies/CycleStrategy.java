package navigation.strategies;

public class CycleStrategy implements RouteStrategy {
    private static final double CYCLING_SPEED_KMH = 15.0;
    private static final int BIKE_LOCKING_MINUTES = 2;
    private static final int MINUTES_PER_HOUR = 60;

    @Override
    public int calculateTravelMinutes(double distanceKm) {
        long cyclingMinutes = Math.round(distanceKm / CYCLING_SPEED_KMH * MINUTES_PER_HOUR);
        return (int) cyclingMinutes + BIKE_LOCKING_MINUTES;
    }
}
