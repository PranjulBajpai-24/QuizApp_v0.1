package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class Quiz extends JFrame implements ActionListener {

    // --- UI Components ---
    JLabel questionCounterLabel, questionTextLabel, timerLabel;
    JRadioButton option1, option2, option3, option4;
    ButtonGroup groupOptions;
    JButton nextButton, submitButton, helpButton;
    JProgressBar progressBar;

    // --- Data and Logic Variables ---
    private List<Question> questions;
    private String[][] userAnswers;
    private int questionIndex = 0;
    private int score = 0;
    private String userName;
    private String rollNo;

    // --- Timer Variables ---
    private Timer timer;
    private int secondsLeft = 30;

    public Quiz(String userName, String rollNo) {
        this.userName = userName;
        this.rollNo = rollNo;

        QuizService quizService = new QuizService();
        this.questions = quizService.getQuizQuestions();

        if (questions == null || questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Could not load questions. Make sure the backend server is running.", "Load Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        this.userAnswers = new String[questions.size()][1];
        setupUI();
        displayQuestion();
        setVisible(true);
    }

    private void setupUI() {
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 245, 230));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        URL iconUrl = getClass().getResource("/icons/quiz.png");
        if (iconUrl != null) {
            ImageIcon i1 = new ImageIcon(iconUrl);
            Image img = i1.getImage().getScaledInstance(screenWidth, 300, Image.SCALE_SMOOTH);
            ImageIcon i2 = new ImageIcon(img);
            JLabel image = new JLabel(i2);
            image.setBounds(0, 0, screenWidth, 300);
            add(image);
        }

        questionCounterLabel = new JLabel();
        questionCounterLabel.setBounds(100, 350, 50, 30);
        questionCounterLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        add(questionCounterLabel);

        questionTextLabel = new JLabel();
        questionTextLabel.setBounds(150, 350, screenWidth - 200, 30);
        questionTextLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(questionTextLabel);

        timerLabel = new JLabel();
        timerLabel.setBounds(screenWidth - 300, 310, 250, 30);
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        timerLabel.setForeground(Color.RED);
        add(timerLabel);

        int optionStartY = 420;
        int optionGap = 50;
        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();
        JRadioButton[] options = { option1, option2, option3, option4 };
        groupOptions = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i].setBounds(150, optionStartY + i * optionGap, screenWidth - 300, 30);
            options[i].setBackground(new Color(255, 245, 230));
            options[i].setFont(new Font("Dialog", Font.PLAIN, 20));
            add(options[i]);
            groupOptions.add(options[i]);
        }

        int buttonWidth = 200;
        int buttonHeight = 40;
        int bottomPadding = 100;

        helpButton = new JButton("Help");
        helpButton.setBounds(100, screenHeight - bottomPadding - 50, buttonWidth, buttonHeight);
        helpButton.setBackground(new Color(30, 144, 255));
        helpButton.setForeground(Color.WHITE);
        helpButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        helpButton.addActionListener(this);
        add(helpButton);

        nextButton = new JButton("Next");
        nextButton.setBounds(screenWidth - buttonWidth - 100, screenHeight - bottomPadding - 50, buttonWidth, buttonHeight);
        nextButton.setBackground(new Color(30, 144, 255));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        nextButton.addActionListener(this);
        add(nextButton);

        submitButton = new JButton("Submit");
        submitButton.setBounds((screenWidth - buttonWidth) / 2, screenHeight - bottomPadding - 50, buttonWidth, buttonHeight);
        submitButton.setBackground(new Color(34, 139, 34));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        submitButton.setEnabled(false);
        submitButton.addActionListener(this);
        add(submitButton);

        progressBar = new JProgressBar(0, questions.size());
        progressBar.setBounds(150, screenHeight - 70, screenWidth - 300, 25);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Tahoma", Font.BOLD, 16));
        progressBar.setForeground(new Color(60, 179, 113));
        add(progressBar);

        timer = new Timer(1000, e -> updateTimer());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.stop();
        recordAnswer();

        if (e.getSource() == submitButton) {
            calculateAndShowScore();
        } else if (e.getSource() == helpButton) {
            new Help(this);
            helpButton.setEnabled(false);
        } else if (e.getSource() == nextButton) {
            questionIndex++;
            displayQuestion();
        }
    }

    private void updateTimer() {
        secondsLeft--;
        timerLabel.setText("Time left: " + secondsLeft + " seconds");
        if (secondsLeft <= 0) {
            timer.stop();
            handleTimeUp();
        }
    }

    private void handleTimeUp() {
        recordAnswer();
        if (questionIndex == questions.size() - 1) {
            calculateAndShowScore();
        } else {
            questionIndex++;
            displayQuestion();
        }
    }

    private void displayQuestion() {
        if (questionIndex >= questions.size()) {
            calculateAndShowScore();
            return;
        }

        if (questionIndex == questions.size() - 1) {
            nextButton.setEnabled(false);
            submitButton.setEnabled(true);
        }

        Question currentQuestion = questions.get(questionIndex);
        questionCounterLabel.setText((questionIndex + 1) + ". ");
        questionTextLabel.setText(currentQuestion.getQuestionText());

        option1.setText(currentQuestion.getOptionA());
        option1.setActionCommand(currentQuestion.getOptionA());
        option2.setText(currentQuestion.getOptionB());
        option2.setActionCommand(currentQuestion.getOptionB());
        option3.setText(currentQuestion.getOptionC());
        option3.setActionCommand(currentQuestion.getOptionC());
        option4.setText(currentQuestion.getOptionD());
        option4.setActionCommand(currentQuestion.getOptionD());

        groupOptions.clearSelection();
        progressBar.setValue(questionIndex + 1);
        progressBar.setString("Question " + (questionIndex + 1) + " of " + questions.size());

        secondsLeft = 30;
        updateTimer();
        timer.start();
        helpButton.setEnabled(true);
    }

    private void recordAnswer() {
        if (groupOptions.getSelection() != null) {
            userAnswers[questionIndex][0] = groupOptions.getSelection().getActionCommand();
        } else {
            userAnswers[questionIndex][0] = "";
        }
    }

    private void calculateAndShowScore() {
        score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i][0].equals(questions.get(i).getCorrectAnswer())) {
                score += 10;
            }
        }
        setVisible(false);
        // CORRECTED: Pass the rollNo to the Score constructor
        new Score(userName, rollNo, score);
    }

    public void resumeTimer() {
        timer.start();
    }

    public String getUserName() {
        return this.userName;
    }

    public int getScore() {
        return this.score;
    }

    // ADDED: This method is needed for the Help class
    public String getRollNo() {
        return this.rollNo;
    }
}