package controller;

import model.Dimension;
import model.Metric;
import model.Scenario;
import model.User;
import view.MainFrame;

public class MainController {
    private MainFrame view;
    private User modelUser;
    private Scenario currentScenario;

    public MainController(MainFrame view) {
        this.view = view;
        this.modelUser = new User();
    }

    // Step 1: Kullanıcı bilgilerini kaydet
    public void saveProfile(String name, String school, String session) {
        modelUser.setUsername(name);
        modelUser.setSchool(school);
        modelUser.setSessionName(session);
    }

    // Step 2: Senaryo seçimini yönet
    public void selectScenario(String mode, String scenarioName) {
        if (mode.equals("Education")) {
            if (scenarioName.contains("Scenario C")) {
                this.currentScenario = Scenario.getEducationScenarioC();
            } else {
                // Diğer senaryolar için varsayılan bir yapı (veya Scenario D metodu)
                this.currentScenario = Scenario.getEducationScenarioC();
            }
        } else if (mode.equals("Health")) {
            
            this.currentScenario = Scenario.getEducationScenarioC(); 
        }

        if (currentScenario != null) {
            view.updateScenarioData(currentScenario);
        }
    }

    // Step 5: ISO 15939 Ağırlıklı Ortalama Hesaplaması
    public void performFinalAnalysis() {
        if (currentScenario == null) return;

        for (Dimension dim : currentScenario.getDimensions()) {
            double totalWeight = 0;
            double weightedSum = 0;

            for (Metric m : dim.getMetrics()) {
                // Formül: (Puan * Katsayı) toplamı / Toplam Katsayı
                weightedSum += (m.getCalculatedScore() * m.getCoefficient());
                totalWeight += m.getCoefficient();
            }

            if (totalWeight > 0) {
                
                dim.setFinalScore(weightedSum / totalWeight);
            }
        }

        
        if (view.getAnalysePanel() != null) {
            view.getAnalysePanel().updateAnalysis(currentScenario.getDimensions());
        }
    }

    public void handleNextStep() {
        view.nextStep();
    }

    public Scenario getCurrentScenario() {
        return currentScenario;
    }
}
