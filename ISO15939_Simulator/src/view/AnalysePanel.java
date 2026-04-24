package view;

import model.Dimension;
import model.Metric;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class AnalysePanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel resultsPanel;
    private JPanel gapPanel;
    private JLabel lblLowestDimension;
    private JLabel lblGapValue;
    private JLabel lblQualityLevel;
    private List<Dimension> latestDimensions;
    private RadarChartPanel radarChartPanel;

    public AnalysePanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout(15, 15));
        MainFrame.applyPanelTheme(this);
        setBackground(MainFrame.BACKGROUND_COLOR);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(220, 226, 231), 1, true),
                "Dimension Scores"
        ));
        JScrollPane scoreScrollPane = new JScrollPane(resultsPanel);
        scoreScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scoreScrollPane, BorderLayout.NORTH);

        gapPanel = new JPanel(new GridBagLayout());
        gapPanel.setBackground(Color.WHITE);
        gapPanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(220, 226, 231), 1, true),
                "Gap Analysis Result"
        ));
        lblLowestDimension = createGapLabel("Lowest Dimension: -");
        lblGapValue = createGapLabel("Gap Value: -");
        lblQualityLevel = createGapLabel("Quality Level: -");
        addGapRow(0, lblLowestDimension);
        addGapRow(1, lblGapValue);
        addGapRow(2, lblQualityLevel);
        radarChartPanel = new RadarChartPanel();

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        centerPanel.setBackground(MainFrame.BACKGROUND_COLOR);
        centerPanel.add(gapPanel);
        centerPanel.add(radarChartPanel);
        add(centerPanel, BorderLayout.CENTER);

        JButton btnCalculate = MainFrame.createModernButton("Gap Analizini Hesapla");
        btnCalculate.addActionListener(e -> calculateGapAnalysis());
        JButton btnPrevious = MainFrame.createSecondaryButton("Previous Step");
        btnPrevious.addActionListener(e -> mainFrame.previousStep());
        JButton btnExit = MainFrame.createModernButton("Uygulamadan Çık");
        btnExit.addActionListener(e -> System.exit(0));
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        footer.setBackground(MainFrame.BACKGROUND_COLOR);
        footer.add(btnPrevious);
        footer.add(btnCalculate);
        footer.add(btnExit);
        add(footer, BorderLayout.SOUTH);
    }

    public void updateAnalysis(List<Dimension> dimensions) {
        latestDimensions = dimensions;
        resultsPanel.removeAll();

        for (Dimension dim : dimensions) {
            double totalWeight = 0;
            double weightedSum = 0;

            for (Metric m : dim.getMetrics()) {
                weightedSum += (m.getCalculatedScore() * m.getCoefficient());
                totalWeight += m.getCoefficient();
            }

            double dimScore = weightedSum / totalWeight; // [cite: 74]

            resultsPanel.add(createScoreRow(dim.getName(), dimScore));

        }
        radarChartPanel.setDimensions(dimensions);
        calculateGapAnalysis();
        revalidate();
        repaint();
    }

    private JLabel createGapLabel(String text) {
        JLabel label = new JLabel(text);
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

        JProgressBar scoreBar = new JProgressBar(0, 50);
        scoreBar.setValue((int) Math.round(score * 10));
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
        return row;
    }

    private void addGapRow(int row, JLabel label) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 8, 6, 8);
        gapPanel.add(label, gbc);
    }

    private void calculateGapAnalysis() {
        if (latestDimensions == null || latestDimensions.isEmpty()) {
            lblLowestDimension.setText("Lowest Dimension: No analysis data available.");
            lblGapValue.setText("Gap Value: -");
            lblQualityLevel.setText("Quality Level: -");
            return;
        }

        Dimension lowestDim = null;
        double minScore = Double.MAX_VALUE;
        for (Dimension dim : latestDimensions) {
            if (dim.getFinalScore() < minScore) {
                minScore = dim.getFinalScore();
                lowestDim = dim;
            }
        }

        if (lowestDim != null) {
            double gap = 5.0 - minScore;
            String level = getQualityLevel(minScore);
            lblLowestDimension.setText("Lowest Dimension: " + lowestDim.getName() + " (" + String.format("%.2f", minScore) + ")");
            lblGapValue.setText("Gap Value: " + String.format("%.2f", gap));
            lblQualityLevel.setText("Quality Level: " + level);
            lblLowestDimension.setForeground(MainFrame.WARNING_COLOR);
            gapPanel.setBorder(BorderFactory.createTitledBorder(
                    new LineBorder(getQualityColor(minScore), 2, true),
                    "Gap Analysis Result"
            ));
        }
    }

    private String getQualityLevel(double score) {
        if (score >= 4.5) return "Excellent";
        if (score >= 3.5) return "Good";
        if (score >= 2.5) return "Needs Improvement";
        return "Poor"; // [cite: 83]
    }

    private Color getQualityColor(double score) {
        if (score >= 3.5) return MainFrame.SUCCESS_COLOR;
        if (score >= 2.5) return MainFrame.WARNING_COLOR;
        return MainFrame.DANGER_COLOR;
    }

    private static class RadarChartPanel extends JPanel {
        private List<Dimension> dimensions;

        private RadarChartPanel() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createTitledBorder(
                    new LineBorder(new Color(220, 226, 231), 1, true),
                    "Radar Chart"
            ));
        }

        private void setDimensions(List<Dimension> dimensions) {
            this.dimensions = dimensions;
            repaint();
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
            int radius = Math.min(width, height) / 3;

            for (int ring = 1; ring <= 5; ring++) {
                int r = (radius * ring) / 5;
                g2.setColor(new Color(230, 234, 238));
                g2.drawOval(cx - r, cy - r, r * 2, r * 2);
            }

            if (dimensions == null || dimensions.isEmpty()) {
                g2.setColor(MainFrame.TEXT_COLOR);
                g2.drawString("No data available.", cx - 45, cy);
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
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                g2.drawString(label, (int) (cx + (radius + 10) * Math.cos(angle)), (int) (cy + (radius + 10) * Math.sin(angle)));

                double score = Math.max(1.0, Math.min(5.0, dimensions.get(i).getFinalScore()));
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
