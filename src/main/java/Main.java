package main.java;

import java.sql.SQLException;

import main.java.management.controller.Controller;
import main.java.management.dao.AttendanceDAO;
import main.java.management.dao.GradeDAO;
import main.java.management.dao.StudentDAO;
import main.java.management.dao.UserDAO;
import main.java.management.service.impl.AttendanceServiceImpl;
import main.java.management.service.impl.GradeServiceImpl;
import main.java.management.service.impl.StudentServiceImpl;
import main.java.management.service.impl.UserServiceImpl;
import main.java.management.view.common.LoginView;

public class Main {
    public static void main(String[] args) {
    	try {
            UserDAO userDAO = new UserDAO();
            UserServiceImpl userService = new UserServiceImpl(userDAO);
            StudentDAO studentDAO = new StudentDAO();
            StudentServiceImpl studentService = new StudentServiceImpl(studentDAO);

            AttendanceDAO attendanceDAO = new AttendanceDAO();
            AttendanceServiceImpl attendanceService = new AttendanceServiceImpl(attendanceDAO);

            GradeDAO gradeDAO = new GradeDAO();
            GradeServiceImpl gradeService = new GradeServiceImpl(gradeDAO);

            Controller controller = new Controller(userService, studentService, attendanceService, gradeService);
            new LoginView(controller).setVisible(true);
    	}catch (SQLException e) {
    		e.printStackTrace();
		}
    }
}
