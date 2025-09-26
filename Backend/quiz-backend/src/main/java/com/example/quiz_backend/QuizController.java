package com.example.quiz_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class QuizController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    private UserResponseRepository userResponseRepository;

    @GetMapping("/api/questions")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @PostMapping("/api/scores")
    public QuizResult saveScore(@RequestBody QuizResult quizResult) {
        return quizResultRepository.save(quizResult);
    }

    @PostMapping("/api/responses")
    public UserResponse saveResponse(@RequestBody UserResponse userResponse) {
        return userResponseRepository.save(userResponse);
    }
}