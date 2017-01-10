package org.launchcode.restaurant.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.restaurant.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface EmployeeDao extends CrudRepository<Employee, Integer> {

    Employee findByUid(int uid);
    
    List<Employee> findAll();
    
    // TODO - add method signatures as needed
    Employee findByUsername(String username);
}
