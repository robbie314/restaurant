package org.launchcode.blogz.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.blogz.models.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface PostDao extends CrudRepository<Post, Integer> {

    Post findByUid(int uid);
    
    List<Post> findByAuthor(int authorId);
    
    List<Post> findAll();
	
}
