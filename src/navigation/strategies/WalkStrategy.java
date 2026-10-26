package navigation.strategies;

public class WalkStrategy implements RouteStrategy {
    private static final double WALKING_SPEED_KMH = 5.0;
    private static final int MINUTES_PER_HOUR = 60;

    @Override
    public int calculateTravelMinutes(double distanceKm) {
        long walkingMinutes = Math.round(distanceKm / WALKING_SPEED_KMH * MINUTES_PER_HOUR);
        return (int) walkingMinutes;
    }
}
