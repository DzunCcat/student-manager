package main.java.management.util;

import java.sql.Connection;
import java.sql.SQLException;

import main.java.management.model.User;

public class SessionManager {
	
	private static SessionManager instance;
	private User currentUser;
	private Connection connection;

	private SessionManager() {
		try {
			this.connection = DataBaseConnection.getConnection();
			this.connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void clearCurrentUser() {
		this.currentUser = null;
	}

	public Connection getConnection() {
		return connection;
	}

	public void beginTransaction() throws SQLException {
		connection.setAutoCommit(false);
	}

	public void commitTransaction() throws SQLException {
		connection.commit();
	}

	public void rollbackTransaction() throws SQLException {
		connection.rollback();
	}

	public void close() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
