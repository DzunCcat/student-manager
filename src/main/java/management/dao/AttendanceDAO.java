package main.java.management.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.management.model.Attendance;
import main.java.management.util.DataBaseConnection;

public class AttendanceDAO {
	private Connection connection;
	
	public AttendanceDAO () throws SQLException{
		connection = DataBaseConnection.getConnection(); 
	}
	
    public boolean createAttendance(Attendance attendance) {
        String query = "INSERT INTO Attendance (student_id, date, status) VALUES (?, ?, ?)";
        try (PreparedStatement state = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            state.setInt(1, attendance.getStudentId());
            state.setDate(2, java.sql.Date.valueOf(attendance.getDate()));
            state.setString(3, attendance.getStatus());
            
            int rowsInserted = state.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = state.getGeneratedKeys();
                if (generatedKeys.next()) {
                    attendance.setAttendanceId(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Attendance getAttendanceById(int attendanceId) throws SQLException {
        Attendance attendance = null;
        String query = "SELECT * FROM Attendance WHERE attendance_id = ?";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            state.setInt(1, attendanceId);
            ResultSet resultset = state.executeQuery();
            if (resultset.next()) {
                attendance = new Attendance(
                        resultset.getInt("attendance_id"),
                        resultset.getInt("student_id"),
                        resultset.getDate("date").toLocalDate(),
                        resultset.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendance;
    }

    public List<Attendance> getAllAttendance() throws SQLException {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT * FROM Attendance";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            ResultSet resultset = state.executeQuery();
            while (resultset.next()) {
                Attendance attendance = new Attendance(
                        resultset.getInt("attendance_id"),
                        resultset.getInt("student_id"),
                        resultset.getDate("date").toLocalDate(),
                        resultset.getString("status")
                );
                attendances.add(attendance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendances;
    }

    public boolean updateAttendance(Attendance attendance) throws SQLException {
        String query = "UPDATE Attendance SET student_id = ?, date = ?, status = ? WHERE attendance_id = ?";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            state.setInt(1, attendance.getStudentId());
            state.setDate(2, java.sql.Date.valueOf(attendance.getDate()));
            state.setString(3, attendance.getStatus());
            state.setInt(4, attendance.getAttendanceId());
            
            int rowsInserted = state.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAttendance(int attendanceId) throws SQLException {
        String query = "DELETE FROM Attendance WHERE attendance_id = ?";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            state.setInt(1, attendanceId);
            int rowsDeleted = state.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
