package com.smartcity.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "userdata")
public class UserData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
    @OneToOne(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinColumn(name = "user_data", referencedColumnName = "id")
	public User user;
	
	@Column(name = "data")
	private String data;
	
	
	public UserData() {
		
	}
	
	public UserData(User user,String data) {
		this.user = user;
		this.data = data;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
