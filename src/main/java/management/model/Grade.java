package main.java.management.model;

import java.time.LocalDate;

public class Grade {
    private int gradeId;
    private int studentId;
    private String subject;
    private int score;
    private LocalDate gradeDate;

    public Grade() {
    }

    public Grade(int gradeId, int studentId, String subject, int score, LocalDate gradeDate) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.subject = subject;
        this.score = score;
        this.gradeDate = gradeDate;
    }

    // Getters and setters
    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(LocalDate gradeDate) {
        this.gradeDate = gradeDate;
    }
}
