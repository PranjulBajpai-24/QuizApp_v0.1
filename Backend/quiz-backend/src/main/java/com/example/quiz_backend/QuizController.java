package com.example.quiz_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // ADD THIS IMPORT
import org.springframework.web.bind.annotation.RequestBody;   // ADD THIS IMPORT
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class QuizController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired // ADD THIS
    private QuizResultRepository quizResultRepository;

    @GetMapping("/api/questions")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // ADD THIS NEW METHOD
    @PostMapping("/api/scores")
    public QuizResult saveScore(@RequestBody QuizResult quizResult) {
        return quizResultRepository.save(quizResult);
    }
}