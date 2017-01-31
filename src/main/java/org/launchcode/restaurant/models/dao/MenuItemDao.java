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
    List <MenuItem> findByStatus(String status);
    
    List<MenuItem> findAll();
    
    // d 
  //  @Query(value="SELECT * FROM menu_item mi WHERE BINARY mi.name = BINARY ?1 ", nativeQuery = true)
    MenuItem findByName(String name);
}

