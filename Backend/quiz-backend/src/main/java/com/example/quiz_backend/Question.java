package com.example.quizbackend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String correctAnswer;

    // --- IMPORTANT ---
    // Your IDE can generate these for you:
    // 1. A no-argument constructor: public Question() {}
    // 2. Getters and Setters for all fields (getId, setId, getQuestionText, etc.)
}