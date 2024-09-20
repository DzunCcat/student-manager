package main.java.management.model;

import java.time.LocalDate;
import java.time.Period;

public class Student {
	private int studentId;
	private int userId;
	private LocalDate birth;
	private String department;
	private int gradeLevel;
	private String email;
	private String phone;
	private String address;
	
	public Student() {
		
	}
	
	
	public Student (int studentId,int userId, LocalDate birth, String department, int gradeLevel, String email, String phone, String address) {
		this.studentId = studentId;
		this.userId = userId;
		this.birth = birth;
		this.department = department;
		this.gradeLevel = gradeLevel;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	
	// Getters and setters
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public LocalDate getBirth() {
		return birth;
	}
	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}
    public int getAge() {
        if (birth != null) {
            return Period.between(birth, LocalDate.now()).getYears();
        }
        return 0;
    }
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(int gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String adress) {
		this.address = adress;
	}
	
}
