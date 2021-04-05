package com.smartcity.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "offers")
public class Offer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String desc;
	
	
	@Column(name = "employer")
	private String employer;

	//   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//    @JoinColumn(name = "clientID")
//	  private User client;
	
	public Offer(){}
	
	public Offer(UserApplication cc) {
		super();
	}

	public Offer(String name, String desc) {
		super();
		this.name = name;
		this.desc = desc;
	}
	
//	public Offer(String name, String desc) {
	//	super();
	//	this.name = name;
	//	this.desc = desc;
	//	this.client=client;
	//}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getEmployer() {
		return employer;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	//  public void setClient(User client){
	//	    this.client = client;
	//	  }
		  
	//	  public User getClient(){
	//	    return this.client;
		//  }
	
}
