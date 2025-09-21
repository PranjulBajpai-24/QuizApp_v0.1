package quiz.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Quiz extends JFrame implements ActionListener {

    String[][] questions;
    String[][] answers;
    String[][] userAns;

    JLabel qno, ques, timerLabel;
    JRadioButton o1, o2, o3, o4;
    ButtonGroup grp;
    JButton next, submit, help;

    private int cnt = 0;
    public static int score = 80; // Initialize to 0

    String name;
    private javax.swing.Timer questionTimer;
    private int secondsLeft = 30; // changed to 30

    int totalQuestions;
    JProgressBar progressBar;

    Quiz(String name) {
        this.name = name;

        QuestionData qData = new QuestionData();
        questions = qData.getQuestions();
        answers = qData.getAnswers();
        totalQuestions = qData.getTotalQuestions();
        userAns = new String[totalQuestions][1];

        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/quiz.png"));
        Image img = i1.getImage().getScaledInstance(screenWidth, 300, Image.SCALE_SMOOTH);
        ImageIcon i2 = new ImageIcon(img);

        JLabel image = new JLabel(i2);
        image.setBounds(0, 0, screenWidth, 300);
        add(image);

        getContentPane().setBackground(new Color(255, 245, 230));

        qno = new JLabel();
        qno.setBounds(100, 350, 50, 30);
        qno.setFont(new Font("Tahoma", Font.BOLD, 24));
        add(qno);

        ques = new JLabel();
        ques.setBounds(150, 350, screenWidth - 200, 30);
        ques.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(ques);

        timerLabel = new JLabel("Time left: 30 seconds");
        timerLabel.setBounds(screenWidth - 300, 310, 250, 30);
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        timerLabel.setForeground(Color.RED);
        add(timerLabel);

        int optionStartY = 420;
        int optionGap = 50;

        o1 = new JRadioButton();
        o2 = new JRadioButton();
        o3 = new JRadioButton();
        o4 = new JRadioButton();

        JRadioButton[] options = { o1, o2, o3, o4 };
        for (int i = 0; i < 4; i++) {
            options[i].setBounds(150, optionStartY + i * optionGap, screenWidth - 300, 30);
            options[i].setBackground(new Color(255, 245, 230));
            options[i].setFont(new Font("Dialog", Font.PLAIN, 20));
            add(options[i]);
        }

        grp = new ButtonGroup();
        grp.add(o1);
        grp.add(o2);
        grp.add(o3);
        grp.add(o4);

        int buttonWidth = 200;
        int buttonHeight = 40;
        int bottomPadding = 100;

        help = new JButton("Help");
        help.setBounds(100, screenHeight - bottomPadding - 50, buttonWidth, buttonHeight);
        help.setBackground(new Color(30, 144, 255));
        help.setForeground(Color.WHITE);
        help.setFont(new Font("Tahoma", Font.BOLD, 16));
        help.addActionListener(this);
        add(help);

        next = new JButton("Next");
        next.setBounds(screenWidth - buttonWidth - 100, screenHeight - bottomPadding - 50, buttonWidth, buttonHeight);
        next.setBackground(new Color(30, 144, 255));
        next.setForeground(Color.WHITE);
        next.setFont(new Font("Tahoma", Font.BOLD, 16));
        next.addActionListener(this);
        add(next);

        submit = new JButton("Submit");
        submit.setBounds((screenWidth - buttonWidth) / 2, screenHeight - bottomPadding - 50, buttonWidth, buttonHeight);
        submit.setBackground(new Color(34, 139, 34));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Tahoma", Font.BOLD, 16));
        submit.setEnabled(false);
        submit.addActionListener(this);
        add(submit);

        progressBar = new JProgressBar(0, totalQuestions);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Tahoma", Font.BOLD, 16));
        progressBar.setForeground(new Color(60, 179, 113));
        progressBar.setBounds(150, screenHeight - 70, screenWidth - 300, 25);
        add(progressBar);

        questionTimer = new javax.swing.Timer(1000, e -> updateTimer());
        start(cnt);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next) {
            recordAnswer();
            cnt++;
            if (cnt == totalQuestions - 1) {
                next.setEnabled(false);
                submit.setEnabled(true);
            }
            if (cnt < totalQuestions) {
                start(cnt);
            }
        } else if (e.getSource() == help) {
            questionTimer.stop();
            new Help(this);
            help.setEnabled(false);
        } else if (e.getSource() == submit) {
            questionTimer.stop();
            recordAnswer();
            calculateScore();
            setVisible(false);
            new Score(name, score);
        }
    }

    private void updateTimer() {
        secondsLeft--;
        timerLabel.setText("Time left: " + secondsLeft + " seconds");

        if (secondsLeft <= 0) {
            timerLabel.setText("Time's up!");
            questionTimer.stop();
            handleTimeUp();
        }
    }

    private void handleTimeUp() {
        recordAnswer();
        cnt++;
        if (cnt == totalQuestions - 1) {
            next.setEnabled(false);
            submit.setEnabled(true);
            start(cnt);
        } else if (cnt < totalQuestions) {
            start(cnt);
        } else {
            calculateScore();
            setVisible(false);
            new Score(name, score);
        }
    }

    private void recordAnswer() {
        if (grp.getSelection() == null) {
            userAns[cnt][0] = "";
        } else {
            userAns[cnt][0] = grp.getSelection().getActionCommand();
        }
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < totalQuestions; i++) {
            System.out.println("Question " + (i + 1) + ":");
            System.out.println("  User answer: " + (userAns[i][0] != null ? userAns[i][0] : "null"));
            System.out.println("  Correct answer: " + (answers[i][0] != null ? answers[i][0] : "null"));

            if (userAns[i][0] != null &&
                answers[i][0] != null &&
                userAns[i][0].equals(answers[i][0])) {
                System.out.println("  CORRECT!");
                score += 10;
            } else {
                System.out.println("  INCORRECT!");
            }
        }
        System.out.println("Total Score: " + score);
    }

    public void start(int cnt) {
        if (cnt < totalQuestions) {
            qno.setText((cnt + 1) + ". ");
            ques.setText(questions[cnt][0]);
            o1.setText(questions[cnt][1]);
            o1.setActionCommand(questions[cnt][1]);
            o2.setText(questions[cnt][2]);
            o2.setActionCommand(questions[cnt][2]);
            o3.setText(questions[cnt][3]);
            o3.setActionCommand(questions[cnt][3]);
            o4.setText(questions[cnt][4]);
            o4.setActionCommand(questions[cnt][4]);
            grp.clearSelection();

            secondsLeft = 30; // changed to 30
            timerLabel.setText("Time left: " + secondsLeft + " seconds");
            timerLabel.setForeground(Color.RED);
            questionTimer.start();

            help.setEnabled(true);
            progressBar.setValue(cnt + 1);
            progressBar.setString("Question " + (cnt + 1) + " of " + totalQuestions);
        }
    }

    public void resumeTimer() {
        if (secondsLeft > 0) {
            questionTimer.start();
        }
    }

    public static void main(String[] args) {
        new Quiz("User");
    }
}
