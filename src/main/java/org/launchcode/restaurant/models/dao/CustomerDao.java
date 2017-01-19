package org.launchcode.restaurant.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.restaurant.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface CustomerDao extends CrudRepository<Customer, Integer> {

    Customer findByUid(int uid);
    
    List<Customer> findAll();
    
    // TODO - add method signatures as needed
    Customer findByPhoneNumber(String phoneNumber);
}
