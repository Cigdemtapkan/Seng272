package view;

import model.Dimension;
import model.Metric;
import model.Scenario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlanPanel extends JPanel {
    private MainFrame mainFrame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblScenarioInfo;

    public PlanPanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout(10, 10));
        MainFrame.applyPanelTheme(this);
        setBackground(MainFrame.BACKGROUND_COLOR);

        lblScenarioInfo = new JLabel("Selected Scenario: -");
        lblScenarioInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblScenarioInfo.setForeground(MainFrame.PRIMARY_COLOR);
        add(lblScenarioInfo, BorderLayout.NORTH);

        // 3b. Tablo Yapılandırması (ISO 15939 Planlama Aşaması)
        String[] columns = {"Dimension", "Metric", "Coeff", "Direction", "Range", "Unit"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Ödev Kuralı: Step 3 tablosu sadece gösterim amaçlıdır, düzenlenemez.
                return false;
            }
        };

        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false); // Sütunların yerinin değiştirilmesini engeller
        MainFrame.styleTable(table);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnPrevious = MainFrame.createSecondaryButton("Previous Step");
        btnPrevious.addActionListener(e -> mainFrame.previousStep());
        JButton btnNext = MainFrame.createModernButton("Next Step (Collect Data)");
        btnNext.addActionListener(e -> mainFrame.nextStep());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(MainFrame.BACKGROUND_COLOR);
        buttonPanel.add(btnPrevious);
        buttonPanel.add(btnNext);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * DefinePanel'de seçilen senaryo verilerini tabloya doldurur.
     * @param scenario Seçilen senaryo nesnesi
     */
    public void loadScenarioData(Scenario scenario) {
        if (scenario == null) return;

        lblScenarioInfo.setText("Selected Scenario: " + scenario.getName());
        tableModel.setRowCount(0); // Tabloyu temizle

        for (Dimension dim : scenario.getDimensions()) {
            for (Metric m : dim.getMetrics()) {
                tableModel.addRow(new Object[]{
                        dim.getName() + " (" + dim.getCoefficient() + ")",
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
