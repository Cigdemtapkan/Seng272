package view;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private JTextField txtUsername = new JTextField(20);
    private JTextField txtSchool = new JTextField(20);
    private JTextField txtSession = new JTextField(20);
    private MainFrame mainFrame;

    public ProfilePanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridBagLayout());
        MainFrame.applyPanelTheme(this);
        setBackground(MainFrame.BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(createLabel("Username:"), gbc);
        gbc.gridy = 1;
        add(createLabel("School:"), gbc);
        gbc.gridy = 2;
        add(createLabel("Session Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(txtUsername, gbc);
        gbc.gridy = 1;
        add(txtSchool, gbc);
        gbc.gridy = 2;
        add(txtSession, gbc);

        styleField(txtUsername);
        styleField(txtSchool);
        styleField(txtSession);

        JButton btnNext = MainFrame.createModernButton("Next");
        btnNext.addActionListener(e -> validateAndProceed());
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(btnNext, gbc);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(MainFrame.TEXT_COLOR);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        return label;
    }

    private void styleField(JTextField field) {
        field.setPreferredSize(new Dimension(300, 35));
        field.setBackground(Color.WHITE);
        field.setForeground(MainFrame.TEXT_COLOR);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }

    private void validateAndProceed() {
        if (txtUsername.getText().isEmpty() || txtSchool.getText().isEmpty() || txtSession.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields must be filled in before proceeding to the next step. Please check your username, school, and session name.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            mainFrame.nextStep();
        }
    }
}
