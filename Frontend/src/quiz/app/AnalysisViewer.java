package quiz.app;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AnalysisViewer extends JFrame {

    // UPDATED: The constructor now accepts the user's name and roll number
    AnalysisViewer(String userName, String rollNo, List<Question> questions, String[][] userAnswers) {
        setTitle("Result Analysis");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10));

        // --- 1. Create a Header Panel to hold the title and user info ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        // Title Label
        JLabel titleLabel = new JLabel("Your Quiz Performance Analysis", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        // --- 2. Create a Panel for the User's Details ---
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
        userInfoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Name: " + userName);
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        userInfoPanel.add(nameLabel);

        JLabel rollNoLabel = new JLabel("Roll No: " + rollNo);
        rollNoLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        userInfoPanel.add(rollNoLabel);

        headerPanel.add(userInfoPanel, BorderLayout.CENTER);

        // Add the combined header panel to the top of the frame
        add(headerPanel, BorderLayout.NORTH);

        // --- 3. Create the Results Table (same as before) ---
        String[] columnNames = {"Q No.", "Question", "Your Answer", "Correct Answer", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userAnswer = userAnswers[i][0];
            String correctAnswer = question.getCorrectAnswer();
            String status;

            if (userAnswer.isEmpty()) {
                status = "Not Answered";
            } else if (userAnswer.trim().equals(correctAnswer.trim())) {
                status = "Correct";
            } else {
                status = "Incorrect";
            }

            Object[] row = {
                    (i + 1),
                    question.getQuestionText(),
                    userAnswer.isEmpty() ? "Not Answered" : userAnswer,
                    correctAnswer,
                    status
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(450);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- 4. Add the Close Button (same as before) ---
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = (String) value;
            if ("Correct".equals(status)) {
                c.setBackground(new Color(204, 255, 204)); c.setForeground(new Color(0, 102, 0));
            } else if ("Incorrect".equals(status)) {
                c.setBackground(new Color(255, 204, 204)); c.setForeground(new Color(153, 0, 0));
            } else if ("Not Answered".equals(status)) {
                c.setBackground(new Color(224, 224, 224)); c.setForeground(new Color(80, 80, 80));
            } else {
                c.setBackground(table.getBackground()); c.setForeground(table.getForeground());
            }
            setHorizontalAlignment(JLabel.CENTER);
            return c;
        }
    }
}