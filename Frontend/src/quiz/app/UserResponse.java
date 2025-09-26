package quiz.app;

public class UserResponse {
    private String userName;
    private String rollNo;
    private Long questionId;
    private String selectedAnswer;

    // CHANGED: Simplified the field name to match the backend
    private boolean correct;

    public UserResponse(String userName, String rollNo, Long questionId, String selectedAnswer, boolean correct) {
        this.userName = userName;
        this.rollNo = rollNo;
        this.questionId = questionId;
        this.selectedAnswer = selectedAnswer;
        this.correct = correct; // Assign to the simplified field
    }
}