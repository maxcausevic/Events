package com.mcausevic.events.models;

import java.util.Date;
import java.util.List;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="users")
public class User {
	 @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long id;
	    @NotNull
	    @Size(min=2, max=75)
	    private String firstName;
	    @NotNull
	    @Size(min=2, max=75)
	    private String lastName;
	    
	   @Email(message="Email must be valid")
	    private String email;
	   
	   @NotNull
	   @Size(min=2, max=75)
	   private String state;
	   
	    
	    @Size(min=5, message="Password must be greater than 5 characters")
	    private String password;
	    
	    @Transient
	    private String passwordConfirmation;
	    
	    @Column(updatable=false)
	    @DateTimeFormat(pattern="yyyy-MM-dd")
	    private Date createdAt;
	    @DateTimeFormat(pattern="yyyy-MM-dd")
	    private Date updatedAt;
	    @ManyToMany(fetch = FetchType.LAZY)
		@JoinTable( 
				name = "users_events", 
				joinColumns = @JoinColumn(name = "user_id"), 
		        inverseJoinColumns = @JoinColumn(name = "event_id"))
	    
	    private List<Event>attending;
	    @OneToMany(mappedBy="host",fetch = FetchType.LAZY)
	    private List<Event>events;
	    
	    
	    public User() {
	    }
	    public User(String firstName, String lastName, String email, String password, String state) {
	    	this.firstName = firstName;
	    	this.lastName = lastName;
	    	this.email = email;
	    	this.password = password;
	    	this.state = state;
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
		public String getPasswordConfirmation() {
			return passwordConfirmation;
		}
		public void setPasswordConfirmation(String passwordConfirmation) {
			this.passwordConfirmation = passwordConfirmation;
		}
		
		
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
		public Date getUpdatedAt() {
			return updatedAt;
		}
		public void setUpdatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
		}
		public List<Event> getAttending() {
			return attending;
		}
		public void setAttending(List<Event> attending) {
			this.attending = attending;
		}
		public List<Event> getEvents() {
			return events;
		}
		public void setEvents(List<Event> events) {
			this.events = events;
		}
		// other getters and setters removed for brevity
	    @PrePersist
	    protected void onCreate(){
	        this.createdAt = new Date();
	    }
	    @PreUpdate
	    protected void onUpdate(){
	        this.updatedAt = new Date();
	    }
	}

