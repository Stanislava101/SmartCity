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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;

@Entity
@Table(name =  "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
    @Size(min = 10, max = 12, message = "Invalid phone number")
	@Column(name = "phone_no")
	private String phoneNo;
    
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;

 @OneToOne(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER,mappedBy = "user")
  private UserData data;
 
 @OneToOne(mappedBy = "user")
 private FileDB cv;

	
    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.REMOVE}, fetch=FetchType.EAGER,mappedBy = "client")
    private Set<UserApplication> offer;
    
    @OneToMany(mappedBy = "participant")
    private Set<EventVisiter> event;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
		            name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
				            name = "role_id", referencedColumnName = "id"))
	
	private Collection<Role> roles;
	
	public User() {
		
	}
	
	
	public User(long id) {
		this.id = id;
	}
	
	public User(String firstName, String lastName, String phoneNo, String email, Collection<Role> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo=phoneNo;
		this.email = email;
//		this.password = password;
		this.roles = roles;
	}
	
	public User(String firstName, String lastName, String phoneNo, String email, String password, Collection<Role> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo=phoneNo;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
	
	public User(String firstName, String lastName,String phoneNo, String email, String password, Collection<Role> roles, UserData data,FileDB cv) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNo = phoneNo;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.data = data;
		this.cv=cv;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	public UserData getData() {
		return data;
	}
	public void setData(UserData data) {
		this.data = data;
	}
	
		public void setOffer(Set<UserApplication> offer){
		    this.offer = offer;
		  }
		  
		  public Set<UserApplication> getOffer(){
		    return this.offer;
		  }
		  
			public void setParticipant(Set<EventVisiter> event){
			    this.event = event;
			  }
			  
			  public Set<EventVisiter> getParticipant(){
			    return this.event;
			  }
				public FileDB getCV() {
					return cv;
				}
				public void setCV(FileDB cv) {
					this.cv = cv;
				}

}
