public class Criterion {
    private String name;
    private double weight;
    private String direction;
    private double minValue;
    private double maxValue;
    private String unit;
    private double measuredValue;

    public Criterion(String name, double weight, String direction, double minValue, double maxValue, String unit) {
        this.name = name;
        this.weight = weight;
        this.direction = direction;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;

    }


    public double calculateScore() {
        double score;


        if (direction.equalsIgnoreCase("higher")) {
            score = 1 + ((measuredValue - minValue) / (maxValue - minValue)) * 4;
        } else {
            score = 5 - ((measuredValue - minValue) / (maxValue - minValue)) * 4;
        }


        score = Math.max(1, Math.min(5, score));
        return Math.round(score * 2) / 2.0;
    }


    public String getName() { return name; }
    public double getWeight() { return weight; }
    public String getUnit() { return unit; }
    public double getMeasuredValue() { return measuredValue; }
    public void setMeasuredValue(double measuredValue) { this.measuredValue = measuredValue; }

    @Override
    public String toString() {
        return name + ": " + measuredValue + " " + unit + " -> Score: " + calculateScore() +
                " (" + (direction.equalsIgnoreCase("higher") ? "Higher is better" : "Lower is better") + ")";
    }
}
