package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import javax.swing.Timer;

public class Quiz extends JFrame implements ActionListener {

    // UI Components
    JLabel questionCounterLabel, questionTextLabel;
    JRadioButton option1, option2, option3, option4;
    ButtonGroup groupOptions;
    JButton nextButton, submitButton;

    // Data and Logic Variables
    private List<Question> questions;
    private int questionIndex = 0;
    public static int score = 0;  // Changed to public static for access from Help class
    public String name;  // Changed to public for access from Help class
    private String rollNo;

    // Timer related variables
    private Timer timer;
    private int secondsLeft = 15;
    private JLabel timerLabel;
    private boolean timerRunning = false;

    public Quiz(String userName, String rollNo) {
        this.name = userName;
        this.rollNo = rollNo;
        QuizService quizService = new QuizService();
        this.questions = quizService.getQuizQuestions();

        setTitle("Quiz Master");
        setBounds(50, 0, 1440, 850);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        URL iconUrl = getClass().getResource("/icons/quiz.png");
        if (iconUrl != null) {
            ImageIcon i1 = new ImageIcon(iconUrl);
            JLabel image = new JLabel(i1);
            image.setBounds(0, 0, 1440, 392);
            add(image);
        }

        // Timer label
        timerLabel = new JLabel("15");
        timerLabel.setBounds(1100, 450, 100, 30);
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        timerLabel.setForeground(Color.RED);
        add(timerLabel);

        // Initialize timer
        timer = new Timer(1000, e -> {
            secondsLeft--;
            timerLabel.setText(Integer.toString(secondsLeft));
            if (secondsLeft <= 0) {
                ((Timer)e.getSource()).stop();
                timerRunning = false;
                checkAnswer();
                nextQuestion();
            }
        });

        questionCounterLabel = new JLabel();
        questionCounterLabel.setBounds(100, 450, 50, 30);
        questionCounterLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(questionCounterLabel);

        questionTextLabel = new JLabel();
        questionTextLabel.setBounds(150, 450, 1200, 30);
        questionTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(questionTextLabel);

        option1 = new JRadioButton();
        option1.setBounds(170, 520, 700, 30);
        option1.setBackground(Color.WHITE);
        option1.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(option1);

        option2 = new JRadioButton();
        option2.setBounds(170, 560, 700, 30);
        option2.setBackground(Color.WHITE);
        option2.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(option2);

        option3 = new JRadioButton();
        option3.setBounds(170, 600, 700, 30);
        option3.setBackground(Color.WHITE);
        option3.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(option3);

        option4 = new JRadioButton();
        option4.setBounds(170, 640, 700, 30);
        option4.setBackground(Color.WHITE);
        option4.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(option4);

        groupOptions = new ButtonGroup();
        groupOptions.add(option1);
        groupOptions.add(option2);
        groupOptions.add(option3);
        groupOptions.add(option4);

        nextButton = new JButton("Next");
        nextButton.setBounds(1100, 550, 200, 40);
        nextButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        nextButton.setBackground(new Color(30, 144, 255));
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(this);
        add(nextButton);

        submitButton = new JButton("Submit");
        submitButton.setBounds(1100, 630, 200, 40);
        submitButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        submitButton.setBackground(new Color(255, 140, 0));
        submitButton.setForeground(Color.WHITE);
        submitButton.setEnabled(false);
        submitButton.addActionListener(this);
        add(submitButton);

        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Could not load questions. Make sure the backend server is running.", "Load Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } else {
            displayQuestion();
            startTimer();
            setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkAnswer();
        if (e.getSource() == submitButton) {
            showScore();
        } else if (e.getSource() == nextButton) {
            nextQuestion();
        }
    }

    private void nextQuestion() {
        questionIndex++;
        if (timer.isRunning()) {
            timer.stop();
        }
        if (questionIndex < questions.size()) {
            displayQuestion();
            startTimer();
            if (questionIndex == questions.size() - 1) {
                nextButton.setEnabled(false);
                submitButton.setEnabled(true);
            }
        } else {
            showScore();
        }
    }

    private void displayQuestion() {
        groupOptions.clearSelection();
        Question currentQuestion = questions.get(questionIndex);
        questionCounterLabel.setText((questionIndex + 1) + ".");
        questionTextLabel.setText(currentQuestion.getQuestionText());
        option1.setText(currentQuestion.getOptionA());
        option2.setText(currentQuestion.getOptionB());
        option3.setText(currentQuestion.getOptionC());
        option4.setText(currentQuestion.getOptionD());
        secondsLeft = 15;
        timerLabel.setText(Integer.toString(secondsLeft));
    }

    private void checkAnswer() {
        Question currentQuestion = questions.get(questionIndex);
        String correctAnswer = currentQuestion.getCorrectAnswer();
        String selectedAnswer = null;

        if (option1.isSelected()) {
            selectedAnswer = option1.getText();
        } else if (option2.isSelected()) {
            selectedAnswer = option2.getText();
        } else if (option3.isSelected()) {
            selectedAnswer = option3.getText();
        } else if (option4.isSelected()) {
            selectedAnswer = option4.getText();
        }

        if (selectedAnswer != null && selectedAnswer.equals(correctAnswer)) {
            score += 10;
        }
    }

    private void showScore() {
        if (timer.isRunning()) {
            timer.stop();
        }
        setVisible(false);
        new Score(name, score);
    }

    public void startTimer() {
        secondsLeft = 15;
        timerLabel.setText(Integer.toString(secondsLeft));
        timer.start();
        timerRunning = true;
    }

    public void stopTimer() {
        if (timer.isRunning()) {
            timer.stop();
            timerRunning = false;
        }
    }

    // Add this method to fix Help.java errors
    public void resumeTimer() {
        if (!timerRunning) {
            timer.start();
            timerRunning = true;
        }
    }
}