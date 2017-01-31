package org.launchcode.restaurant.models;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cart_item")
public class CartItem extends AbstractEntity {
	
	private Customer customer;
	private MenuItem menuItem;
	private int quantity;
	private double price;
	
	public CartItem() {
	}

	public CartItem(Customer customer, MenuItem menuItem) {

		super();

		this.customer = customer;
		this.menuItem = menuItem;
		this.quantity = 1;
		this.price = Double.parseDouble(menuItem.getPrice());
		customer.addCartItem(this);
	}



	@NotNull
	@Column(name = "price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@NotNull
	@Column(name = "quantity")
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void addQuantity() {
		this.quantity += 1;
		this.price += Double.parseDouble(menuItem.getPrice());
	}
	
	public void subtractQuantity() {
		this.quantity -= 1;
		this.price -= Double.parseDouble(menuItem.getPrice());
		
	}
	
	@ManyToOne
	public Customer getCustomer() {
		return customer;
		
	}
	@SuppressWarnings("unused")
	public void setCustomer(Customer customer) {
		this.customer = customer;
		
	}
	
	@ManyToOne
	public MenuItem getMenuItem() {
		return menuItem;
		
	}
	@SuppressWarnings("unused")
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
		
	}
}
