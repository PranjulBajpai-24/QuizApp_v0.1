package quiz.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

    JTextField text, rollText; // Added rollText
    JButton Next, Back;

    Login() {
        getContentPane().setBackground(new Color(255, 218, 185)); // Peach background
        setLayout(null);

        URL imgUrl = getClass().getResource("/icons/login.jpg");
        if (imgUrl != null) {
            ImageIcon i1 = new ImageIcon(imgUrl);
            Image i = i1.getImage().getScaledInstance(550, 500, Image.SCALE_DEFAULT);
            ImageIcon i2 = new ImageIcon(i);
            JLabel image = new JLabel(i2);
            image.setBounds(450, 0, 550, 500);
            add(image);
        } else {
            System.err.println("Image not found at /icons/login.jpg");
        }

        JLabel heading = new JLabel("QUIZ MASTER");
        heading.setBounds(25, 40, 400, 60);
        heading.setFont(new Font("Arial Black", Font.BOLD, 48));
        heading.setForeground(new Color(255, 140, 0)); // Dark Orange
        add(heading);

        // Name Label and Field
        JLabel name = new JLabel("Enter Your Name:");
        name.setBounds(125, 120, 200, 30);
        name.setFont(new Font("Serif", Font.BOLD, 22));
        name.setForeground(new Color(47, 79, 79)); // Dark Slate Gray
        add(name);

        text = new JTextField();
        text.setBounds(75, 150, 300, 30);
        text.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(text);

        // Roll No. Label and Field
        JLabel Roll = new JLabel("Enter Your Roll No.:");
        Roll.setBounds(125, 180, 200, 30);
        Roll.setFont(new Font("Serif", Font.BOLD, 22));
        Roll.setForeground(new Color(47, 79, 79)); // Dark Slate Gray
        add(Roll);
        
        rollText = new JTextField();
        rollText.setBounds(75, 210, 300, 30);
        rollText.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        add(rollText);

        // Buttons
        Back = new JButton("Back");
        Back.setBounds(60, 270, 130, 35);
        Back.setBackground(new Color(255, 140, 0)); // Dark Orange
        Back.setForeground(Color.WHITE);
        Back.setFont(new Font("Tahoma", Font.BOLD, 16));
        Back.addActionListener(this);
        add(Back);

        Next = new JButton("Next");
        Next.setBounds(260, 270, 130, 35);
        Next.setBackground(new Color(255, 140, 0)); // Dark Orange
        Next.setForeground(Color.WHITE);
        Next.setFont(new Font("Tahoma", Font.BOLD, 16));
        Next.addActionListener(this);
        add(Next);

        setSize(1000, 500);
        setLocation(250, 150);
        setUndecorated(true);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Next) {
            String name = text.getText();
            String rollNo = rollText.getText();
            
            if (name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name and roll number to continue.", "Name and Roll Number Required", JOptionPane.WARNING_MESSAGE);
            } else if (rollNo.trim().isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Please enter your roll ID to continue.", "Roll No. Required", JOptionPane.WARNING_MESSAGE);
            } else {
                setVisible(false);
                // Important: You must now pass both values to the Rules class
                new Rules(name, rollNo); 
            }
        } else if (e.getSource() == Back) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}