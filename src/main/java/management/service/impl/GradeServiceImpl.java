package main.java.management.service.impl;

import java.sql.SQLException;
import java.util.List;

import main.java.management.dao.GradeDAO;
import main.java.management.model.Grade;
import main.java.management.service.GradeService;

public class GradeServiceImpl implements GradeService {

    private GradeDAO gradeDAO;

    public GradeServiceImpl(GradeDAO gradeDAO) {
        this.gradeDAO = gradeDAO;
    }

    @Override
    public boolean addGrade(Grade grade) throws SQLException {
        return gradeDAO.createGrade(grade);
    }

    @Override
    public Grade getGradeById(int gradeId) throws SQLException {
        return gradeDAO.getGradeById(gradeId);
    }

    @Override
    public List<Grade> getAllGrades() throws SQLException {
        return gradeDAO.getAllGrades();
    }

    @Override
    public boolean updateGrade(Grade grade) throws SQLException {
        return gradeDAO.updateGrade(grade);
    }

    @Override
    public boolean deleteGrade(int gradeId) throws SQLException {
        return gradeDAO.deleteGrade(gradeId);
    }
}
