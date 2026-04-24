package model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private String name;
    private List<Dimension> dimensions;

    public Scenario(String name) {
        this.name = name;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(Dimension d) {
        dimensions.add(d);
    }

    public String getName() { return name; }
    public List<Dimension> getDimensions() { return dimensions; }

    // Ödev gereği hazır senaryo (Örn: Education Scenario C)
    public static Scenario getEducationScenarioC() {
        Scenario s = new Scenario("Education - Team Alpha (Scenario C)");

        Dimension d1 = new Dimension("Usability", 25);
        d1.addMetric(new Metric("Learning Curve", 10, "Higher is better", "0-100", "Points"));
        d1.addMetric(new Metric("Task Completion", 15, "Higher is better", "0-100", "Percentage"));

        Dimension d2 = new Dimension("Performance", 30);
        d2.addMetric(new Metric("Response Time", 20, "Lower is better", "0-500", "ms"));

        s.addDimension(d1);
        s.addDimension(d2);
        return s;
    }
}