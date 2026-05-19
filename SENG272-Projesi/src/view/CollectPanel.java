package view;

import model.Metric;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Step 4: raw values, automatic 1–5 scores, coeff/unit display.
 */
public class CollectPanel extends JPanel {
    private static final int COL_METRIC = 0;
    private static final int COL_DIRECTION = 1;
    private static final int COL_RANGE = 2;
    private static final int COL_VALUE = 3;
    private static final int COL_SCORE = 4;
    private static final int COL_COEFF_UNIT = 5;

    private final MainFrame mainFrame;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final List<Metric> rowMetrics = new ArrayList<>();
    private boolean updatingTable;

    public CollectPanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout(10, 10));
        setBackground(MainFrame.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JLabel title = new JLabel("Step 4: Collect measurement data (edit Value as needed).");
        title.setForeground(MainFrame.PRIMARY_COLOR);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        String[] columns = {"Metric", "Direction", "Range", "Value", "Score (1-5)", "Coeff / Unit"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == COL_VALUE;
            }
        };
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        MainFrame.styleTable(table);
        table.setRowHeight(30);

        tableModel.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == COL_VALUE && !updatingTable) {
                recalculateRowScore(e.getFirstRow());
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 226, 231)));
        scroll.getViewport().setBackground(MainFrame.PANEL_COLOR);

        JPanel north = new JPanel(new BorderLayout());
        north.setOpaque(false);
        north.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(scroll, BorderLayout.CENTER);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        JButton btnPrevious = MainFrame.createSecondaryButton("Previous Step");
        btnPrevious.addActionListener(e -> mainFrame.previousStep());
        JButton btnAnalyse = MainFrame.createModernButton("Analyse Results");
        btnAnalyse.addActionListener(e -> {
            mainFrame.getController().performFinalAnalysis();
            mainFrame.nextStep();
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnPrevious);
        buttonPanel.add(btnAnalyse);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void loadScenarioData(model.Scenario scenario) {
        rowMetrics.clear();
        tableModel.setRowCount(0);
        if (scenario == null) {
            return;
        }
        for (model.Dimension dim : scenario.getDimensions()) {
            for (Metric m : dim.getMetrics()) {
                rowMetrics.add(m);
                String coeffUnit = m.getCoefficient() + " / " + m.getUnit();
                String valueText = formatDefaultSample(m);
                tableModel.addRow(new Object[]{
                        m.getName(),
                        m.getDirection(),
                        m.getRange(),
                        valueText,
                        "-",
                        coeffUnit
                });
            }
        }
        for (int i = 0; i < rowMetrics.size(); i++) {
            recalculateRowScore(i);
        }
    }

    public void syncAllRowsToModel() {
        for (int i = 0; i < rowMetrics.size(); i++) {
            recalculateRowScore(i);
        }
    }

    private String formatDefaultSample(Metric m) {
        double d = m.getDefaultSampleValue();
        if (!Double.isNaN(d)) {
            return formatDoubleForCell(d);
        }
        try {
            String[] parts = m.getRange().split("-");
            double min = Double.parseDouble(parts[0].trim());
            double max = Double.parseDouble(parts[1].trim());
            return formatDoubleForCell((min + max) / 2.0);
        } catch (Exception ex) {
            return "";
        }
    }

    private static String formatDoubleForCell(double v) {
        if (Double.isNaN(v) || Double.isInfinite(v)) {
            return "";
        }
        if (Math.abs(v - Math.rint(v)) < 1e-6) {
            return String.valueOf((long) Math.rint(v));
        }
        return String.valueOf(v);
    }

    private void recalculateRowScore(int row) {
        if (row < 0 || row >= rowMetrics.size()) {
            return;
        }

        Object valueObj = tableModel.getValueAt(row, COL_VALUE);
        String valueText = valueObj == null ? "" : valueObj.toString().trim();
        Metric metric = rowMetrics.get(row);
        if (valueText.isEmpty()) {
            metric.clearScore();
            updatingTable = true;
            tableModel.setValueAt("-", row, COL_SCORE);
            updatingTable = false;
            return;
        }

        try {
            double value = Double.parseDouble(valueText);
            double score = metric.calculateScore(value);
            updatingTable = true;
            tableModel.setValueAt(String.format("%.1f", score), row, COL_SCORE);
            updatingTable = false;
        } catch (NumberFormatException ex) {
            metric.clearScore();
            updatingTable = true;
            tableModel.setValueAt("Invalid", row, COL_SCORE);
            updatingTable = false;
        }
    }
}
