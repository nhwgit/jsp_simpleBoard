package member.model;

import java.util.Date;

public class Member {
	private String id;
	private String name;
	private String password;
	private int grade;
	private Date regDate;
	
	public Member(String id, String name, String password, int grade, Date regDate) {
		this.id= id;
		this.name = name;
		this.password = password;
		this.regDate = regDate;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	public boolean matchPassword(String pwd) {
		return password.equals(pwd);
	}
}
