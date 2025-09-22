package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Quiz extends JFrame implements ActionListener {

    // UI Components
    JLabel questionCounterLabel, questionTextLabel;
    JRadioButton option1, option2, option3, option4; // Assuming 4 options for flexibility
    ButtonGroup groupOptions;
    JButton nextButton, submitButton;

    // Data and Logic Variables
    private List<Question> questions;
    private int questionIndex = 0;
    private int score = 0;
    private String userName;
    public static final int TOTAL_QUESTIONS = 15; // Set the total number of questions for the quiz

    public Quiz(String userName) {
        this.userName = userName;

        // --- 1. FETCH QUESTIONS FROM BACKEND ---
        // This is the key change: we call the service to get live data.
        QuizService quizService = new QuizService();
        this.questions = quizService.getQuizQuestions();

        // Basic JFrame setup
        setTitle("Quiz Master");
        setBounds(50, 0, 1440, 850);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add an image icon (optional)
        ImageIcon i1 = new ImageIcon(getClass().getResource("/icons/quiz.png"));
        JLabel image = new JLabel(i1);
        image.setBounds(0, 0, 1440, 392);
        add(image);

        // UI Component Initialization
        questionCounterLabel = new JLabel();
        questionCounterLabel.setBounds(100, 450, 50, 30);
        questionCounterLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(questionCounterLabel);

        questionTextLabel = new JLabel();
        questionTextLabel.setBounds(150, 450, 900, 30);
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
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setEnabled(false); // Enabled only on the last question
        submitButton.addActionListener(this);
        add(submitButton);

        // Start the quiz by displaying the first question
        displayQuestion();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check the answer and update the score
        checkAnswer();

        // Move to the next question
        questionIndex++;

        if (questionIndex == TOTAL_QUESTIONS) {
            // If it's the end of the quiz, show the score
            showScore();
        } else {
            // Otherwise, display the next question
            displayQuestion();
            // Enable the submit button only on the last question
            if (questionIndex == TOTAL_QUESTIONS - 1) {
                nextButton.setEnabled(false);
                submitButton.setEnabled(true);
            }
        }
    }

    private void displayQuestion() {
        if (questionIndex < questions.size() && questionIndex < TOTAL_QUESTIONS) {
            Question currentQuestion = questions.get(questionIndex);

            questionCounterLabel.setText((questionIndex + 1) + ".");
            questionTextLabel.setText(currentQuestion.getQuestionText());
            option1.setText(currentQuestion.getOptionA());
            option2.setText(currentQuestion.getOptionB());
            option3.setText(currentQuestion.getOptionC());
            // Assuming the 4th option is stored in a field, e.g., getOptionD()
            // If you only have 3 options, you can hide the 4th radio button:
            // option4.setVisible(false);
            // For now, let's assume 3 options and use a placeholder for the 4th.
             option4.setText("None of the above");


            // Clear the selection from the previous question
            groupOptions.clearSelection();
        } else {
            // Handle case where there are not enough questions from the backend
            if (questionIndex > 0) { // If at least one question was shown
                showScore();
            } else {
                JOptionPane.showMessageDialog(this, "Could not load questions from the server.", "Error", JOptionPane.ERROR_MESSAGE);
                setVisible(false);
                // Go back to login or exit
            }
        }
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
        setVisible(false);
        // Assuming you have a Score class that takes name and score
        new Score(userName, score);
    }

    public static void main(String[] args) {
        // For testing purposes
        new Quiz("User");
    }
}