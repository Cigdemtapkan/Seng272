import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ProjectFormApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Software Project Registration Form"); // [cite: 48]
            frame.setSize(500, 450); // [cite: 49]
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // [cite: 50]

            // Paneli frame'e ekliyoruz
            frame.add(new ProjectFormPanel()); // [cite: 51]

            frame.setVisible(true); // [cite: 52]
        });
    }
}