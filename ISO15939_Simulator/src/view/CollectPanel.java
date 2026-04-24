package view;

import model.Dimension;
import model.Metric;
import model.Scenario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollectPanel extends JPanel {
    private MainFrame mainFrame;
    private JTable table;
    private DefaultTableModel tableModel;
    private final List<Metric> rowMetrics = new ArrayList<>();
    private boolean updatingTable = false;

    public CollectPanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout(10, 10));
        MainFrame.applyPanelTheme(this);
        setBackground(MainFrame.BACKGROUND_COLOR);

        JLabel title = new JLabel("Step 4: Enter values for metrics.");
        title.setForeground(MainFrame.PRIMARY_COLOR);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Metric", "Direction", "Value", "Score", "Unit"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        table = new JTable(tableModel);
        MainFrame.styleTable(table);
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        tableModel.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 2 && !updatingTable) {
                recalculateRowScore(e.getFirstRow());
            }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnPrevious = MainFrame.createSecondaryButton("Previous Step");
        btnPrevious.addActionListener(e -> mainFrame.previousStep());
        JButton btnAnalyse = MainFrame.createModernButton("Analyse Results");
        btnAnalyse.addActionListener(e -> {
            mainFrame.getController().performFinalAnalysis();
            mainFrame.nextStep();
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(MainFrame.BACKGROUND_COLOR);
        buttonPanel.add(btnPrevious);
        buttonPanel.add(btnAnalyse);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void loadScenarioData(Scenario scenario) {
        rowMetrics.clear();
        tableModel.setRowCount(0);
        if (scenario == null) return;
        for (Dimension dim : scenario.getDimensions()) {
            for (Metric m : dim.getMetrics()) {
                rowMetrics.add(m);
                tableModel.addRow(new Object[]{m.getName(), m.getDirection(), "", "-", m.getUnit()});
            }
        }
    }

    private void recalculateRowScore(int row) {
        if (row < 0 || row >= rowMetrics.size()) {
            return;
        }

        Object valueObj = tableModel.getValueAt(row, 2);
        String valueText = valueObj == null ? "" : valueObj.toString().trim();
        if (valueText.isEmpty()) {
            updatingTable = true;
            tableModel.setValueAt("-", row, 3);
            updatingTable = false;
            return;
        }

        try {
            double value = Double.parseDouble(valueText);
            Metric metric = rowMetrics.get(row);
            // Formula in Metric follows: 1 + (value - min)/(max - min) * 4.
            double score = metric.calculateScore(value);
            updatingTable = true;
            tableModel.setValueAt(String.format("%.1f", score), row, 3);
            updatingTable = false;
        } catch (NumberFormatException ex) {
            updatingTable = true;
            tableModel.setValueAt("Invalid", row, 3);
            updatingTable = false;
        }
    }
}
