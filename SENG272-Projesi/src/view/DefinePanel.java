package view;

import javax.swing.*;
import java.awt.*;

/**
 * Step 2: quality type, mode, and scenario (single-selection radio groups + dependent combo).
 */
public class DefinePanel extends JPanel {
    private final MainFrame mainFrame;

    private final JRadioButton rbProduct = new JRadioButton("Product Quality");
    private final JRadioButton rbProcess = new JRadioButton("Process Quality");
    private final ButtonGroup typeGroup = new ButtonGroup();

    private final JRadioButton rbCustom = new JRadioButton("Custom");
    private final JRadioButton rbHealth = new JRadioButton("Health");
    private final JRadioButton rbEducation = new JRadioButton("Education");
    private final ButtonGroup modeGroup = new ButtonGroup();

    private final JComboBox<String> cbScenarios = new JComboBox<>();
    private final JButton btnNext = MainFrame.createModernButton("Next Step");

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
        cbScenarios.setPreferredSize(new Dimension(360, 35));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        formPanel.add(cbScenarios, gbc);
        gbc.gridy++;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        formPanel.add(Box.createVerticalGlue(), gbc);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.add(formPanel, BorderLayout.NORTH);
        center.add(Box.createVerticalGlue(), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        rbEducation.addActionListener(e -> updateScenariosForMode("Education"));
        rbHealth.addActionListener(e -> updateScenariosForMode("Health"));
        rbCustom.addActionListener(e -> updateScenariosForMode("Custom"));

        rbProduct.setSelected(true);
        rbEducation.setSelected(true);
        updateScenariosForMode("Education");

        btnNext.addActionListener(e -> {
            if (typeGroup.getSelection() == null || modeGroup.getSelection() == null
                    || cbScenarios.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,
                        "Please select all options before proceeding.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedMode = rbEducation.isSelected() ? "Education"
                    : (rbHealth.isSelected() ? "Health" : "Custom");
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

    private void updateScenariosForMode(String mode) {
        cbScenarios.removeAllItems();
        if ("Education".equals(mode)) {
            cbScenarios.addItem("Scenario C - Team Alpha");
            cbScenarios.addItem("Scenario D - Team Beta");
        } else if ("Health".equals(mode)) {
            cbScenarios.addItem("Scenario A - Hospital Admin");
            cbScenarios.addItem("Scenario B - Patient Portal");
        } else if ("Custom".equals(mode)) {
            cbScenarios.addItem("New Custom Scenario");
        }
        if (cbScenarios.getItemCount() > 0) {
            cbScenarios.setSelectedIndex(0);
        }
    }
}
