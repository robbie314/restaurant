package org.launchcode.restaurant.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.restaurant.models.MenuItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface MenuItemDao extends CrudRepository<MenuItem, Integer> {

    MenuItem findByUid(int uid);
    
    List<MenuItem> findAll();
    
    // TODO - add method signatures as needed
    MenuItem findByName(String name);
}

