package org.launchcode.blogz.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.blogz.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Integer> {

    User findByUid(int uid);
    
    List<User> findAll();
    
    // TODO - add method signatures as needed
    User findByUsername(String username);
}
