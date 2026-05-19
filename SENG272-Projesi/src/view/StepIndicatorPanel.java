package view;

import javax.swing.*;
import java.awt.*;

/**
 * Wizard step strip inside a horizontal scroll for narrow windows.
 */
public class StepIndicatorPanel extends JPanel {
    private static final String[] STEP_LABELS = {"Profile", "Define", "Plan", "Collect", "Analyse"};
    private int currentStep = 1;
    private final JPanel stepsHost;

    public StepIndicatorPanel() {
        setLayout(new BorderLayout());
        setBackground(MainFrame.PRIMARY_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));

        stepsHost = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 10));
        stepsHost.setOpaque(false);

        JScrollPane scroll = new JScrollPane(stepsHost);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        rebuild();
    }

    public void setCurrentStep(int step) {
        this.currentStep = Math.max(1, Math.min(STEP_LABELS.length, step));
        rebuild();
    }

    private void rebuild() {
        stepsHost.removeAll();
        Color inactive = new Color(160, 181, 201);
        Color arrowColor = new Color(120, 140, 160);

        for (int i = 0; i < STEP_LABELS.length; i++) {
            int stepNo = i + 1;
            boolean completed = stepNo < currentStep;
            boolean active = stepNo == currentStep;

            String prefix = completed ? "\u2713 " : "";
            String text = prefix + stepNo + ". " + STEP_LABELS[i];

            JLabel label = new JLabel(text);
            label.setOpaque(false);

            if (active) {
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                label.setForeground(MainFrame.ACCENT_COLOR);
            } else if (completed) {
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label.setForeground(new Color(180, 220, 200));
            } else {
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label.setForeground(inactive);
            }

            stepsHost.add(label);

            if (i < STEP_LABELS.length - 1) {
                JLabel arrow = new JLabel("  \u2192  ");
                arrow.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                arrow.setForeground(arrowColor);
                stepsHost.add(arrow);
            }
        }
        stepsHost.revalidate();
        stepsHost.repaint();
    }
}
