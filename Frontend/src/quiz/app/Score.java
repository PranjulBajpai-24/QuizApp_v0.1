package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.List;

public class Score extends JFrame {

    private List<Question> questions;
    private String[][] userAnswers;

    Score(String name, String rollNo, int score, List<Question> questions, String[][] userAnswers) {
        this.questions = questions;
        this.userAnswers = userAnswers;

        QuizService service = new QuizService();
        service.saveScore(name, rollNo, score);

        setUndecorated(true);
        setTitle("Quiz Score");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 245, 230));

        URL iconUrl = getClass().getResource("/icons/score.jpeg");
        if (iconUrl != null) {
            ImageIcon i1 = new ImageIcon(iconUrl);
            Image i2 = i1.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            ImageIcon i3 = new ImageIcon(i2);
            JLabel image = new JLabel(i3);
            image.setBounds(250, 30, 300, 200);
            add(image);
        }

        JLabel heading = new JLabel("Thank You, " + name + ", for Attending the Quiz!");
        heading.setBounds(100, 250, 600, 40);
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setHorizontalAlignment(JLabel.CENTER);
        add(heading);

        JLabel scoreLabel = new JLabel("Your Score: " + score);
        scoreLabel.setBounds(100, 310, 600, 40);
        scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
        scoreLabel.setForeground(new Color(34, 139, 34));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        add(scoreLabel);

        JButton analysisButton = new JButton("View Result Analysis");
        analysisButton.setBounds((800 - 250) / 2, 370, 250, 40);
        analysisButton.setBackground(new Color(60, 179, 113));
        analysisButton.setForeground(Color.WHITE);
        analysisButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        analysisButton.addActionListener(e -> {
            // UPDATED: Pass the name and roll number along with the results
            new AnalysisViewer(name, rollNo, this.questions, this.userAnswers);
        });
        add(analysisButton);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds((800 - 150) / 2, 430, 150, 40);
        playAgainButton.setBackground(new Color(30, 144, 255));
        playAgainButton.setForeground(Color.WHITE);
        playAgainButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        playAgainButton.addActionListener(e -> {
            setVisible(false);
            new Login();
        });
        add(playAgainButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds((800 - 150) / 2, 490, 150, 40);
        exitButton.setBackground(new Color(255, 140, 0));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        add(exitButton);

        setVisible(true);
    }
}