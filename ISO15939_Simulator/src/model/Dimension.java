package model;

import java.util.ArrayList;
import java.util.List;

public class Dimension {
    private String name;
    private int coefficient;
    private List<Metric> metrics;
    // Analiz aşamasında hesaplanan puanı tutmak için gerekli
    private double finalScore;

    public Dimension(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
        this.metrics = new ArrayList<>();
    }

    public void addMetric(Metric m) {
        metrics.add(m);
    }

    // Getter Metotları
    public String getName() { return name; }
    public int getCoefficient() { return coefficient; }
    public List<Metric> getMetrics() { return metrics; }

    // Analiz ekranı için eklediğimiz Getter ve Setter
    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }
}
