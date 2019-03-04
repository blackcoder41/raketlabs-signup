package com.raketlabs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole {
	@Id
	@GeneratedValue(generator = "user_roles_generator")
	@SequenceGenerator(name = "user_roles_generator", sequenceName = "user_roles_sequence", initialValue = 1000)
	private Long id;

	@Column(name = "username")
	private String userName;
	
	@Column(name = "role")
	private String role;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	

}