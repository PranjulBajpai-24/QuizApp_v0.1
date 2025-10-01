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
    private QuizService quizService;

    // --- Timer Variables ---
    private Timer timer;
    private int secondsLeft = 30;

    public Quiz(String userName, String rollNo) {
        this.userName = userName;
        this.rollNo = rollNo;
        this.quizService = new QuizService();

        // --- Step 1: Set up the UI immediately ---
        setupUI();
        setVisible(true);

        // --- Step 2: Show a "Loading" state to the user ---
        showLoadingState();

        // --- Step 3: Start fetching questions in the background ---
        new QuestionFetcher().execute();
    }

    /**
     * A SwingWorker to fetch questions on a background thread without freezing the UI.
     */
    private class QuestionFetcher extends SwingWorker<List<Question>, Void> {
        @Override
        protected List<Question> doInBackground() throws Exception {
            // This runs on a separate thread
            return quizService.getQuizQuestions();
        }

        @Override
        protected void done() {
            // This runs back on the UI thread after doInBackground is finished
            try {
                questions = get(); // Get the list of questions from the background task
                if (questions == null || questions.isEmpty()) {
                    showErrorAndExit();
                } else {
                    // Questions loaded successfully, initialize the quiz
                    initializeQuiz();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showErrorAndExit();
            }
        }
    }

    private void showLoadingState() {
        questionTextLabel.setText("Loading questions from the server, please wait...");
        option1.setVisible(false);
        option2.setVisible(false);
        option3.setVisible(false);
        option4.setVisible(false);
        nextButton.setEnabled(false);
        helpButton.setEnabled(false);
        progressBar.setIndeterminate(true); // Show a loading animation on the progress bar
        progressBar.setString("Connecting to server...");
    }

    private void showErrorAndExit() {
        JOptionPane.showMessageDialog(this, "Could not load questions. The server might be busy or down.", "Load Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    private void initializeQuiz() {
        this.userAnswers = new String[questions.size()][1];
        progressBar.setIndeterminate(false);
        progressBar.setMaximum(questions.size());
        option1.setVisible(true);
        option2.setVisible(true);
        option3.setVisible(true);
        option4.setVisible(true);
        nextButton.setEnabled(true);
        helpButton.setEnabled(true);
        displayQuestion();
    }

    // --- The rest of your code remains largely the same ---

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
        int optionStartY = 420; int optionGap = 50;
        option1 = new JRadioButton(); option2 = new JRadioButton(); option3 = new JRadioButton(); option4 = new JRadioButton();
        JRadioButton[] options = { option1, option2, option3, option4 };
        groupOptions = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i].setBounds(150, optionStartY + i * optionGap, screenWidth - 300, 30);
            options[i].setBackground(new Color(255, 245, 230));
            options[i].setFont(new Font("Dialog", Font.PLAIN, 20));
            add(options[i]);
            groupOptions.add(options[i]);
        }
        int buttonWidth = 200; int buttonHeight = 40; int bottomPadding = 100;
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
        progressBar = new JProgressBar();
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
        if(questionIndex >= questions.size()) return; // Safety check
        String selectedAnswer = "";
        if (groupOptions.getSelection() != null) {
            selectedAnswer = groupOptions.getSelection().getActionCommand();
        }
        userAnswers[questionIndex][0] = selectedAnswer;
        Question currentQuestion = questions.get(questionIndex);
        boolean isCorrect = selectedAnswer.trim().equals(currentQuestion.getCorrectAnswer().trim());
        UserResponse response = new UserResponse(this.userName, this.rollNo, currentQuestion.getId(), selectedAnswer, isCorrect);
        quizService.submitAnswer(response);
    }

    private void calculateAndShowScore() {
        score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers[i][0].equals(questions.get(i).getCorrectAnswer())) {
                score += 10;
            }
        }
        setVisible(false);
        new Score(userName, rollNo, score, questions, userAnswers);
    }

    public void resumeTimer() { timer.start(); }
    public String getUserName() { return this.userName; }
    public int getScore() { return this.score; }
    public String getRollNo() { return this.rollNo; }
    public List<Question> getQuestions() { return this.questions; }
    public String[][] getUserAnswers() { return this.userAnswers; }
}