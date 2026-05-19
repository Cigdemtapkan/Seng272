package view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

/**
 * Step 5: weighted dimension scores, radar chart, gap analysis (English UI).
 */
public class AnalysePanel extends JPanel {
    private static final String GAP_SENTENCE =
            "This dimension has the lowest score and requires the most improvement.";

    private final MainFrame mainFrame;
    private final JPanel resultsPanel;
    private final JPanel gapPanel;
    private final JLabel lblLowestDimension;
    private final JLabel lblGapValue;
    private final JLabel lblQualityLevel;
    private final JTextArea txtGapNarrative;
    private List<model.Dimension> latestDimensions;
    private final RadarChartPanel radarChartPanel;
    private final JSplitPane splitGapRadar;

    public AnalysePanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout(12, 12));
        setBackground(MainFrame.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(220, 226, 231), 1, true),
                "Dimension Scores"
        ));

        JScrollPane scoreScrollPane = new JScrollPane(resultsPanel);
        scoreScrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 226, 231)));
        scoreScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scoreScrollPane.setMinimumSize(new Dimension(100, 120));

        gapPanel = new JPanel(new GridBagLayout());
        gapPanel.setBackground(Color.WHITE);
        gapPanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(220, 226, 231), 1, true),
                "Gap Analysis Result"
        ));

        lblLowestDimension = createGapLabel("Lowest Dimension: -");
        lblGapValue = createGapLabel("Gap Value: -");
        lblQualityLevel = createGapLabel("Quality Level: -");

        txtGapNarrative = new JTextArea();
        txtGapNarrative.setEditable(false);
        txtGapNarrative.setOpaque(false);
        txtGapNarrative.setBackground(new Color(0, 0, 0, 0));
        txtGapNarrative.setLineWrap(true);
        txtGapNarrative.setWrapStyleWord(true);
        txtGapNarrative.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtGapNarrative.setForeground(MainFrame.TEXT_COLOR);
        txtGapNarrative.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        txtGapNarrative.setRows(0);
        txtGapNarrative.setColumns(0);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(6, 8, 4, 8);
        gbc.gridy = 0;
        gapPanel.add(lblLowestDimension, gbc);
        gbc.gridy = 1;
        gapPanel.add(lblGapValue, gbc);
        gbc.gridy = 2;
        gapPanel.add(lblQualityLevel, gbc);

        gbc.gridy = 3;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(8, 8, 8, 8);
        gapPanel.add(txtGapNarrative, gbc);

        gapPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                layoutGapNarrative();
            }
        });

        radarChartPanel = new RadarChartPanel();

        splitGapRadar = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gapPanel, radarChartPanel);
        splitGapRadar.setResizeWeight(0.52);
        splitGapRadar.setContinuousLayout(true);
        splitGapRadar.setOneTouchExpandable(true);
        splitGapRadar.setBorder(BorderFactory.createEmptyBorder());

        JPanel centerStack = new JPanel(new BorderLayout(0, 10));
        centerStack.setOpaque(false);
        centerStack.add(splitGapRadar, BorderLayout.CENTER);

        add(scoreScrollPane, BorderLayout.NORTH);
        add(centerStack, BorderLayout.CENTER);

        JButton btnCalculate = MainFrame.createModernButton("Calculate Gap Analysis");
        btnCalculate.addActionListener(e -> calculateGapAnalysis());
        JButton btnPrevious = MainFrame.createSecondaryButton("Previous Step");
        btnPrevious.addActionListener(e -> mainFrame.previousStep());
        JButton btnExit = MainFrame.createModernButton("Exit Application");
        btnExit.addActionListener(e -> System.exit(0));
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        footer.setOpaque(false);
        footer.add(btnPrevious);
        footer.add(btnCalculate);
        footer.add(btnExit);
        add(footer, BorderLayout.SOUTH);
    }

    private void layoutGapNarrative() {
        int w = txtGapNarrative.getParent() != null
                ? Math.max(80, txtGapNarrative.getParent().getWidth() - 24)
                : 400;
        txtGapNarrative.setSize(new Dimension(w, 10_000));
        int h = txtGapNarrative.getPreferredSize().height + 8;
        txtGapNarrative.setPreferredSize(new Dimension(w, Math.max(h, 72)));
        txtGapNarrative.setMinimumSize(new Dimension(w, Math.max(h, 72)));
        gapPanel.revalidate();
    }

    public void clearForNewScenario() {
        latestDimensions = null;
        resultsPanel.removeAll();
        resultsPanel.revalidate();
        resultsPanel.repaint();
        lblLowestDimension.setForeground(MainFrame.TEXT_COLOR);
        lblGapValue.setForeground(MainFrame.TEXT_COLOR);
        lblQualityLevel.setForeground(MainFrame.TEXT_COLOR);
        lblLowestDimension.setText("Lowest Dimension: -");
        lblGapValue.setText("Gap Value: -");
        lblQualityLevel.setText("Quality Level: -");
        txtGapNarrative.setText("");
        radarChartPanel.setDimensions(null);
        SwingUtilities.invokeLater(this::layoutGapNarrative);
    }

    public void updateAnalysis(List<model.Dimension> dimensions) {
        latestDimensions = dimensions;
        resultsPanel.removeAll();

        for (model.Dimension dim : dimensions) {
            double dimScore = dim.getFinalScore();
            resultsPanel.add(createScoreRow(dim.getName(), dimScore));
        }
        radarChartPanel.setDimensions(dimensions);
        calculateGapAnalysis();
        resultsPanel.revalidate();
        resultsPanel.repaint();
        revalidate();
        repaint();
        SwingUtilities.invokeLater(() -> {
            splitGapRadar.setDividerLocation(0.52);
            layoutGapNarrative();
        });
    }

    private JLabel createGapLabel(String text) {
        JLabel label = new JLabel("<html>" + text + "</html>");
        label.setForeground(MainFrame.TEXT_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    private JPanel createScoreRow(String dimensionName, double score) {
        JPanel row = new JPanel(new BorderLayout(10, 6));
        row.setOpaque(false);
        row.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        JLabel label = new JLabel(dimensionName + "  (" + String.format("%.2f", score) + ")");
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(MainFrame.PRIMARY_COLOR);

        JProgressBar scoreBar = new JProgressBar(0, 500);
        scoreBar.setValue((int) Math.round(Math.max(0, Math.min(5, score)) * 100));
        scoreBar.setStringPainted(true);
        scoreBar.setString(String.format("%.2f / 5.00", score));
        scoreBar.setForeground(score < 3.0 ? MainFrame.DANGER_COLOR : MainFrame.SUCCESS_COLOR);
        scoreBar.setBackground(new Color(233, 236, 239));

        JLabel qualityBadge = new JLabel(getQualityLevel(score));
        qualityBadge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        qualityBadge.setForeground(Color.WHITE);
        qualityBadge.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        qualityBadge.setOpaque(true);
        qualityBadge.setBackground(getQualityColor(score));

        row.add(label, BorderLayout.NORTH);
        row.add(scoreBar, BorderLayout.CENTER);
        row.add(qualityBadge, BorderLayout.EAST);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        int ph = row.getPreferredSize().height;
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, Math.max(ph, 52)));
        return row;
    }

    private void calculateGapAnalysis() {
        if (latestDimensions == null || latestDimensions.isEmpty()) {
            lblLowestDimension.setText("Lowest Dimension: No analysis data available.");
            lblGapValue.setText("Gap Value: -");
            lblQualityLevel.setText("Quality Level: -");
            txtGapNarrative.setText("");
            gapPanel.setBorder(BorderFactory.createTitledBorder(
                    new LineBorder(new Color(220, 226, 231), 1, true),
                    "Gap Analysis Result"
            ));
            SwingUtilities.invokeLater(this::layoutGapNarrative);
            return;
        }

        model.Dimension lowestDim = null;
        double minScore = Double.MAX_VALUE;
        for (model.Dimension dim : latestDimensions) {
            if (dim.getFinalScore() < minScore) {
                minScore = dim.getFinalScore();
                lowestDim = dim;
            }
        }

        if (lowestDim != null) {
            double gap = 5.0 - minScore;
            String level = getQualityLevel(minScore);
            lblLowestDimension.setText("Name: " + lowestDim.getName());
            lblGapValue.setText("Score: " + String.format("%.2f", minScore));
            lblQualityLevel.setText("Gap value: " + String.format("%.2f", gap)
                    + "   |   Quality level: " + level);

            lblLowestDimension.setForeground(MainFrame.WARNING_COLOR);

            String narrative = "Dimension: " + lowestDim.getName() + "\n"
                    + "Score: " + String.format("%.2f", minScore) + "\n"
                    + "Gap value (5.00 - score): " + String.format("%.2f", gap) + "\n"
                    + "Quality level: " + level + "\n\n"
                    + GAP_SENTENCE;
            txtGapNarrative.setText(narrative);

            gapPanel.setBorder(BorderFactory.createTitledBorder(
                    new LineBorder(getQualityColor(minScore), 2, true),
                    "Gap Analysis Result"
            ));
        }
        radarChartPanel.repaint();
        SwingUtilities.invokeLater(this::layoutGapNarrative);
    }

    private String getQualityLevel(double score) {
        if (score >= 4.5) {
            return "Excellent";
        }
        if (score >= 3.5) {
            return "Good";
        }
        if (score >= 2.5) {
            return "Needs Improvement";
        }
        return "Poor";
    }

    private Color getQualityColor(double score) {
        if (score >= 3.5) {
            return MainFrame.SUCCESS_COLOR;
        }
        if (score >= 2.5) {
            return MainFrame.WARNING_COLOR;
        }
        return MainFrame.DANGER_COLOR;
    }

    private static class RadarChartPanel extends JPanel {
        private List<model.Dimension> dimensions;

        private RadarChartPanel() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createTitledBorder(
                    new LineBorder(new Color(220, 226, 231), 1, true),
                    "Radar Chart (dimension scores)"
            ));
            setMinimumSize(new Dimension(200, 200));
        }

        private void setDimensions(List<model.Dimension> dimensions) {
            this.dimensions = dimensions;
            repaint();
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(200, 200);
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension d = super.getPreferredSize();
            return new Dimension(Math.max(280, d.width), Math.max(280, d.height));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int cx = width / 2;
            int cy = height / 2 + 10;
            int radius = Math.max(40, Math.min(width, height) / 3);

            for (int ring = 1; ring <= 5; ring++) {
                int r = (radius * ring) / 5;
                g2.setColor(new Color(230, 234, 238));
                g2.drawOval(cx - r, cy - r, r * 2, r * 2);
            }

            if (dimensions == null || dimensions.isEmpty()) {
                g2.setColor(MainFrame.TEXT_COLOR);
                g2.drawString("No data available.", Math.max(8, cx - 45), cy);
                g2.dispose();
                return;
            }

            int n = dimensions.size();
            Polygon polygon = new Polygon();
            for (int i = 0; i < n; i++) {
                double angle = -Math.PI / 2 + (2 * Math.PI * i / n);
                int ax = (int) (cx + radius * Math.cos(angle));
                int ay = (int) (cy + radius * Math.sin(angle));
                g2.setColor(new Color(210, 216, 222));
                g2.drawLine(cx, cy, ax, ay);

                String label = dimensions.get(i).getName();
                g2.setColor(MainFrame.PRIMARY_COLOR);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                int lx = (int) (cx + (radius + 12) * Math.cos(angle));
                int ly = (int) (cy + (radius + 12) * Math.sin(angle));
                g2.drawString(label, lx, ly);

                double score = dimensions.get(i).getFinalScore();
                score = Math.max(1.0, Math.min(5.0, score));
                int pr = (int) ((score / 5.0) * radius);
                int px = (int) (cx + pr * Math.cos(angle));
                int py = (int) (cy + pr * Math.sin(angle));
                polygon.addPoint(px, py);
            }

            g2.setColor(new Color(52, 152, 219, 90));
            g2.fillPolygon(polygon);
            g2.setColor(MainFrame.ACCENT_COLOR);
            g2.setStroke(new BasicStroke(2f));
            g2.drawPolygon(polygon);

            g2.dispose();
        }
    }
}
