package quiz.app;

// This is a simple class to hold score data for sending to the backend.
public class QuizResult {
    private String userName;
    private String rollNo;
    private int score;

    public QuizResult(String userName, String rollNo, int score) {
        this.userName = userName;
        this.rollNo = rollNo;
        this.score = score;
    }
}