package main.java.management.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.management.model.Student;
import main.java.management.util.DataBaseConnection;

public class StudentDAO {
	private Connection connection;
	
	public StudentDAO () throws SQLException{
		connection = DataBaseConnection.getConnection(); 
	}
	
	public List<Student> searchStudents(String name, Integer studentId, String department, String gradeLevel, String address, Integer age) throws SQLException {
	    List<Student> students = new ArrayList<>();
	    String query = "SELECT *, TIMESTAMPDIFF(YEAR, s.birth, CURDATE()) AS age FROM Student s JOIN User u ON s.user_id = u.code WHERE 1=1";

	    if (name != null && !name.isEmpty()) {
	        query += " AND u.name LIKE ?";
	    }
	    if (studentId != null) {
	        query += " AND s.student_id = ?";
	    }
	    if (department != null && !department.isEmpty()) {
	        query += " AND s.department LIKE ?";
	    }
	    if (gradeLevel != null && !gradeLevel.isEmpty()) {
	        query += " AND s.grade_level LIKE ?";
	    }
	    if (address != null && !address.isEmpty()) {
	        query += " AND s.address LIKE ?";
	    }
	    if (age != null) {
	        query += " AND TIMESTAMPDIFF(YEAR, s.birth, CURDATE()) = ?";
	    }

	    try (PreparedStatement state = connection.prepareStatement(query)) {

	        int index = 1;
	        if (name != null && !name.isEmpty()) {
	            state.setString(index++, "%" + name + "%");
	        }
	        if (studentId != null) {
	            state.setInt(index++, studentId);
	        }
	        if (department != null && !department.isEmpty()) {
	            state.setString(index++, "%" + department + "%");
	        }
	        if (gradeLevel != null && !gradeLevel.isEmpty()) {
	            state.setString(index++, "%" + gradeLevel + "%");
	        }
	        if (address != null && !address.isEmpty()) {
	            state.setString(index++, "%" + address + "%");
	        }
	        if (age != null) {
	            state.setInt(index++, age);
	        }

	        ResultSet rs = state.executeQuery();
	        while (rs.next()) {
	            Student student = new Student();
	            student.setStudentId(rs.getInt("student_id"));
	            student.setUserId(rs.getInt("user_id"));
	            student.setBirth(rs.getDate("birth").toLocalDate());
	            student.setDepartment(rs.getString("department"));
	            student.setGradeLevel(rs.getInt("grade_level"));
	            student.setEmail(rs.getString("email"));
	            student.setPhone(rs.getString("phone"));
	            student.setAddress(rs.getString("address"));
	            students.add(student);
	        }
	    }
	    return students;
	}

    
    public boolean addStudent(Student student) throws SQLException {
        String query = "INSERT INTO Student (user_id, birth, department, grade_level, email, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement state = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            state.setInt(1, student.getUserId());
            state.setDate(2, Date.valueOf(student.getBirth()));
            state.setString(3, student.getDepartment());
            state.setInt(4, student.getGradeLevel());
            state.setString(5, student.getEmail());
            state.setString(6, student.getPhone());
            state.setString(7, student.getAddress());
            
            int rowsInserted = state.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = state.getGeneratedKeys();
                if (generatedKeys.next()) {
                    student.setStudentId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Student getStudentById(int studentId) throws SQLException {
        Student student = null;
        String query = "SELECT * FROM Student WHERE student_id = ?";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            state.setInt(1, studentId);
            ResultSet resultset = state.executeQuery();
            if (resultset.next()) {
                student = new Student(
                        resultset.getInt("student_id"),
                        resultset.getInt("user_id"),
                        resultset.getDate("birth").toLocalDate(),
                        resultset.getString("department"),
                        resultset.getInt("grade_level"),
                        resultset.getString("email"),
                        resultset.getString("phone"),
                        resultset.getString("address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }
    
    public List<Student> getAllStudent() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Student";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            ResultSet resultset = state.executeQuery();
            while (resultset.next()) {
                Student student = new Student(
                        resultset.getInt("student_id"),
                        resultset.getInt("user_id"),
                        resultset.getDate("birth").toLocalDate(),
                        resultset.getString("department"),
                        resultset.getInt("grade_level"),
                        resultset.getString("email"),
                        resultset.getString("phone"),
                        resultset.getString("address")
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
     
    public boolean updateStudent(Student student) throws SQLException {
        String query = "UPDATE Student SET user_id = ?, birth = ?, department = ?, grade_level = ?, email = ?, phone = ?, address = ? WHERE student_id = ?";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            state.setInt(1, student.getUserId());
            state.setDate(2, Date.valueOf(student.getBirth()));
            state.setString(3, student.getDepartment());
            state.setInt(4, student.getGradeLevel());
            state.setString(5, student.getEmail());
            state.setString(6, student.getPhone());
            state.setString(7, student.getAddress());
            state.setInt(8, student.getStudentId());
            
            int rowsInserted = state.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
     
    public boolean deleteStudent(int studentId) throws SQLException {
        String query = "DELETE FROM Student WHERE student_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
