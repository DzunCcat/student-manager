package main.java.management.model;

public class User {
    private int code; 
    private String name;
    private String username;
    private String password;
    private String role;
    
    public User() {
    }

    public User(int code, String username, String password, String name, String role) {
        this.code = code;
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    // Getters and setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
