package view;

import javax.swing.*;
import java.awt.*;

public class StepIndicatorPanel extends JPanel {
    private String[] steps = {"Profile", "Define", "Plan", "Collect", "Analyse"};
    private int currentStep = 1;

    public StepIndicatorPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        MainFrame.applyPanelTheme(this);
        setBackground(MainFrame.PRIMARY_COLOR);
        setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        updateLabels();
    }

    public void setCurrentStep(int step) {
        this.currentStep = step;
        updateLabels();
    }

    private void updateLabels() {
        removeAll();
        for (int i = 0; i < steps.length; i++) { // .size() değil .length olmalı
            String text = (i + 1) + ". " + steps[i];

            JLabel label = new JLabel(text);
            label.setForeground(new Color(160, 181, 201));
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            if (i + 1 == currentStep) {
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setForeground(MainFrame.ACCENT_COLOR);
            }

            add(label);

            if (i < steps.length - 1) {
                JLabel arrow = new JLabel("  \u2192  ");
                arrow.setFont(new Font("Segoe UI", Font.BOLD, 14));
                arrow.setForeground(MainFrame.ACCENT_COLOR);
                add(arrow);
            }
        }
        revalidate();
        repaint();
    }
}
