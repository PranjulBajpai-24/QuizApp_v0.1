package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Score extends JFrame {

    Score(String name, int score) {
        setUndecorated(true);
        setTitle("Quiz Score");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 245, 230));

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/score.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(250, 30, 300, 200);
        add(image);

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

        JButton exit = new JButton("Back");
        exit.setBounds((800 - 150) / 2, 420, 150, 40);
        exit.setBackground(new Color(255, 140, 0));
        exit.setForeground(Color.WHITE);
        exit.setFont(new Font("Tahoma", Font.BOLD, 16));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Login();
            }
        });
        add(exit);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Score("User", 0);
    }
}