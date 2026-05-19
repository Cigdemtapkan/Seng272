import java.util.ArrayList;
import java.util.HashMap;

public class SWSystemData { // Sınıf başlangıcı

    public static HashMap<String, ArrayList<SWSystem>> getAllSystems() { //
        HashMap<String, ArrayList<SWSystem>> map = new HashMap<>(); //

        // Web Kategorisi
        ArrayList<SWSystem> webList = new ArrayList<>(); //
        webList.add(createECommercePlatform()); //
        webList.add(createBankingPortal()); //
        map.put("Web", webList); //

        // Mobile Kategorisi
        ArrayList<SWSystem> mobileList = new ArrayList<>(); //
        mobileList.add(createHealthApp()); //
        map.put("Mobile", mobileList); //

        return map; //
    } // getAllSystems metodu kapanışı

    private static SWSystem createECommercePlatform() { //
        SWSystem s = new SWSystem("ShopSphere", "Web", "3.2.1"); //

        // 1. Functional Suitability [QC.FS]
        QualityDimension funcSuit = new QualityDimension("Functional Suitability", "QC.FS", 25); //
        funcSuit.addCriterion(new Criterion("Functional Completeness Ratio", 50, "higher", 0, 100, "%")); //
        funcSuit.addCriterion(new Criterion("Functional Correctness Ratio", 50, "higher", 0, 100, "%")); //
        s.addDimension(funcSuit); //

        // 2. Reliability [QC.RE]
        QualityDimension reliability = new QualityDimension("Reliability", "QC.RE", 25); //
        reliability.addCriterion(new Criterion("Availability Ratio", 50, "higher", 95, 100, "%")); //
        reliability.addCriterion(new Criterion("Defect Density", 50, "lower", 0, 20, "defect/KLOC")); //
        s.addDimension(reliability); //

        // 3. Performance Efficiency [QC.PE]
        QualityDimension perfEff = new QualityDimension("Performance Efficiency", "QC.PE", 25);
        perfEff.addCriterion(new Criterion("Response Time", 50, "lower", 100, 500, "ms"));
        perfEff.addCriterion(new Criterion("CPU Utilisation Ratio", 50, "lower", 0, 100, "%"));
        s.addDimension(perfEff);

        // 4. Maintainability [QC.MA]
        QualityDimension maintainability = new QualityDimension("Maintainability", "QC.MA", 25);
        maintainability.addCriterion(new Criterion("Test Coverage Ratio", 50, "higher", 0, 100, "%"));
        maintainability.addCriterion(new Criterion("Cyclomatic Complexity (avg)", 50, "lower", 1, 30, "score"));
        s.addDimension(maintainability);

        return s; //
    } // createECommercePlatform metodu kapanışı

    private static SWSystem createBankingPortal() { //
        return new SWSystem("GlobalBank", "Web", "1.0.0");
    }

    private static SWSystem createHealthApp() { //
        return new SWSystem("PulseCheck", "Mobile", "2.1.4");
    }

} // SINIF KAPANIŞI (Hata muhtemelen bu parantezin eksik olmasındaydı)
