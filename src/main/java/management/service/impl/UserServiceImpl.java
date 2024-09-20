package main.java.management.service.impl;

import java.sql.SQLException;
import java.util.List;

import main.java.management.dao.UserDAO;
import main.java.management.model.User;
import main.java.management.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean addUser(User user) throws SQLException {
    	
        return userDAO.addUser(user);
    }

    @Override
    public User getUserById(int id) throws SQLException {
        return userDAO.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUser();
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        return userDAO.updateUser(user);
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        return userDAO.deleteUser(id);
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }
    
    @Override
    public void beginTransaction() throws SQLException {
        userDAO.beginTransaction();
    }

    @Override
    public void commitTransaction() throws SQLException {
        userDAO.commitTransaction();
    }

    @Override
    public void rollbackTransaction() throws SQLException {
        userDAO.rollbackTransaction();
    }
    
}
