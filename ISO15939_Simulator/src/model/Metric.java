package model;

public class Metric {
    private String name;
    private int coefficient;
    private String direction;
    private String range;
    private String unit;
    private double rawValue;
    private double calculatedScore;

    public Metric(String name, int coefficient, String direction, String range, String unit) {
        this.name = name;
        this.coefficient = coefficient;
        this.direction = direction;
        this.range = range;
        this.unit = unit;
    }

    public double calculateScore(double value) {
        this.rawValue = value;
        String[] parts = this.range.split("-");
        double min = Double.parseDouble(parts[0]);
        double max = Double.parseDouble(parts[1]);

        double score;
        if (this.direction.contains("Higher")) {
            // Formül: 1 + (value - min) / (max - min) * 4
            score = 1 + ((value - min) / (max - min)) * 4;
        } else {
            // Formül: 5 - (value - min) / (max - min) * 4
            score = 5 - ((value - min) / (max - min)) * 4;
        }

        // Puanı 1.0 - 5.0 arasına sınırla ve 0.5'e yuvarla
        score = Math.max(1.0, Math.min(5.0, score));
        this.calculatedScore = Math.round(score * 2) / 2.0;
        return this.calculatedScore;
    }

    // Getter Metotları
    public String getName() { return name; }
    public int getCoefficient() { return coefficient; }
    public String getDirection() { return direction; }
    public String getRange() { return range; }
    public String getUnit() { return unit; }
    public double getCalculatedScore() { return calculatedScore; }
}