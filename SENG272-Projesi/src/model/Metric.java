package model;

/**
 * ISO 15939 metric definition and per-session collected value / score.
 * Score formulas (Step 4) follow the assignment specification.
 */
public class Metric {
    private final String name;
    private final int coefficient;
    private final String direction;
    private final String range;
    private final String unit;
    /** Sample default shown in Collect when the scenario is loaded. */
    private final double defaultSampleValue;

    private double rawValue;
    private double calculatedScore;

    public Metric(String name, int coefficient, String direction, String range, String unit,
                  double defaultSampleValue) {
        this.name = name;
        this.coefficient = coefficient;
        this.direction = direction;
        this.range = range;
        this.unit = unit;
        this.defaultSampleValue = defaultSampleValue;
    }

    /**
     * Backward-compatible constructor: no explicit default sample (uses midpoint of range).
     */
    public Metric(String name, int coefficient, String direction, String range, String unit) {
        this(name, coefficient, direction, range, unit, Double.NaN);
    }

    /**
     * Computes a 1–5 score from a raw measurement.
     * <ul>
     *   <li>Higher is better: {@code 1 + ((value - min) / (max - min)) * 4}</li>
     *   <li>Lower is better: {@code 5 - ((value - min) / (max - min)) * 4}</li>
     * </ul>
     * <p><b>Rounding rule (mandatory):</b> the value is first clamped to {@code [1.0, 5.0]},
     * then rounded to the nearest {@code 0.5}. We implement this by {@code Math.round(score * 2) / 2.0},
     * because multiplying by 2 turns steps of 0.5 into integers (e.g. 4.23 → 8.46 → round 8 → 4.0;
     * 4.42 → 8.84 → round 9 → 4.5).</p>
     */
    public double calculateScore(double value) {
        this.rawValue = value;
        double min;
        double max;
        try {
            String[] parts = this.range.split("-");
            min = Double.parseDouble(parts[0].trim());
            max = Double.parseDouble(parts[1].trim());
        } catch (Exception ex) {
            this.calculatedScore = 1.0;
            return this.calculatedScore;
        }

        double span = max - min;
        if (span <= 0) {
            this.calculatedScore = 3.0;
            return this.calculatedScore;
        }

        double unclamped;
        if (direction != null && direction.contains("Higher")) {
            unclamped = 1 + ((value - min) / span) * 4;
        } else {
            unclamped = 5 - ((value - min) / span) * 4;
        }

        double clamped = Math.max(1.0, Math.min(5.0, unclamped));
        this.calculatedScore = Math.round(clamped * 2) / 2.0;
        return this.calculatedScore;
    }

    public Metric copy() {
        Metric m = new Metric(name, coefficient, direction, range, unit, defaultSampleValue);
        m.rawValue = this.rawValue;
        m.calculatedScore = this.calculatedScore;
        return m;
    }

    public String getName() {
        return name;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public String getDirection() {
        return direction;
    }

    public String getRange() {
        return range;
    }

    public String getUnit() {
        return unit;
    }

    public double getDefaultSampleValue() {
        return defaultSampleValue;
    }

    public double getRawValue() {
        return rawValue;
    }

    public double getCalculatedScore() {
        return calculatedScore;
    }

    /** Clears session values when the Collect table cell is empty or invalid. */
    public void clearScore() {
        this.rawValue = Double.NaN;
        this.calculatedScore = 0.0;
    }
}
