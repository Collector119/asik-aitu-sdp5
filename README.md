# Assignment 5 — Strategy Pattern

Course: ShP-2216 Software Design Patterns (OP 6B06102)
Astana IT University, group SE-2530
Java 17

## What the program does

A route planning example (Option A). `Navigator` is the context. It estimates the travel time of a
trip with the strategy it holds, and the strategy can be changed at runtime:

```java
Navigator navigator = new Navigator(new CarStrategy());
navigator.planRoute("Dormitory", "AITU", 1.5);

navigator.setRouteStrategy(new WalkStrategy());
navigator.planRoute("Dormitory", "AITU", 1.5);
```

## Folder structure

```
src/navigation/
├── Main.java                 - client, chooses and switches the strategies
├── context/
│   └── Navigator.java        - context
└── strategies/
    ├── RouteStrategy.java    - strategy interface
    ├── CarStrategy.java
    ├── WalkStrategy.java
    ├── CycleStrategy.java
    └── BusStrategy.java
docs/
├── REPORT.md
├── class-diagram.puml
└── class-diagram.png
```

## How to build and run

```bash
javac --release 17 -d out $(find src -name '*.java')
java -cp out navigation.Main
```

In IntelliJ IDEA: open the folder, mark `src` as Sources Root, then run `Main`.

## How to add a new strategy

1. Create a class in `src/navigation/strategies/` that implements `RouteStrategy`:

   ```java
   public class ScooterStrategy implements RouteStrategy {
       @Override
       public int calculateTravelMinutes(double distanceKm) {
           ...
       }
   }
   ```

2. Pass it to the navigator in the client:

   ```java
   navigator.setRouteStrategy(new ScooterStrategy());
   ```

`Navigator` and the other strategies do not change.
