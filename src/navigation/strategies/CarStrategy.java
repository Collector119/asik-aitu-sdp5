package navigation.strategies;

public class CarStrategy implements RouteStrategy {
    private static final double CAR_SPEED_KMH = 40.0;
    private static final int PARKING_MINUTES = 10;
    private static final int MINUTES_PER_HOUR = 60;

    @Override
    public int calculateTravelMinutes(double distanceKm) {
        long drivingMinutes = Math.round(distanceKm / CAR_SPEED_KMH * MINUTES_PER_HOUR);
        return (int) drivingMinutes + PARKING_MINUTES;
    }
}
