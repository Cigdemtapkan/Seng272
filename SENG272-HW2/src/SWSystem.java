import java.util.ArrayList;

public class SWSystem {
    private String name; //
    private String category; //
    private String version; //
    private ArrayList<QualityDimension> dimensions; //

    public SWSystem(String name, String category, String version) {
        this.name = name;
        this.category = category;
        this.version = version;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(QualityDimension dimension) { //
        dimensions.add(dimension);
    }

    public ArrayList<QualityDimension> getDimensions() {
        return dimensions;
    }

    // Overall Quality Score: Aggregating scores of all characteristics with weights
    public double calculateOverallScore() {
        double totalWeightedScore = 0;
        double totalWeight = 0;
        for (QualityDimension qd : dimensions) {
            totalWeightedScore += (qd.calculateDimensionScore() * qd.getWeight());
            totalWeight += qd.getWeight();
        }
        if (totalWeight == 0) return 0;
        return totalWeightedScore / totalWeight;
    }

    // Weakest Dimension Identification: Finding the lowest score
    public QualityDimension findWeakestDimension() {
        if (dimensions.isEmpty()) return null;
        QualityDimension weakest = dimensions.get(0);
        for (QualityDimension qd : dimensions) {
            if (qd.calculateDimensionScore() < weakest.calculateDimensionScore()) {
                weakest = qd;
            }
        }
        return weakest;
    }

    // Reporting Requirement: Generating structured evaluation report
    public void printReport() {
        System.out.println("===");
        System.out.println("SOFTWARE QUALITY EVALUATION REPORT (ISO/IEC 25010)");
        System.out.println("System: " + name + " v" + version + " (" + category + ")"); //

        for (QualityDimension qd : dimensions) {
            System.out.println(qd.toString()); //
            for (Criterion c : qd.getCriteria()) {
                System.out.println(c.toString()); //
            }
            System.out.printf(">> Dimension Score: %.1f/5 [%s]\n",
                    qd.calculateDimensionScore(), qd.getQualityLabel()); //
        }

        double overall = calculateOverallScore(); //
        System.out.printf("\nOVERALL QUALITY SCORE: %.1f/5 [%s]\n", overall, getQualityLabel(overall));

        System.out.println("\nGAP ANALYSIS (ISO/IEC 25010)");
        QualityDimension weakest = findWeakestDimension(); //
        if (weakest != null) {
            System.out.println("Weakest Characteristic: " + weakest.getName() + " [" + weakest.getIsoCode() + "]");
            System.out.printf("Score: %.1f/5 Gap: %.1f\n", weakest.calculateDimensionScore(), weakest.getQualityGap());
            System.out.println("Level: " + weakest.getQualityLabel());
            System.out.println(">> This characteristic requires the most improvement."); //
        }
    }

    // Quality Label Classification table
    private String getQualityLabel(double score) {
        if (score >= 4.5) return "Excellent Quality"; // 4.5-5.0
        if (score >= 3.5) return "Good Quality";      // 3.5-4.4
        if (score >= 2.5) return "Needs Improvement"; // 2.5-3.4
        return "Poor Quality";                        // 1.0-2.4
    }

    public String getName() { return name; }

} // DOSYANIN EN SONU - BAŞKA HİÇBİR ŞEY OLMAMALI