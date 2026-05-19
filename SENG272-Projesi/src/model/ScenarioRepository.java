package model;

/**
 * Hardcoded scenario templates (MVC: data separate from Swing).
 * At least two modes (Health, Education) with two scenarios each, plus Custom.
 */
public final class ScenarioRepository {

    private ScenarioRepository() {
    }

    /**
     * @param mode        "Education", "Health", or "Custom" (from DefinePanel)
     * @param comboLabel  exact JComboBox item text
     */
    public static Scenario createWorkingCopy(String mode, String comboLabel) {
        Scenario template = resolveTemplate(mode, comboLabel);
        return template.deepCopy();
    }

    private static Scenario resolveTemplate(String mode, String comboLabel) {
        if ("Education".equals(mode)) {
            if (comboLabel != null && comboLabel.contains("Scenario D")) {
                return educationScenarioDTeamBeta();
            }
            return educationScenarioCTeamAlpha();
        }
        if ("Health".equals(mode)) {
            if (comboLabel != null && comboLabel.contains("Scenario B")) {
                return healthScenarioBPatientPortal();
            }
            return healthScenarioAHospitalAdmin();
        }
        if ("Custom".equals(mode)) {
            return customNewScenario();
        }
        return educationScenarioCTeamAlpha();
    }

    /**
     * Education — Scenario C (Team Alpha) — full dataset per specification.
     */
    private static Scenario educationScenarioCTeamAlpha() {
        Scenario s = new Scenario("Education - Scenario C (Team Alpha)");

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score", 50, "Higher is better", "0-100", "points", 89));
        usability.addMetric(new Metric("Onboarding time", 50, "Lower is better", "0-60", "min", 5));
        s.addDimension(usability);

        Dimension performance = new Dimension("Performance Efficiency", 20);
        performance.addMetric(new Metric("Video start time", 50, "Lower is better", "0-15", "sec", 3));
        performance.addMetric(new Metric("Concurrent exams", 50, "Higher is better", "0-600", "users", 400));
        s.addDimension(performance);

        Dimension accessibility = new Dimension("Accessibility", 20);
        accessibility.addMetric(new Metric("WCAG conformance score", 50, "Higher is better", "0-100", "points", 88));
        accessibility.addMetric(new Metric("Keyboard-only task success", 50, "Higher is better", "0-100", "%", 91));
        s.addDimension(accessibility);

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Mean time between failures", 50, "Higher is better", "0-2000", "hours", 720));
        reliability.addMetric(new Metric("Failed login rate", 50, "Lower is better", "0-20", "%", 2));
        s.addDimension(reliability);

        Dimension functional = new Dimension("Functional Suitability", 15);
        functional.addMetric(new Metric("Learning objective coverage", 50, "Higher is better", "0-100", "%", 86));
        functional.addMetric(new Metric("Assessment rubric alignment", 50, "Higher is better", "0-100", "%", 79));
        s.addDimension(functional);

        return s;
    }

    private static Scenario educationScenarioDTeamBeta() {
        Scenario s = new Scenario("Education - Scenario D (Team Beta)");
        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score", 50, "Higher is better", "0-100", "points", 76));
        usability.addMetric(new Metric("Onboarding time", 50, "Lower is better", "0-60", "min", 12));
        s.addDimension(usability);

        Dimension performance = new Dimension("Performance Efficiency", 20);
        performance.addMetric(new Metric("Video start time", 50, "Lower is better", "0-15", "sec", 8));
        performance.addMetric(new Metric("Concurrent exams", 50, "Higher is better", "0-600", "users", 220));
        s.addDimension(performance);

        Dimension accessibility = new Dimension("Accessibility", 20);
        accessibility.addMetric(new Metric("WCAG conformance score", 50, "Higher is better", "0-100", "points", 72));
        accessibility.addMetric(new Metric("Keyboard-only task success", 50, "Higher is better", "0-100", "%", 68));
        s.addDimension(accessibility);

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Mean time between failures", 50, "Higher is better", "0-2000", "hours", 480));
        reliability.addMetric(new Metric("Failed login rate", 50, "Lower is better", "0-20", "%", 6));
        s.addDimension(reliability);

        Dimension functional = new Dimension("Functional Suitability", 15);
        functional.addMetric(new Metric("Learning objective coverage", 50, "Higher is better", "0-100", "%", 74));
        functional.addMetric(new Metric("Assessment rubric alignment", 50, "Higher is better", "0-100", "%", 70));
        s.addDimension(functional);

        return s;
    }

    private static Scenario healthScenarioAHospitalAdmin() {
        Scenario s = new Scenario("Health - Scenario A (Hospital Admin)");

        Dimension safety = new Dimension("Safety", 30);
        safety.addMetric(new Metric("Medication error incidents", 50, "Lower is better", "0-50", "count", 4));
        safety.addMetric(new Metric("Clinical checklist compliance", 50, "Higher is better", "0-100", "%", 93));
        s.addDimension(safety);

        Dimension reliability = new Dimension("Reliability", 25);
        reliability.addMetric(new Metric("EHR uptime", 50, "Higher is better", "90-100", "%", 99.2));
        reliability.addMetric(new Metric("Order transmission failures", 50, "Lower is better", "0-30", "count", 3));
        s.addDimension(reliability);

        Dimension usability = new Dimension("Usability", 20);
        usability.addMetric(new Metric("Nurse task time (medication)", 50, "Lower is better", "0-25", "min", 9));
        usability.addMetric(new Metric("SUS (admin module)", 50, "Higher is better", "0-100", "points", 71));
        s.addDimension(usability);

        Dimension performance = new Dimension("Performance Efficiency", 15);
        performance.addMetric(new Metric("Patient search latency", 50, "Lower is better", "0-800", "ms", 210));
        performance.addMetric(new Metric("Concurrent sessions", 50, "Higher is better", "0-500", "users", 180));
        s.addDimension(performance);

        Dimension functional = new Dimension("Functional Suitability", 10);
        functional.addMetric(new Metric("HL7 message validation pass rate", 50, "Higher is better", "0-100", "%", 97));
        functional.addMetric(new Metric("Duplicate patient merges pending", 50, "Lower is better", "0-50", "count", 6));
        s.addDimension(functional);

        return s;
    }

    private static Scenario healthScenarioBPatientPortal() {
        Scenario s = new Scenario("Health - Scenario B (Patient Portal)");

        Dimension security = new Dimension("Security & Privacy", 30);
        security.addMetric(new Metric("Failed authentication rate", 50, "Lower is better", "0-25", "%", 4));
        security.addMetric(new Metric("PHI exposure incidents", 50, "Lower is better", "0-10", "count", 0));
        s.addDimension(security);

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("Appointment booking success", 50, "Higher is better", "0-100", "%", 89));
        usability.addMetric(new Metric("Time to book appointment", 50, "Lower is better", "0-10", "min", 3));
        s.addDimension(usability);

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Portal availability", 50, "Higher is better", "90-100", "%", 98.5));
        reliability.addMetric(new Metric("Timeout errors per day", 50, "Lower is better", "0-100", "count", 12));
        s.addDimension(reliability);

        Dimension performance = new Dimension("Performance Efficiency", 15);
        performance.addMetric(new Metric("Lab results page load", 50, "Lower is better", "0-3000", "ms", 850));
        performance.addMetric(new Metric("Peak concurrent users", 50, "Higher is better", "0-5000", "users", 1200));
        s.addDimension(performance);

        Dimension functional = new Dimension("Functional Suitability", 10);
        functional.addMetric(new Metric("Feature completeness (backlog)", 50, "Higher is better", "0-100", "%", 82));
        functional.addMetric(new Metric("Open defects (P1/P2)", 50, "Lower is better", "0-40", "count", 5));
        s.addDimension(functional);

        return s;
    }

    /** Minimal custom template for demonstration. */
    private static Scenario customNewScenario() {
        Scenario s = new Scenario("Custom - New Scenario");
        Dimension d1 = new Dimension("Core Quality", 50);
        d1.addMetric(new Metric("Overall satisfaction", 50, "Higher is better", "0-100", "points", 75));
        d1.addMetric(new Metric("Defect density", 50, "Lower is better", "0-50", "defects/KLOC", 8));
        s.addDimension(d1);

        Dimension d2 = new Dimension("Delivery", 50);
        d2.addMetric(new Metric("On-time delivery rate", 50, "Higher is better", "0-100", "%", 88));
        d2.addMetric(new Metric("Cycle time", 50, "Lower is better", "0-90", "days", 21));
        s.addDimension(d2);

        return s;
    }
}
