package com.mcausevic.events.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mcausevic.events.models.Comment;


@Repository
public interface CommentRepo extends CrudRepository<Comment, Long>{
	List<Comment>findAll();
	 Optional<Comment> findById(Long id);

}
