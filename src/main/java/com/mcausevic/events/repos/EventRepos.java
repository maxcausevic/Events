package com.mcausevic.events.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mcausevic.events.models.Event;
import com.mcausevic.events.models.User;



@Repository
public interface EventRepos extends CrudRepository<Event, Long> {
	Event findByName(String name);
    List<Event>findAll();
    void deleteById(Long id);
    Event save(Event e);
    Optional<Event> findById(Long id);
   //
}
