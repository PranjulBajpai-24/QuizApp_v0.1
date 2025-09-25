package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL; // ADDED THIS IMPORT

public class Score extends JFrame {

    Score(String name, String rollNo, int score) {
        System.out.println("Attempting to save score for user: " + name);
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
        scoreLabel.setBounds(100, 310, 600, 40);git checkout main
        scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
        scoreLabel.setForeground(new Color(34, 139, 34));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        add(scoreLabel);

        JButton exit = new JButton("Play Again");
        exit.setBounds((800 - 150) / 2, 420, 150, 40);
        exit.setBackground(new Color(255, 140, 0));
        exit.setForeground(Color.WHITE);
        exit.setFont(new Font("Tahoma", Font.BOLD, 16));
        exit.addActionListener(e -> {
            setVisible(false);
            new Login();
        });
        add(exit);

        setVisible(true);
    }
}