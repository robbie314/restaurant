package org.launchcode.restaurant.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_history")
public class OrderHistory extends AbstractEntity {

	private Customer customer;
	private MenuItem menuItem;
	private Date orderDate;
	private int quantity;
	
	
	
	public OrderHistory() {}
	
	public OrderHistory(Customer customer, MenuItem menuItem, int quantity) {
		
		super();
		
		this.customer = customer;
		this.menuItem = menuItem;
		this.quantity = quantity;
		
		this.orderDate = new Date();
		
		customer.addOrderHistory(this);
		menuItem.addOrderHistory(this);
	}
	
	
	@ManyToOne
	public Customer getCustomer() {
		return customer;
	}
	
	@SuppressWarnings("unused")
	private void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@ManyToOne
	public MenuItem getMenuItem() {
		return menuItem;
	}
	
	@SuppressWarnings("unused")
	private void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
	
	@NotNull
	@OrderColumn
	@Column(name = "orderDate")
	public Date getOrderDate() {
		return orderDate;
	}
	
	@SuppressWarnings("unused")
	private void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	@NotNull
	@Column(name = "quantity")
	public int getQuantity() {
		return quantity;
	}
	
	@SuppressWarnings("unused")
	private void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void addQuantity(int quantity) {
		this.quantity += quantity;
		this.orderDate = new Date();
		
	}
}
