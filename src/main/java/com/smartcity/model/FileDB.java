package com.smartcity.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "files")
public class FileDB {
	
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  private String name;

  private String type;

  @Lob
  private byte[] data;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
	public User user;
  
  public FileDB() {}

  public FileDB(User user) {
	  this.user= user;
  }
  
  public FileDB(String name, byte[] data, User user) {
	    this.name = name;
	    this.data = data;
	    this.user=user;
	  }

  public FileDB(String name, String type, byte[] data, User user) {
    this.name = name;
    this.type = type;
    this.data = data;
    this.user=user;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}