package view;

import javax.swing.*;
import java.awt.*;

public class DefinePanel extends JPanel {
    private MainFrame mainFrame;

    // Kalite Tipi Seçimi (Product/Process) - Sadece bir seçenek seçilebilir [cite: 34, 35, 36]
    private JRadioButton rbProduct = new JRadioButton("Product Quality");
    private JRadioButton rbProcess = new JRadioButton("Process Quality");
    private ButtonGroup typeGroup = new ButtonGroup();

    // Mod Seçimi (Custom/Health/Education) - Sadece bir seçenek seçilebilir [cite: 38, 39, 41]
    private JRadioButton rbCustom = new JRadioButton("Custom");
    private JRadioButton rbHealth = new JRadioButton("Health");
    private JRadioButton rbEducation = new JRadioButton("Education");
    private ButtonGroup modeGroup = new ButtonGroup();

    // Senaryo Seçimi - Seçilen moda göre içeriği değişecek [cite: 42, 43]
    private JComboBox<String> cbScenarios = new JComboBox<>();

    // Eksik olan butonu tanımlıyoruz
    private JButton btnNext = MainFrame.createModernButton("Next Step");

    public DefinePanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout(12, 12));
        MainFrame.applyPanelTheme(this);
        setBackground(MainFrame.BACKGROUND_COLOR);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(MainFrame.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(createSectionLabel("2a. Select Quality Type:"), gbc);
        gbc.gridy++;
        typeGroup.add(rbProduct);
        typeGroup.add(rbProcess);
        styleRadio(rbProduct);
        styleRadio(rbProcess);
        formPanel.add(rbProduct, gbc);
        gbc.gridy++;
        formPanel.add(rbProcess, gbc);
        gbc.gridy++;
        formPanel.add(Box.createVerticalStrut(10), gbc);
        gbc.gridy++;

        formPanel.add(createSectionLabel("2b. Select Mode:"), gbc);
        gbc.gridy++;
        modeGroup.add(rbCustom);
        modeGroup.add(rbHealth);
        modeGroup.add(rbEducation);
        styleRadio(rbCustom);
        styleRadio(rbHealth);
        styleRadio(rbEducation);
        formPanel.add(rbCustom, gbc);
        gbc.gridy++;
        formPanel.add(rbHealth, gbc);
        gbc.gridy++;
        formPanel.add(rbEducation, gbc);
        gbc.gridy++;
        formPanel.add(Box.createVerticalStrut(10), gbc);
        gbc.gridy++;

        formPanel.add(createSectionLabel("2c. Select Scenario:"), gbc);
        gbc.gridy++;
        cbScenarios.setBackground(Color.WHITE);
        cbScenarios.setForeground(MainFrame.TEXT_COLOR);
        cbScenarios.setPreferredSize(new java.awt.Dimension(300, 35));
        cbScenarios.setMaximumSize(new java.awt.Dimension(300, 35));
        formPanel.add(cbScenarios, gbc);
        add(formPanel, BorderLayout.NORTH);

        // Event Listeners: Mod değişince senaryo listesini güncelle [cite: 44, 46]
        rbEducation.addActionListener(e -> updateScenarios("Education"));
        rbHealth.addActionListener(e -> updateScenarios("Health"));
        rbCustom.addActionListener(e -> updateScenarios("Custom"));

        // "Next" Butonu İşlemleri
        btnNext.addActionListener(e -> {
            // Hiçbir şey seçilmemişse uyarı ver
            if (typeGroup.getSelection() == null || modeGroup.getSelection() == null || cbScenarios.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please select all options before proceeding.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedMode = rbEducation.isSelected() ? "Education" : (rbHealth.isSelected() ? "Health" : "Custom");
            String selectedScenario = cbScenarios.getSelectedItem().toString();
            mainFrame.getController().selectScenario(selectedMode, selectedScenario);
            mainFrame.nextStep();
        });

        JButton btnPrevious = MainFrame.createSecondaryButton("Previous Step");
        btnPrevious.addActionListener(e -> mainFrame.previousStep());

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        footerPanel.setBackground(MainFrame.BACKGROUND_COLOR);
        footerPanel.add(btnPrevious);
        footerPanel.add(btnNext);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(MainFrame.PRIMARY_COLOR);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
        return label;
    }

    private void styleRadio(JRadioButton radio) {
        radio.setBackground(MainFrame.BACKGROUND_COLOR);
        radio.setForeground(MainFrame.TEXT_COLOR);
        radio.setFocusPainted(false);
    }

    // Seçilen moda göre JComboBox içeriğini güncelleyen metod [cite: 44, 46]
    private void updateScenarios(String mode) {
        cbScenarios.removeAllItems();
        if (mode.equals("Education")) {
            cbScenarios.addItem("Scenario C - Team Alpha");
            cbScenarios.addItem("Scenario D - Team Beta");
        } else if (mode.equals("Health")) {
            cbScenarios.addItem("Scenario A - Hospital Admin");
            cbScenarios.addItem("Scenario B - Patient Portal");
        } else if (mode.equals("Custom")) {
            cbScenarios.addItem("New Custom Scenario");
        }
    }
}
