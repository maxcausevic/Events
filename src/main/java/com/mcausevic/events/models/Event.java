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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="events")
public class Event {
	 @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long id;
	    @NotNull
	    @Size(min=2, max=75)
	    private String name;
	    
	    @NotNull
	    @Size(min=2, max=75)
	    private String location;
	    
	    @NotNull
	    private String state;
	    
	    @NotNull
	    private String eventdate;
	    
	    @Column(updatable=false)
	    @DateTimeFormat(pattern="yyyy-MM-dd")
	    private Date createdAt;
	    @DateTimeFormat(pattern="yyyy-MM-dd")
	    private Date updatedAt;
	    
	    @ManyToMany(fetch = FetchType.LAZY)
		@JoinTable( 
				name = "users_events", 
				joinColumns = @JoinColumn(name = "event_id"), 
		        inverseJoinColumns = @JoinColumn(name = "user_id"))
	    private List<User>attendees;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="user_id")
	    private User host;
	    
	   @OneToMany(mappedBy="message",fetch = FetchType.LAZY)
	    private List<Comment>messages;
	   
	    public Event() {
	    	
	    }
	    public Event(String name, String location, String eventdate, String state) {
	    	this.name = name;
	    	this.location = location;
	    	this.eventdate = eventdate;
	    	
	    	this.state = state;
	    }
	    @PrePersist
	    protected void onCreate(){
	        this.createdAt = new Date();
	    }
	    @PreUpdate
	    protected void onUpdate(){
	        this.updatedAt = new Date();
	    }
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
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
		
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getEventdate() {
			return eventdate;
		}
		public void setEventdate(String eventdate) {
			this.eventdate = eventdate;
		}
	
		public void setUpdatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
		}
		public List<User> getAttendees() {
			return attendees;
		}
		public void setAttendees(List<User> attendees) {
			this.attendees = attendees;
		}
		public User getHost() {
			return host;
		}
		public void setHost(User host) {
			this.host = host;
		}
		public List<Comment> getMessages() {
			return messages;
		}
		public void setMessages(List<Comment> messages) {
			this.messages = messages;
		}
		
		
		
	    
}
