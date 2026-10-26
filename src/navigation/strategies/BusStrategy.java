package navigation.strategies;

public class BusStrategy implements RouteStrategy {
    private static final double BUS_SPEED_KMH = 25.0;
    private static final int WAITING_AT_STOP_MINUTES = 8;
    private static final int MINUTES_PER_HOUR = 60;

    @Override
    public int calculateTravelMinutes(double distanceKm) {
        long ridingMinutes = Math.round(distanceKm / BUS_SPEED_KMH * MINUTES_PER_HOUR);
        return (int) ridingMinutes + WAITING_AT_STOP_MINUTES;
    }
}
