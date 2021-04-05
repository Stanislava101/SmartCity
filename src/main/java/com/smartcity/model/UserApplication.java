package com.smartcity.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "userapplication")
public class UserApplication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "offerID")
	private Long offerID;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="candidateID", referencedColumnName = "id")
	private User client;
	
  //  @ManyToOne(optional = false)
 //   @JoinColumn(name="user", referencedColumnName = "id")
//	  private User client;
	   
	public UserApplication() {
		super();
	}

	public UserApplication(String name, long offerID) {
		super();
		this.name = name;
		this.offerID = offerID;
	//	this.candidateID = candidateID;
	}
	
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
	public Long getOfferID() {
		return offerID;
	}
	public void setOfferID(Long id2) {
		this.offerID = id2;
	}
	public User getClient() {
		return client;
	}
	public void setClient(User client) {
		this.client = client;
	}
	//  public void setClient(User client){
	//	    this.client= client;
	//	  }
		  
	//	  public User getClient(){
	//		  return this.client;
	//	  }
}
