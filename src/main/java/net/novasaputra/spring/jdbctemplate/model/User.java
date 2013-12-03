package net.novasaputra.spring.jdbctemplate.model;
/**
 * @author Nova Saputra
 */
public class User {
	private int id;
	private String username;
	private String password;
	private int role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User-name:"+username+"; pass:"+password+"; role:"+role+"; id:"+id;
	}
}
