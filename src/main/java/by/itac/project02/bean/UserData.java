package by.itac.project02.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import by.itac.project02.controller.atribute.Role;


@Entity
@Table(name = "users")
public class UserData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
		
	@Column(name = "login")
	private String login;
	
	@Column(name = "password")
	private String password;
			
	@Column(name = "role")
	private String role;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_details_id_user_details")
	private UserDetail userDetail;
	
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "reporter",
			cascade = {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	private List<NewsData> createdNews;
	
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "reporter",
			cascade = {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	private List<InfoAboutUpdatedNews> updatedNews;
	
	public List<InfoAboutUpdatedNews> getUpdatedNews() {
		return updatedNews;
	}

	public void setUpdatedNews(List<InfoAboutUpdatedNews> updatedNews) {
		this.updatedNews = updatedNews;
	}

	{
		this.role = Role.USER.getTitle();
		this.userDetail = new UserDetail("name", "email");
	}
	
	public UserData() { }

	public UserData(String name, String login, String password, 
			String email,
			UserDetail userDetail) {
		this.login = login;
		this.password = password;
		this.role = Role.USER.getTitle();
		this.userDetail = userDetail; 
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}	
	
	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}
	
	public UserDetail getUserDetail() {
		return userDetail;
	}

	public List<NewsData> getCreatedNews() {
		return createdNews;
	}

	public void setCreatedNews(List<NewsData> createdNews) {
		this.createdNews = createdNews;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, role, userDetail);
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
		return id == other.id && Objects.equals(login, other.login) && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && Objects.equals(userDetail, other.userDetail);
	}

	@Override
	public String toString() {
		return "UserData [id=" + id + ", login=" + login + ", password=" + password + ", role=" + role + ", userDetail="
				+ userDetail + "]";
	}

	
		
}
