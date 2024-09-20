package main.java.management.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.management.model.User;
import main.java.management.util.DataBaseConnection;

public class UserDAO {
	private Connection connection;
	
	public UserDAO () throws SQLException{
		connection = DataBaseConnection.getConnection(); 
	}
    
    public User getUserById(int userId) throws SQLException {
        User user = null;
        String query = "SELECT * FROM User WHERE code = ?";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            state.setInt(1, userId);
            ResultSet resultset = state.executeQuery();
            if (resultset.next()) {
                user = new User(
                    resultset.getInt("code"),
                    resultset.getString("username"),
                    resultset.getString("password"),
                    resultset.getString("name"),
                    resultset.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public User getUserByUsername(String username)throws SQLException {
        User user = null;
        String query = "SELECT * FROM User WHERE username = ?";
        try (PreparedStatement state = connection.prepareStatement(query)){
                state.setString(1, username);
                ResultSet resultset = state.executeQuery();
                if (resultset.next()) {
                    user = new User(
                        resultset.getInt("code"),
                        resultset.getString("username"),
                        resultset.getString("password"),
                        resultset.getString("name"),
                        resultset.getString("role")
                    );
                }
            } catch(SQLException e){
                e.printStackTrace();
        }
        return user;
    }
    
    public List<User> getAllUser() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            ResultSet resultset = state.executeQuery();
            while (resultset.next()) {
                User user = new User(
                    resultset.getInt("code"),
                    resultset.getString("username"),
                    resultset.getString("password"),
                    resultset.getString("name"),
                    resultset.getString("role")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public boolean addUser(User user) throws SQLException {
        String query = "INSERT INTO User (username, password, name, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement state = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            state.setString(1, user.getUsername());
            state.setString(2, user.getPassword());
            state.setString(3, user.getName());
            state.setString(4, user.getRole());
            int rowsInserted = state.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = state.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setCode(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
     
    public boolean updateUser(User user) throws SQLException {
        String query = "UPDATE User SET username = ?, password = ?, name = ?, role = ? WHERE code = ?";
        try (PreparedStatement state = connection.prepareStatement(query)) {
            state.setString(1, user.getUsername());
            state.setString(2, user.getPassword());
            state.setString(3, user.getName());
            state.setString(4, user.getRole());
            state.setInt(5, user.getCode());
            int rowsInserted = state.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
     
    public boolean deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM User WHERE code = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }
}
