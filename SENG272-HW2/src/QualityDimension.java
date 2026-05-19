import java.util.ArrayList;

public class QualityDimension {
    private String name;
    private String isoCode; // ISO identifier code [cite: 49]
    private double weight;  // Weight value for overall quality [cite: 50]
    private ArrayList<Criterion> criteria;

    public QualityDimension(String name, String isoCode, double weight) {
        this.name = name;
        this.isoCode = isoCode;
        this.weight = weight;
        this.criteria = new ArrayList<>();
    }

    public void addCriterion(Criterion criterion) {
        criteria.add(criterion);
    }

    // Dimension Score Calculation: Weighted average of metrics [cite: 55, 56]
    public double calculateDimensionScore() {
        double totalWeightedScore = 0;
        double totalWeight = 0;

        for (Criterion c : criteria) {
            totalWeightedScore += (c.calculateScore() * c.getWeight());
            totalWeight += c.getWeight();
        }

        if (totalWeight == 0) return 0;
        // Formula: Sum(score * weight) / Sum(weights)
        return totalWeightedScore / totalWeight;
    }

    // Quality Label Classification based on score ranges [cite: 58]
    public String getQualityLabel() {
        double score = calculateDimensionScore();
        if (score >= 4.5) return "Excellent Quality"; // 4.5-5.0 [cite: 58]
        if (score >= 3.5) return "Good Quality";      // 3.5-4.4 [cite: 58]
        if (score >= 2.5) return "Needs Improvement"; // 2.5-3.4 [cite: 58]
        return "Poor Quality";                        // 1.0-2.4 [cite: 58]
    }

    public double getQualityGap() {
        return 5.0 - calculateDimensionScore(); // Difference from max possible score [cite: 59]
    }

    // Getters
    public String getName() { return name; }
    public String getIsoCode() { return isoCode; }
    public double getWeight() { return weight; }
    public ArrayList<Criterion> getCriteria() { return criteria; }

    @Override
    public String toString() {
        return "--- " + name + " [" + isoCode + "] (Weight: " + weight + ") ---";
    }
}
