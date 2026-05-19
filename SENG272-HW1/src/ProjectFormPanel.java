import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProjectFormPanel extends JPanel { // [cite: 54]
    private JTextField projectNameField, teamLeaderField, startDateField;
    private JComboBox<String> teamSizeCombo, projectTypeCombo;

    public ProjectFormPanel() {
        // Label ve field'ları satırlar halinde hizalamak için GridLayout kullanıyoruz
        setLayout(new GridLayout(7, 2, 10, 10)); // [cite: 55]
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form alanlarının oluşturulması
        projectNameField = new JTextField(); // [cite: 15]
        teamLeaderField = new JTextField(); // [cite: 15]
        startDateField = new JTextField(); // [cite: 15]

        String[] teamSizes = {"1-3", "4-6", "7-10", "10+"}; // [cite: 15]
        teamSizeCombo = new JComboBox<>(teamSizes); // [cite: 15]

        String[] projectTypes = {"Web", "Mobile", "Desktop", "API"}; // [cite: 15]
        projectTypeCombo = new JComboBox<>(projectTypes); // [cite: 15]

        // Bileşenlerin panele eklenmesi
        add(new JLabel("Project Name:")); // [cite: 56]
        add(projectNameField); // [cite: 56]
        add(new JLabel("Team Leader:")); // [cite: 56]
        add(teamLeaderField); // [cite: 56]
        add(new JLabel("Team Size:")); // [cite: 56]
        add(teamSizeCombo); // [cite: 56]
        add(new JLabel("Project Type:")); // [cite: 56]
        add(projectTypeCombo); // [cite: 56]
        add(new JLabel("Start Date (DD/MM/YYYY):")); // [cite: 56]
        add(startDateField); // [cite: 56]

        // Butonlar
        JButton saveButton = new JButton("Save"); // [cite: 57]
        JButton clearButton = new JButton("Clear"); // [cite: 57]
        add(saveButton); // [cite: 57]
        add(clearButton); // [cite: 57]

        // Save Butonu Mantığı
        saveButton.addActionListener(new ActionListener() { // [cite: 61]
            @Override
            public void actionPerformed(ActionEvent e) {
                saveRecord();
            }
        });

        // Clear Butonu Mantığı
        clearButton.addActionListener(e -> clearFields()); // [cite: 69]
    }

    private void saveRecord() {
        String projName = projectNameField.getText().trim();
        String leader = teamLeaderField.getText().trim();
        String startDate = startDateField.getText().trim();

        // Boş alan kontrolü
        if (projName.isEmpty() || leader.isEmpty() || startDate.isEmpty()) { // [cite: 64]
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE); // [cite: 65]
            return;
        }

        // Dosyaya yazma işlemi (Append modunda)
        try (FileWriter writer = new FileWriter("projects.txt", true)) { // [cite: 66]
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now(); // 

            writer.write("=== Project Entry ===\n"); // [cite: 23]
            writer.write("Project Name : " + projName + "\n"); // [cite: 24]
            writer.write("Team Leader  : " + leader + "\n"); // [cite: 25, 26]
            writer.write("Team Size    : " + teamSizeCombo.getSelectedItem() + "\n"); // [cite: 27, 28]
            writer.write("Project Type : " + projectTypeCombo.getSelectedItem() + "\n"); // [cite: 29, 30]
            writer.write("Start Date   : " + startDate + "\n"); // [cite: 31, 32]
            writer.write("Record Time  : " + dtf.format(now) + "\n"); // [cite: 33, 34]
            writer.write("======\n"); // [cite: 35]

            JOptionPane.showMessageDialog(this, "Project successfully saved!"); // [cite: 68]
            clearFields();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "File writing error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        projectNameField.setText(""); // [cite: 71]
        teamLeaderField.setText(""); // [cite: 71]
        startDateField.setText(""); // [cite: 71]
        teamSizeCombo.setSelectedIndex(0); // [cite: 72]
        projectTypeCombo.setSelectedIndex(0); // [cite: 72]
    }
}