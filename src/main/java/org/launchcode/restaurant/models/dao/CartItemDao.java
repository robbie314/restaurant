package org.launchcode.restaurant.models.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.restaurant.models.CartItem;
import org.launchcode.restaurant.models.Customer;
import org.launchcode.restaurant.models.MenuItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface CartItemDao extends CrudRepository<CartItem, Integer> {

	CartItem findByUid(int uid);

	List<CartItem> findByCustomer(Customer customer);

	List<CartItem> findAll();

	CartItem findByCustomerAndMenuItem(Customer customer, MenuItem menuItem);

}
