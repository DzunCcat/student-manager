package main.java.management.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.management.model.Grade;
import main.java.management.util.DataBaseConnection;

public class GradeDAO {
	private Connection connection;
	
	public GradeDAO () throws SQLException {
		connection = DataBaseConnection.getConnection(); 
	}

    public boolean createGrade(Grade grade) throws SQLException {
        String query = "INSERT INTO Grade (student_id, subject, score, grade_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement state = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            state.setInt(1, grade.getStudentId());
            state.setString(2, grade.getSubject());
            state.setInt(3, grade.getScore());
            state.setDate(4, java.sql.Date.valueOf(grade.getGradeDate()));
            
            int rowsInserted = state.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = state.getGeneratedKeys();
                if (generatedKeys.next()) {
                    grade.setGradeId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Grade getGradeById(int gradeId) throws SQLException {
        Grade grade = null;
        String query = "SELECT * FROM Grade WHERE grade_id = ?";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            state.setInt(1, gradeId);
            ResultSet resultset = state.executeQuery();
            if (resultset.next()) {
                grade = new Grade(
                        resultset.getInt("grade_id"),
                        resultset.getInt("student_id"),
                        resultset.getString("subject"),
                        resultset.getInt("score"),
                        resultset.getDate("grade_date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grade;
    }

    public List<Grade> getAllGrades() throws SQLException {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT * FROM Grade";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            ResultSet resultset = state.executeQuery();
            while (resultset.next()) {
                Grade grade = new Grade(
                        resultset.getInt("grade_id"),
                        resultset.getInt("student_id"),
                        resultset.getString("subject"),
                        resultset.getInt("score"),
                        resultset.getDate("grade_date").toLocalDate()
                );
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public boolean updateGrade(Grade grade) throws SQLException {
        String query = "UPDATE Grade SET student_id = ?, subject = ?, score = ?, grade_date = ? WHERE grade_id = ?";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            state.setInt(1, grade.getStudentId());
            state.setString(2, grade.getSubject());
            state.setInt(3, grade.getScore());
            state.setDate(4, java.sql.Date.valueOf(grade.getGradeDate()));
            state.setInt(5, grade.getGradeId());
            
            int rowsInserted = state.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteGrade(int gradeId) throws SQLException {
        String query = "DELETE FROM Grade WHERE grade_id = ?";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            state.setInt(1, gradeId);
            int rowsDeleted = state.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
