package com.smartcity.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "eventsvisiters")
public class EventVisiter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "eventID")
	private Long eventID;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="participant", referencedColumnName = "id")
	private User participant;
	
	public EventVisiter() {
		super();
	}

	public EventVisiter(String name, long eventID,User participant) {
		super();
		this.name = name;
		this.eventID = eventID;
		this.participant=participant;
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
	public Long getEventID() {
		return eventID;
	}
	public void setEventID(Long id2) {
		this.eventID = id2;
	}
	public User getParticipant() {
		return participant;
	}
	public void setParticipant(User participant) {
		this.participant = participant;
	}
}
