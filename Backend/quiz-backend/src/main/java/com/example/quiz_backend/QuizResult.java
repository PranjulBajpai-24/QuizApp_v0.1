package com.example.quiz_backend;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String rollNo;
    private int score;

    // Constructors, Getters, and Setters
    public QuizResult() {}

    public QuizResult(String userName, String rollNo, int score) {
        this.userName = userName;
        this.rollNo = rollNo;
        this.score = score;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}