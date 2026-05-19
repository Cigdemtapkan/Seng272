package view;

import model.Dimension;
import model.Metric;
import model.Scenario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Step 3: read-only metric plan. Dimension and Metric are separate columns (no HTML overlap).
 */
public class PlanPanel extends JPanel {
    private final MainFrame mainFrame;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JLabel lblScenarioInfo;

    public PlanPanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout(10, 10));
        setBackground(MainFrame.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        lblScenarioInfo = new JLabel("Selected Scenario: -");
        lblScenarioInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblScenarioInfo.setForeground(MainFrame.PRIMARY_COLOR);
        lblScenarioInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        String[] columns = {"Dimension", "Metric", "Coefficient", "Direction", "Range", "Unit"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        MainFrame.styleTable(table);
        table.setRowHeight(30);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 226, 231)));
        scroll.getViewport().setBackground(MainFrame.PANEL_COLOR);

        JPanel north = new JPanel(new BorderLayout());
        north.setOpaque(false);
        north.add(lblScenarioInfo, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(scroll, BorderLayout.CENTER);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        JButton btnPrevious = MainFrame.createSecondaryButton("Previous Step");
        btnPrevious.addActionListener(e -> mainFrame.previousStep());
        JButton btnNext = MainFrame.createModernButton("Next Step");
        btnNext.addActionListener(e -> mainFrame.nextStep());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnPrevious);
        buttonPanel.add(btnNext);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void loadScenarioData(Scenario scenario) {
        if (scenario == null) {
            return;
        }

        lblScenarioInfo.setText("Selected Scenario: " + scenario.getName());
        tableModel.setRowCount(0);

        for (Dimension dim : scenario.getDimensions()) {
            String dimLabel = dim.getName() + " (" + dim.getCoefficient() + ")";
            for (Metric m : dim.getMetrics()) {
                tableModel.addRow(new Object[]{
                        dimLabel,
                        m.getName(),
                        m.getCoefficient(),
                        m.getDirection(),
                        m.getRange(),
                        m.getUnit()
                });
            }
        }
    }
}
