package com.mcausevic.events.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.mcausevic.events.models.Comment;
import com.mcausevic.events.models.Event;
import com.mcausevic.events.models.User;
import com.mcausevic.events.repos.CommentRepo;
import com.mcausevic.events.repos.EventRepos;
import com.mcausevic.events.repos.UserRepo;





@Service
public class EventsService {
private final EventRepos eventRepos;
private final UserRepo userRepo;
private final CommentRepo commentRepo;
    
    public EventsService(EventRepos eventRepos, UserRepo userRepo, CommentRepo commentRepo) {
        this.eventRepos = eventRepos;
        this.userRepo = userRepo;
        this.commentRepo= commentRepo;
    }
    
    // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepo.save(user);
    }
    
    // find user by email
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    
    // find user by id
    public User findUserById(Long id) {
    	Optional<User> u = userRepo.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepo.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
    public List<User>allUsers(){
		return userRepo.findAll();
	}
	public User createUsers(User u) {
		return userRepo.save(u);
	//======================================================//
		// these are my event methods
	//======================================================//
	}
	public List<Event>allEvents(){
		return eventRepos.findAll();
	}
	public Event findEvent(Long id) {
        Optional<Event> optionalEvent = eventRepos.findById(id);
        if(optionalEvent.isPresent()) {
            return optionalEvent.get();
        } else {
            return null;
        }
	}
	public Event findEventById(Long id) {
    	Optional<Event> e = eventRepos.findById(id);
    	
    	if(e.isPresent()) {
            return e.get();
    	} else {
    	    return null;
    	}
    }
//	public Event findEventByName(String name) {
//		Optional<Event> e = eventRepos.findByName(name);
//		if(e.isPresent()) {
//			return e.get();
//		}else {
//			return null;
//		}
	//}
	public Event createEvents(Event e) {
		return eventRepos.save(e);
	}

    public void deleteEvent(Long id) {
		Event event = findEvent(id);
		eventRepos.delete(event);
		
	}
    public Event updateEvent(Long id, String name, String location, String state, String eventdate) {
		Event event1 = findEvent(id);
		event1.setName(name);
		event1.setLocation(location);
		event1.setState(state);
		event1.setEventdate(eventdate);
		
		eventRepos.save(event1);
		return event1;
}

	public Event update(Event event) {
		return eventRepos.save(event);
	}
	//======================================================//
			// these are my connecting methods
	//======================================================//
//	public void addUser(Event event, User attendee ) {
//		event.getUsers().add(attendee);
//		eventRepos.save(event);
//		
//	}
	//======================================================//
	// these are my message methods
//======================================================//
	 public List<Comment>allComments(){
			return commentRepo.findAll();
		}
	public Comment createMessage(Comment c) {
		return commentRepo.save(c);
	}
	public Comment findCommentById(Long id) {
    	Optional<Comment> c = commentRepo.findById(id);
    	
    	if(c.isPresent()) {
            return c.get();
    	} else {
    	    return null;
    	}
    }
}


