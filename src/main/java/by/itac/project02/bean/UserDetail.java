package by.itac.project02.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "user_details")
public class UserDetail implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user_details")
	private int idUserDetail;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;

	public UserDetail() {}

	public UserDetail(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public int getIdUserDetail() {
		return idUserDetail;
	}

	public void setIdUserDetail(int idUserDetail) {
		this.idUserDetail = idUserDetail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
