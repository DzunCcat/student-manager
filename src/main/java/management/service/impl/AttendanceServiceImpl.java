package main.java.management.service.impl;

import java.sql.SQLException;
import java.util.List;

import main.java.management.dao.AttendanceDAO;
import main.java.management.model.Attendance;
import main.java.management.service.AttendanceService;

public class AttendanceServiceImpl implements AttendanceService {

    private AttendanceDAO attendanceDAO;

    public AttendanceServiceImpl(AttendanceDAO attendanceDAO) {
        this.attendanceDAO = attendanceDAO;
    }

    @Override
    public boolean addAttendance(Attendance attendance) throws SQLException {
        return attendanceDAO.createAttendance(attendance);
    }

    @Override
    public Attendance getAttendanceById(int attendanceId) throws SQLException {
        return attendanceDAO.getAttendanceById(attendanceId);
    }

    @Override
    public List<Attendance> getAllAttendance() throws SQLException {
        return attendanceDAO.getAllAttendance();
    }

    @Override
    public boolean updateAttendance(Attendance attendance) throws SQLException {
        return attendanceDAO.updateAttendance(attendance);
    }

    @Override
    public boolean deleteAttendance(int attendanceId) throws SQLException {
        return attendanceDAO.deleteAttendance(attendanceId);
    }
}
