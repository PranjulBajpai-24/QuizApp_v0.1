package quiz.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Rules extends JFrame implements ActionListener {

    JButton Start, Back;
    String name;
    String rollNo;

    Rules(String name, String rollNo) {
        this.name = name;
        this.rollNo = rollNo;
        getContentPane().setBackground(new Color(255, 218, 185)); // Peach background
        setLayout(null); // Set layout to null for absolute positioning

        JLabel heading = new JLabel("WELCOME " + name + " TO THE QUIZ !! ");
        heading.setBounds(80, 20, 700, 60);
        heading.setFont(new Font("Arial Black", Font.BOLD, 36));
        heading.setForeground(new Color(255, 140, 0)); // Dark Orange
        add(heading);

        JLabel subHeading = new JLabel("SET OF RULES:");
        subHeading.setBounds(60, 90, 400, 30);
        subHeading.setFont(new Font("Arial", Font.BOLD, 24));
        subHeading.setForeground(new Color(128, 0, 0)); // Maroon
        add(subHeading);

        JLabel rules = new JLabel();
        rules.setFont(new Font("Tahoma", Font.PLAIN, 18));
        rules.setForeground(new Color(47, 79, 79)); // Dark Slate Gray

        rules.setText(
            "<html>" +
                "1. Participation is open to anyone with a registered account.<br><br>" +
                "2. The quiz contains a total of <b>10 multiple-choice questions</b>.<br><br>" +
                "3. You will have <b>15 seconds</b> to answer each question.<br><br>" +
                "4. <b>Backtracking</b> is not allowed; once you answer, you cannot return.<br><br>" +
                "5. Use of <b>external help, devices, or browser tabs</b> during the quiz is strictly prohibited.<br><br>" +
                "6. Ensure a <b>stable internet connection</b> before beginning the quiz.<br><br>" +
                "7. Any suspicious activity may lead to immediate disqualification.<br><br>" +
                "8. Your final score will be displayed at the end of the quiz.<br><br>" +
                "9. In case of disconnection, you may not be allowed to resume.<br><br>" +
                "10. By starting the quiz, you agree to all the terms and conditions.<br><br>" +
                "</html>"
        );

        JScrollPane scrollPane = new JScrollPane(rules);
        scrollPane.setBounds(60, 130, 700, 320);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(255, 218, 185));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane);

        Back = new JButton("Back");
        Back.setBounds(180, 520, 130, 35);
        Back.setBackground(new Color(255, 140, 0)); // Dark Orange
        Back.setForeground(Color.WHITE);
        Back.setFont(new Font("Tahoma", Font.BOLD, 16));
        Back.addActionListener(this);
        add(Back);

        Start = new JButton("Start");
        Start.setBounds(520, 520, 130, 35);
        Start.setBackground(new Color(255, 140, 0)); // Dark Orange
        Start.setForeground(Color.WHITE);
        Start.setFont(new Font("Tahoma", Font.BOLD, 16));
        Start.addActionListener(this);
        add(Start);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/back.jpeg"));
        Image i = i1.getImage().getScaledInstance(850, 600, Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(i);
        JLabel image = new JLabel(i2);
        image.setBounds(0, 0, 850, 600);
        add(image); // Add last so it appears at the back

        setSize(850, 600);
        setLocation(350, 100);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Start) {
            setVisible(false);
            new Quiz(name);
        } else if (e.getSource() == Back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Rules("User", "RollNo");
    }
}
