package main.java.management.service;

import java.sql.SQLException;
import java.util.List;

import main.java.management.model.Grade;

public interface GradeService {
    boolean addGrade(Grade grade) throws SQLException;
    Grade getGradeById(int gradeId) throws SQLException;
    List<Grade> getAllGrades() throws SQLException;
    boolean updateGrade(Grade grade) throws SQLException;
    boolean deleteGrade(int gradeId) throws SQLException;
}
