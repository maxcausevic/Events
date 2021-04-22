package com.mcausevic.events.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mcausevic.events.models.Comment;
import com.mcausevic.events.models.Event;
import com.mcausevic.events.models.User;
import com.mcausevic.events.services.EventsService;
import com.mcausevic.events.validator.UserValidator;

@Controller
public class MainController {
	private final EventsService eventsService;
	private final UserValidator userValidator;
	public MainController(EventsService eventsService,UserValidator userValidator ) {
		this.eventsService = eventsService;
		this.userValidator = userValidator;
	}
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("user", new User());
		return "index.jsp";
	}
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session,RedirectAttributes redirect){
		  userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "index.jsp";
		}else {
			User u = eventsService.registerUser(user);
			session.setAttribute("userId", u.getId());
			redirect.addFlashAttribute("success", "You have successfully registered!");

			return "redirect:/events";
		}
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model Model, HttpSession session, RedirectAttributes redirect) {
		if (eventsService.authenticateUser(email, password)) {
			User u = eventsService.findByEmail(email);
			session.setAttribute("userId", u.getId());
			redirect.addFlashAttribute("success", "You have successfully logged in!");
			return "redirect:/events";
		}else {
			redirect.addFlashAttribute("error", "Invalid login Credentials!");
			return "redirect:/";
		}
	}
		@RequestMapping("/events")
		public String events(Model model, HttpSession session, RedirectAttributes redirect) {
			Long userId = (Long)session.getAttribute("userId");
			if(userId == null) {
				redirect.addFlashAttribute("please", "Please register or login!");
				return "redirect:/";
			}
			model.addAttribute("user", eventsService.findUserById(userId));
			model.addAttribute("event", new Event());
			model.addAttribute("events", eventsService.allEvents());
			return "allEvents.jsp";
		}
		@RequestMapping("/logout")
		public String logout(HttpSession session) {
			session.invalidate();
			return "redirect:/";
			
		}
		@RequestMapping(value="/createEvent",method=RequestMethod.POST)
		public String createEvent(@Valid @ModelAttribute("event") Event event, BindingResult result, Model model, HttpSession session) {
			User user = eventsService.findUserById((Long)session.getAttribute("userId"));

			if(result.hasErrors()) {
				return "allEvents.jsp";
			}else
				event.setHost(user);
				eventsService.createEvents(event);
				
			return "redirect:/events";
		}
		@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
		public String destroy(@PathVariable("id") Long id) {
			
			eventsService.deleteEvent(id);
			return "redirect:/events";
		}
	
		@RequestMapping("/events/{id}/edit")
		  public String edit(@PathVariable("id") Long id, Model model) {
		     Event event = eventsService.findEvent(id);
		     model.addAttribute("event", event);
		         return "editEvent.jsp";
		}
		@RequestMapping(value="/events/{id}", method=RequestMethod.PUT)
	    public String update(@Valid @ModelAttribute("event") Event event, BindingResult result) {
	        if (result.hasErrors()) {
	            return "editEvent.jsp";
	        } else {
	            eventsService.update(event);
	            return "redirect:/events";
	        }
		}
		@RequestMapping("/showEvent/{id}")
		public String showEvent(Model model, @PathVariable("id") Long id) {
			Event event = eventsService.findEvent(id);
			User user = eventsService.findUserById(id);
			Comment comment = eventsService.findCommentById(id);
			model.addAttribute("event", event);
			model.addAttribute("user", user);
			model.addAttribute("comment", comment);
			
			return "showEvent.jsp";
}
		@RequestMapping(value="/events/addUser/{eventId}", method=RequestMethod.GET)
		public String addUser(@PathVariable("eventId") Long eventId, HttpSession session){
			Event event = eventsService.findEvent(eventId);
			Long userId= (Long) session.getAttribute("userId");
			User loggedInUser = eventsService.findUserById(userId);
			//eventsService.addUser(event, user);
			event.getAttendees().add(loggedInUser);
			eventsService.update(event);
			return "redirect:/events/";
		}
		@RequestMapping(value="/events/removeUser/{eventId}", method=RequestMethod.GET)
		public String removeUser(@PathVariable("eventId") Long eventId, HttpSession session){
			Event event = eventsService.findEvent(eventId);
			Long userId= (Long) session.getAttribute("userId");
			User loggedInUser = eventsService.findUserById(userId);
			//eventsService.addUser(event, user);
			event.getAttendees().remove(loggedInUser);
			eventsService.update(event);
			return "redirect:/events/";
		}
		@RequestMapping(value="/createMessage/{eventId}",method=RequestMethod.POST)
		public String createMessage(@PathVariable("eventId") Long eventId, @RequestParam("content") String content) {
			Comment comment = new Comment();
			comment.setContent(content);
			Event event = eventsService.findEvent(eventId);
			comment.setMessage(event);
				eventsService.createMessage(comment);
				
			return "redirect:/showEvent/" + event.getId();
}
}
