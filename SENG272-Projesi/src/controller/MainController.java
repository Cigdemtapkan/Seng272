package controller;

import model.Dimension;
import model.Metric;
import model.Scenario;
import model.ScenarioRepository;
import model.User;
import view.CollectPanel;
import view.MainFrame;

/**
 * Application controller: profile persistence, scenario selection, and Step 5 aggregation.
 */
public class MainController {
    private final MainFrame view;
    private final User modelUser;
    private Scenario currentScenario;

    public MainController(MainFrame view) {
        this.view = view;
        this.modelUser = new User();
    }

    public void saveProfile(String name, String school, String session) {
        modelUser.setUsername(name);
        modelUser.setSchool(school);
        modelUser.setSessionName(session);
    }

    /**
     * Step 2: bind mode + scenario label to a fresh deep-copied {@link Scenario} for the wizard.
     */
    public void selectScenario(String mode, String scenarioName) {
        this.currentScenario = ScenarioRepository.createWorkingCopy(mode, scenarioName);
        view.updateScenarioData(currentScenario);
    }

    /**
     * Step 5: dimensionScore = sum(metricScore * metricCoefficient) / sum(metricCoefficient).
     */
    public void performFinalAnalysis() {
        CollectPanel collect = view.getCollectPanel();
        if (collect != null) {
            collect.syncAllRowsToModel();
        }
        if (currentScenario == null) {
            return;
        }

        for (Dimension dim : currentScenario.getDimensions()) {
            double totalWeight = 0;
            double weightedSum = 0;

            for (Metric m : dim.getMetrics()) {
                weightedSum += m.getCalculatedScore() * m.getCoefficient();
                totalWeight += m.getCoefficient();
            }

            if (totalWeight > 0) {
                dim.setFinalScore(weightedSum / totalWeight);
            } else {
                dim.setFinalScore(0);
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
