# Assignment 5 — Strategy Pattern

| | |
|---|---|
| **Student** | Anarov Daniyal |
| **Group** | SE-2530 |
| **Course** | ShP-2216 — Software Design Patterns |
| **Repository** | https://github.com/Collector119/asik-aitu-sdp5 |

## 1. Introduction

I chose Option A, route planning. The context is `Navigator`. It plans a trip and estimates how
many minutes it takes. The algorithm family is the way to calculate the travel time, one algorithm
for each way of travelling:

| Strategy | Algorithm |
|---|---|
| `CarStrategy` | driving at 40 km/h, plus 10 minutes to park |
| `WalkStrategy` | walking at 5 km/h |
| `CycleStrategy` | cycling at 15 km/h, plus 2 minutes to lock the bike |
| `BusStrategy` | riding at 25 km/h, plus 8 minutes of waiting at the stop |

`Main` is the client. It creates one navigator and switches its strategy at runtime. Output:

```
=== 1. By car ===
Dormitory -> AITU (1.5 km): 12 min
Khan Shatyr -> AITU (7.0 km): 21 min

=== 2. On foot ===
Dormitory -> AITU (1.5 km): 18 min
Khan Shatyr -> AITU (7.0 km): 84 min

=== 3. By bicycle ===
Dormitory -> AITU (1.5 km): 8 min
Khan Shatyr -> AITU (7.0 km): 30 min

=== 4. By bus ===
Dormitory -> AITU (1.5 km): 12 min
Khan Shatyr -> AITU (7.0 km): 25 min
```

## 2. UML class diagram

![Class diagram](https://raw.githubusercontent.com/Collector119/asik-aitu-sdp5/main/docs/class-diagram.png)

- `RouteStrategy` is the strategy interface. It declares one method, `calculateTravelMinutes(double)`.
- `CarStrategy`, `WalkStrategy`, `CycleStrategy` and `BusStrategy` are the concrete strategies.
- `Navigator` is the context. It holds a `RouteStrategy` field and delegates the calculation to it.
  `setRouteStrategy(...)` changes the strategy at runtime.
- `Main` is the client. It creates the concrete strategies and gives them to the navigator.

`Navigator` has an arrow only to the interface, not to any concrete strategy.

## 3. Clean Code

### 3.1 Polymorphism instead of conditional logic

Before (one method with a branch for each way of travelling):

```java
public int calculateTravelMinutes(String mode, double distanceKm) {
    if (mode.equals("car")) {
        return (int) Math.round(distanceKm / 40.0 * 60) + 10;
    } else if (mode.equals("walk")) {
        return (int) Math.round(distanceKm / 5.0 * 60);
    } else if (mode.equals("cycle")) {
        return (int) Math.round(distanceKm / 15.0 * 60) + 2;
    }
    throw new IllegalArgumentException("Unknown mode: " + mode);
}
```

After (`Navigator.planRoute`):

```java
int travelMinutes = routeStrategy.calculateTravelMinutes(distanceKm);
```

There is no `if` or `switch` in `Navigator`. Java calls the right method because of the object that
is stored in `routeStrategy`. An unknown mode is also impossible: the client can only pass an object
that implements `RouteStrategy`.

### 3.2 Meaningful, intention-revealing names

```java
public interface RouteStrategy {
    int calculateTravelMinutes(double distanceKm);
}
```

The method says what it returns (minutes) and the parameter says its unit (kilometres). The class
names follow the task: `CarStrategy`, `WalkStrategy`, `CycleStrategy`. The numbers inside the
strategies are named constants instead of magic numbers:

```java
private static final double CAR_SPEED_KMH = 40.0;
private static final int PARKING_MINUTES = 10;
private static final int MINUTES_PER_HOUR = 60;
```

### 3.3 Single Responsibility: each strategy does one algorithm

```java
public class CycleStrategy implements RouteStrategy {
    ...
    @Override
    public int calculateTravelMinutes(double distanceKm) {
        long cyclingMinutes = Math.round(distanceKm / CYCLING_SPEED_KMH * MINUTES_PER_HOUR);
        return (int) cyclingMinutes + BIKE_LOCKING_MINUTES;
    }
}
```

Each strategy has one method and calculates the time for one way of travelling. It does not print
anything and does not build the text of the route. Building the text is the job of `Navigator`.
If the speed of a bicycle changes, only `CycleStrategy` changes.

### 3.4 The context stays algorithm-agnostic

```java
import navigation.strategies.RouteStrategy;

public class Navigator {
    private RouteStrategy routeStrategy;
    ...
}
```

`Navigator` imports only the interface. It does not know the names of the concrete strategies,
their speeds or their extra minutes. It only knows that the strategy returns minutes for a
distance.

### 3.5 Easy extensibility: adding a strategy without touching existing classes

I added `BusStrategy` in a separate commit after the first three strategies were done:

```
add the bus strategy without changing existing classes

 src/navigation/Main.java                   |  5 +++++
 src/navigation/strategies/BusStrategy.java | 13 +++++++++++++
```

The commit creates one new class and adds lines only to the client:

```java
navigator.setRouteStrategy(new BusStrategy());
printTitle("4. By bus");
printBothTrips(navigator);
```

`Navigator`, `RouteStrategy` and the other three strategies are not in the commit. With the
"before" version from 3.1 I would have to add one more `else if` inside the existing method.

## 4. Strategy vs. State

Both patterns have a context that holds an object behind an interface and delegates to it. The
difference is who changes that object and why. In State the object changes itself: a state decides
which state comes next, and the context moves between states because of its own events. In my
problem nothing like that happens. A car route never turns into a walking route by itself. The
client chooses how the user wants to travel and sets that strategy on the navigator. The strategies
do not know about each other or about `Navigator`, and any of them can be used for any trip. So
this is a choice of algorithm from outside, which is the Strategy pattern.

## 5. Conclusion

I added five types for one calculation: one interface and four strategies. The "before" version from
section 3.1 is a single method, so for a very small program the pattern means more classes. In
return, every way of travelling is in its own small class, `Navigator` has no conditionals, the
strategy can be switched while the program runs, and a new way of travelling is a new class instead
of a change in an old one. The `BusStrategy` commit showed this in practice.

## 6. Repository

https://github.com/Collector119/asik-aitu-sdp5

## References

- Freeman, E., & Robson, E. (2021). *Head First Design Patterns* (2nd ed.), Ch. 1.
- Martin, R. C. (2008). *Clean Code*, Ch. 10.
- Refactoring.Guru, Strategy: https://refactoring.guru/design-patterns/strategy
