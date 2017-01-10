package org.launchcode.restaurant.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.restaurant.models.Post;
import org.launchcode.restaurant.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface PostDao extends CrudRepository<Post, Integer> {
    
    List<Post> findByAuthor(Employee u);
    
    // TODO - add method signatures as needed
	List<Post> findAll();
	Post findByUid(int Uid);
}
