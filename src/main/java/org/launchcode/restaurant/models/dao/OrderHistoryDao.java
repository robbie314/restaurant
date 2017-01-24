package org.launchcode.restaurant.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.restaurant.models.Customer;
import org.launchcode.restaurant.models.MenuItem;
import org.launchcode.restaurant.models.OrderHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface OrderHistoryDao extends CrudRepository<OrderHistory, Integer> {
    
    List<Customer> findByCustomer(Customer c);
    List<MenuItem> findByMenuItem(MenuItem mi);
    
    // TODO -
	List<OrderHistory> findAll();
	OrderHistory findByUid(int Uid);
}

