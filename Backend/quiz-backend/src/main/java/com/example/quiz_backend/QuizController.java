package com.example.quizbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class QuizController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/api/questions")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}