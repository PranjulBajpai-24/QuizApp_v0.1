package quiz.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Help extends JFrame {

    Help(Quiz quizRef) {
        setTitle("Help and Support");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 250, 240));

        JLabel heading = new JLabel("Quiz Help & Support");
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setBounds(200, 20, 400, 30);
        add(heading);

        JTextArea helpText = new JTextArea();
        helpText.setText(
                "→ Each question has 4 options, only one is correct.\n" +
                        "→ You can use the 'Help' button to pause the timer.\n" +
                        "→ Click 'Next' to go to the next question.\n" +
                        "→ Timer for each question is 30 seconds.\n\n" +
                        "For further issues, contact:\n" +
                        "Pranjul Bajpai\n" +
                        "Phone: +91 6389668790\n" +
                        "Email: pranjulbajpai63@gmail.com"
        );
        helpText.setFont(new Font("Tahoma", Font.PLAIN, 16));
        helpText.setBounds(50, 70, 600, 200);
        helpText.setEditable(false);
        helpText.setFocusable(false);
        helpText.setBackground(new Color(255, 250, 240));
        add(helpText);

        JButton back = new JButton("Back to Quiz");
        back.setBounds(150, 300, 160, 40);
        back.setBackground(new Color(30, 144, 255));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Tahoma", Font.BOLD, 16));
        back.addActionListener(e -> {
            if (quizRef != null) {
                quizRef.setEnabled(true);
                quizRef.resumeTimer();
            }
            dispose();
        });
        add(back);

        JButton exit = new JButton("Exit the Test");
        exit.setBounds(370, 300, 160, 40);
        exit.setBackground(new Color(220, 20, 60));
        exit.setForeground(Color.WHITE);
        exit.setFont(new Font("Tahoma", Font.BOLD, 16));
        exit.addActionListener(e -> {
            if (quizRef != null) {
                quizRef.setEnabled(true);
                quizRef.setVisible(false);
                // CORRECTED: Pass the rollNo to the Score constructor
                new Score(quizRef.getUserName(), quizRef.getRollNo(), quizRef.getScore());
            }
            dispose();
        });
        add(exit);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (quizRef != null) {
                    quizRef.setEnabled(true);
                    quizRef.resumeTimer();
                }
            }
        });

        if (quizRef != null) {
            quizRef.setEnabled(false);
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}