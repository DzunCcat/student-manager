package main.java.management.service;

import java.sql.SQLException;
import java.util.List;

import main.java.management.model.Student;

public interface StudentService {
    boolean addStudent(Student student) throws SQLException;
    Student getStudentById(int studentId) throws SQLException;
    List<Student> getAllStudents() throws SQLException;
    List<Student> searchStudents(String name, Integer studentId, String department, String gradeLevel, String address, Integer age) throws SQLException;
    boolean updateStudent(Student student) throws SQLException;
    boolean deleteStudent(int studentId) throws SQLException;
}
