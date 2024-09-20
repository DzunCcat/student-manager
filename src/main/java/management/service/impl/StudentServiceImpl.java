package main.java.management.service.impl;

import java.sql.SQLException;
import java.util.List;

import main.java.management.dao.StudentDAO;
import main.java.management.model.Student;
import main.java.management.service.StudentService;

public class StudentServiceImpl implements StudentService {

    private StudentDAO studentDAO;

    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public List<Student> searchStudents(String name, Integer studentId, String department, String gradeLevel, String address, Integer age) throws SQLException {
        return studentDAO.searchStudents(name, studentId, department, gradeLevel, address, age);
    }
    
    @Override
    public boolean addStudent(Student student) throws SQLException {
        return studentDAO.addStudent(student);
    }

    @Override
    public Student getStudentById(int studentId) throws SQLException {
        return studentDAO.getStudentById(studentId);
    }

    @Override
    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudent();
    }

    @Override
    public boolean updateStudent(Student student) throws SQLException {
        return studentDAO.updateStudent(student);
    }

    @Override
    public boolean deleteStudent(int studentId) throws SQLException {
        return studentDAO.deleteStudent(studentId);
    }
}
