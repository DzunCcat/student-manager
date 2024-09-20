package main.java.management.service;

import java.sql.SQLException;
import java.util.List;

import main.java.management.model.Attendance;

public interface AttendanceService {
    boolean addAttendance(Attendance attendance) throws SQLException;
    Attendance getAttendanceById(int attendanceId) throws SQLException;
    List<Attendance> getAllAttendance() throws SQLException;
    boolean updateAttendance(Attendance attendance) throws SQLException;
    boolean deleteAttendance(int attendanceId) throws SQLException;
}
