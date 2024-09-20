package main.java.management.controller;

import java.sql.SQLException;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import main.java.management.model.Attendance;
import main.java.management.model.Grade;
import main.java.management.model.Student;
import main.java.management.model.User;
import main.java.management.service.AttendanceService;
import main.java.management.service.GradeService;
import main.java.management.service.StudentService;
import main.java.management.service.UserService;
import main.java.management.util.CacheManager;
import main.java.management.util.SessionManager;
import main.java.management.view.base.BaseView;

public class Controller {
    private UserService userService;
    private StudentService studentService;
    private AttendanceService attendanceService;
    private GradeService gradeService;

    private SessionManager sessionManager = SessionManager.getInstance();
    private CacheManager cacheManager = CacheManager.getInstance();

    private BaseView baseView;

    public Controller(UserService userService, StudentService studentService, AttendanceService attendanceService, GradeService gradeService) {
        this.userService = userService;
        this.studentService = studentService;
        this.attendanceService = attendanceService;
        this.gradeService = gradeService;
    }

    public void setBaseView(BaseView baseView) {
        this.baseView = baseView;
    }

    // User methods
    public boolean addUser(User user) throws SQLException {
        boolean result = userService.addUser(user);
        if (result) {
            cacheManager.addUserToCache(user);
        }
        return result;
    }

    public User getUserById(int userId) throws SQLException {
        User user = cacheManager.getUserFromCache(userId);
        if (user == null) {
            user = userService.getUserById(userId);
            if (user != null) {
                cacheManager.addUserToCache(user);
            }
        }
        return user;
    }

    public List<User> getAllUsers() throws SQLException {
        return userService.getAllUsers();
    }

    public boolean updateUser(User user) throws SQLException {
        boolean result = userService.updateUser(user);
        if (result) {
            cacheManager.addUserToCache(user);
        }
        return result;
    }

    public boolean deleteUser(int userId) throws SQLException {
        boolean result = userService.deleteUser(userId);
        if (result) {
            cacheManager.removeUserFromCache(userId);
        }
        return result;
    }

    public User getUserByUsername(String username) throws SQLException {
        for (User user : cacheManager.getAllUsersFromCache()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return userService.getUserByUsername(username);
    }

    // Student methods
    public boolean addStudentWithUser(User user, Student student) throws SQLException {
        SessionManager session = SessionManager.getInstance();
        try {
            session.beginTransaction();

            if (userService.addUser(user)) {
                int userId = user.getCode();
                student.setUserId(userId);
                if (studentService.addStudent(student)) {
                    session.commitTransaction();
                    refreshStudentTable();
                    return true;
                }
            }
            session.rollbackTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                session.rollbackTransaction();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public Student getStudentById(int studentId) throws SQLException {
        return studentService.getStudentById(studentId);
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentService.getAllStudents();
    }

    public boolean updateStudentWithUser(User user, Student student) throws SQLException {
        SessionManager session = SessionManager.getInstance();
        try {
            session.beginTransaction();

            boolean userUpdated = userService.updateUser(user);
            if (userUpdated) {
                boolean studentUpdated = studentService.updateStudent(student);
                if (studentUpdated) {
                    session.commitTransaction();
                    refreshStudentTable();
                    return true;
                }
            }
            session.rollbackTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                session.rollbackTransaction();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteStudentWithUser(int studentId) throws SQLException {
        SessionManager session = SessionManager.getInstance();
        try {
            session.beginTransaction();

            Student student = studentService.getStudentById(studentId);
            if (student != null) {
                boolean studentDeleted = studentService.deleteStudent(studentId);
                if (studentDeleted) {
                    boolean userDeleted = userService.deleteUser(student.getUserId());
                    if (userDeleted) {
                        session.commitTransaction();
                        refreshStudentTable();
                        return true;
                    }
                }
            }
            session.rollbackTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                session.rollbackTransaction();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
    
    public List<Student> searchStudents(String name, Integer studentId, String department, String gradeLevel, String address, Integer age) throws SQLException {
        return studentService.searchStudents(name, studentId, department, gradeLevel, address, age);
    }

    public void refreshStudentTable() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<Student> students = getAllStudents();
                DefaultTableModel newModel = new DefaultTableModel(new Object[]{"ID", "ユーザー名", "名前", "学科", "学年", "メール", "電話", "住所", "年齢"}, 0);
                for (Student student : students) {
                    User user = getUserById(student.getUserId());
                    newModel.addRow(new Object[]{
                        student.getStudentId(),
                        user.getUsername(),
                        user.getName(),
                        student.getDepartment(),
                        student.getGradeLevel(),
                        student.getEmail(),
                        student.getPhone(),
                        student.getAddress(),
                        student.getAge()
                    });
                }
                baseView.refreshTable("StudentManage", newModel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    
    public void recreateView(String viewName) {
        SwingUtilities.invokeLater(() -> {
            baseView.recreateView(viewName);
        });
    }

    // Attendance methods
    public boolean addAttendance(Attendance attendance) throws SQLException {
        return attendanceService.addAttendance(attendance);
    }

    public Attendance getAttendanceById(int attendanceId) throws SQLException {
        return attendanceService.getAttendanceById(attendanceId);
    }

    public List<Attendance> getAllAttendance() throws SQLException {
        return attendanceService.getAllAttendance();
    }

    public boolean updateAttendance(Attendance attendance) throws SQLException {
        return attendanceService.updateAttendance(attendance);
    }

    public boolean deleteAttendance(int attendanceId) throws SQLException {
        return attendanceService.deleteAttendance(attendanceId);
    }

    // Grade methods
    public boolean addGrade(Grade grade) throws SQLException {
        return gradeService.addGrade(grade);
    }

    public Grade getGradeById(int gradeId) throws SQLException {
        return gradeService.getGradeById(gradeId);
    }

    public List<Grade> getAllGrades() throws SQLException {
        return gradeService.getAllGrades();
    }

    public boolean updateGrade(Grade grade) throws SQLException {
        return gradeService.updateGrade(grade);
    }

    public boolean deleteGrade(int gradeId) throws SQLException {
        return gradeService.deleteGrade(gradeId);
    }

    // User session methods
    public void setCurrentUser(User user) {
        sessionManager.setCurrentUser(user);
    }

    public User getCurrentUser() {
        return sessionManager.getCurrentUser();
    }

    public void clearCurrentUser() {
        sessionManager.clearCurrentUser();
    }
}
