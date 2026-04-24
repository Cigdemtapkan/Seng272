package view;

import controller.MainController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.Scenario;

public class MainFrame extends JFrame {
    public static final Color PRIMARY_COLOR = new Color(44, 62, 80);
    public static final Color ACCENT_COLOR = new Color(52, 152, 219);
    public static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    public static final Color PANEL_COLOR = Color.WHITE;
    public static final Color TEXT_COLOR = new Color(33, 37, 41);
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    public static final Color WARNING_COLOR = new Color(243, 156, 18);
    public static final Color DANGER_COLOR = new Color(231, 76, 60);

    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    private MainController controller;

    private ProfilePanel profilePanel;
    private DefinePanel definePanel;
    private PlanPanel planPanel;
    private CollectPanel collectPanel;
    private AnalysePanel analysePanel;
    private StepIndicatorPanel stepIndicator;
    private int currentStepIndex = 1;

    public MainFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Fall back to default look and feel if system LAF cannot be applied.
        }
        this.controller = new MainController(this);
        applyGlobalFont();

        setTitle("ISO 15939 Measurement Process Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setMinimumSize(new Dimension(1000, 800));
        setMaximumSize(new Dimension(1000, 800));
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        stepIndicator = new StepIndicatorPanel();
        add(stepIndicator, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(BACKGROUND_COLOR);

        profilePanel = new ProfilePanel(this);
        definePanel = new DefinePanel(this);
        planPanel = new PlanPanel(this);
        collectPanel = new CollectPanel(this);
        analysePanel = new AnalysePanel(this);

        mainContentPanel.add(profilePanel, "Step1");
        mainContentPanel.add(definePanel, "Step2");
        mainContentPanel.add(planPanel, "Step3");
        mainContentPanel.add(collectPanel, "Step4");
        mainContentPanel.add(analysePanel, "Step5");

        add(mainContentPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    public static void applyPanelTheme(JPanel panel) {
        panel.setBackground(PANEL_COLOR);
        panel.setForeground(TEXT_COLOR);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
    }

    public static JButton createModernButton(String text) {
        JButton button = new RoundedFillButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setForeground(Color.WHITE);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBackground(ACCENT_COLOR);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setMargin(new Insets(10, 16, 10, 16));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(41, 128, 185));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(ACCENT_COLOR);
            }
        });
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        JButton button = new RoundedFillButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setForeground(PRIMARY_COLOR);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBackground(new Color(236, 240, 241));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setMargin(new Insets(10, 16, 10, 16));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(220, 228, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(236, 240, 241));
            }
        });
        return button;
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setGridColor(new Color(220, 226, 231));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setSelectionBackground(new Color(221, 236, 248));
        table.setSelectionForeground(TEXT_COLOR);
        table.setBackground(PANEL_COLOR);
        table.setForeground(TEXT_COLOR);
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD, 13f));
        table.getTableHeader().setPreferredSize(new Dimension(0, 34));
    }

    private void applyGlobalFont() {
        Font uiFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 16);
        UIManager.put("Label.font", uiFont);
        UIManager.put("Button.font", uiFont);
        UIManager.put("TextField.font", uiFont);
        UIManager.put("ComboBox.font", uiFont);
        UIManager.put("Table.font", uiFont);
        UIManager.put("TableHeader.font", uiFont.deriveFont(Font.BOLD));
        UIManager.put("RadioButton.font", uiFont);
        UIManager.put("TitledBorder.font", titleFont);
    }

    private static class RoundedFillButton extends JButton {
        private RoundedFillButton(String text) {
            super(text);
            setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public void nextStep() {
        currentStepIndex++;
        if (currentStepIndex <= 5) {
            cardLayout.next(mainContentPanel);
            stepIndicator.setCurrentStep(currentStepIndex);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
        }
    }

    public void previousStep() {
        currentStepIndex--;
        if (currentStepIndex >= 1) {
            cardLayout.previous(mainContentPanel);
            stepIndicator.setCurrentStep(currentStepIndex);
            mainContentPanel.revalidate();
            mainContentPanel.repaint();
        } else {
            currentStepIndex = 1;
        }
    }

    // Bu metod DefinePanel'deki hatayı düzeltecek
    public void setSelectedScenario(Scenario scenario) {
        this.updateScenarioData(scenario);
    }

    public void updateScenarioData(Scenario scenario) {
        planPanel.loadScenarioData(scenario);
        collectPanel.loadScenarioData(scenario);
    }

    public MainController getController() { return controller; }
    public AnalysePanel getAnalysePanel() { return analysePanel; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}