package navigation.context;

import navigation.strategies.RouteStrategy;

public class Navigator {
    private RouteStrategy routeStrategy;

    public Navigator(RouteStrategy routeStrategy) {
        this.routeStrategy = routeStrategy;
    }

    public void setRouteStrategy(RouteStrategy routeStrategy) {
        this.routeStrategy = routeStrategy;
    }

    public String planRoute(String from, String to, double distanceKm) {
        int travelMinutes = routeStrategy.calculateTravelMinutes(distanceKm);
        return from + " -> " + to + " (" + distanceKm + " km): " + travelMinutes + " min";
    }
}
