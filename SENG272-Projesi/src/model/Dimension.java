package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Quality dimension grouping several metrics (ISO 15939 tree node).
 */
public class Dimension {
    private final String name;
    private final int coefficient;
    private final List<Metric> metrics;
    private double finalScore;

    public Dimension(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
        this.metrics = new ArrayList<>();
    }

    public void addMetric(Metric m) {
        metrics.add(m);
    }

    public String getName() {
        return name;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public Dimension copy() {
        Dimension d = new Dimension(name, coefficient);
        for (Metric m : metrics) {
            d.addMetric(m.copy());
        }
        d.finalScore = this.finalScore;
        return d;
    }
}
