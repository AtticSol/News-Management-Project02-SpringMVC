package by.itac.project02.bean;

import java.io.Serializable;
import java.util.Objects;

import by.itac.project02.controller.Role;

public class UserData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String login;
	private String password;	
	private String confirmPassword;
	private String email;
	private String role;
	
	{
		this.role = Role.USER.getTitle();
	}
	
	public UserData() { }

	public UserData(String name, String login, String password, String confirmPassword, String email) {
		this.name = name;
		this.login = login;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.email = email;
		this.role = Role.USER.getTitle();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(confirmPassword, email, login, name, password, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserData other = (UserData) obj;
		return Objects.equals(confirmPassword, other.confirmPassword) && Objects.equals(email, other.email)
				&& Objects.equals(login, other.login) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(role, other.role);
	}

	@Override
	public String toString() {
		return "UserData [name=" + name + ", login=" + login + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", email=" + email + ", role=" + role + "]";
	}

	
		
}
