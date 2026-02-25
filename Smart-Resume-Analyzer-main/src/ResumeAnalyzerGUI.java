import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

public class ResumeAnalyzerGUI extends JFrame {
    private JButton selectButton;
    private JTextArea outputArea;
    private JLabel resultLabel;
    private JFileChooser fileChooser;

    public ResumeAnalyzerGUI() {
        setTitle("Smart Resume Analyzer");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        selectButton = new JButton("Select Resume");
        outputArea = new JTextArea(10, 40);
        resultLabel = new JLabel("Match Percentage: ");
        fileChooser = new JFileChooser();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(selectButton, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        panel.add(resultLabel, BorderLayout.SOUTH);

        add(panel);

        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String resumePath = selectedFile.getAbsolutePath();

                    try {
                        // Step 1: Extract resume text
                        String resumeText = ResumeParser.extractTextFromPDF(resumePath);

                        // Step 2: Load keywords
                        List<String> keywords = SkillMatcher.loadKeywords("data/job_keywords.txt");

                        if (keywords.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "The job_keywords.txt file is empty.", "Keyword Error", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Step 3: Count matches
                        int matched = SkillMatcher.countMatches(resumeText, keywords);

                        // Step 4: Show results
                        outputArea.setText("Matched Skills: " + matched + " out of " + keywords.size() + "\n");
                        outputArea.append("Missing Skills:\n");
                        for (String keyword : keywords) {
                            if (!resumeText.toLowerCase().contains(keyword.toLowerCase())) {
                                outputArea.append("- " + keyword + "\n");
                            }
                        }

                        double percent = (matched * 100.0) / keywords.size();
                        resultLabel.setText("Match Percentage: " + String.format("%.2f%%", percent));

                        // Step 5: Success message
                        JOptionPane.showMessageDialog(null, "Resume analyzed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    } catch (java.nio.file.NoSuchFileException nf) {
                        JOptionPane.showMessageDialog(null, "The job_keywords.txt file was not found in the data folder.", "File Not Found", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "An error occurred:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ResumeAnalyzerGUI().setVisible(true);
            }
        });
    }
}
