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
import javax.persistence.OneToOne;
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
	
	@Column(name="approved")
	private Boolean approved;
	
//	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER)
	@ManyToOne(optional = false)
	@JoinColumn(name="candidateID", referencedColumnName = "id")
	private User client;
	
	 @OneToOne(mappedBy = "application")
	  private Message message;
	
	   
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
	public Boolean getApproved() {
		return approved;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	public User getClient() {
		return client;
	}
	public void setClient(User client) {
		this.client = client;
	}
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
}
