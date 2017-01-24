package org.launchcode.restaurant.models;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "menu_item")
public class MenuItem extends AbstractEntity {

	private String name;
	private String description;
	private String price;
	private String status;
	private List<OrderHistory> orderHistories;

	// n

	public MenuItem() {
	}

	public MenuItem(String name, String description, String price) {

		super();

		if (!isValidPrice(price)) {
			throw new IllegalArgumentException("Invalid price");
		}

		this.name = name;
		this.description = description;
		this.price = price;
		this.status = "Active";
	}

	public MenuItem(String name, String description, String price, String status) {

		super();

		if (!isValidPrice(price)) {
			throw new IllegalArgumentException("Invalid price");
		}

		this.name = name;
		this.description = description;
		this.price = price;
		this.status = status;
	}

	@NotNull
	@Column(name = "name", unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@Column(name = "price")
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@NotNull
	@Column(name = "status")
	public String getStatus() {
		return status;

	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static boolean isValidPrice(String price) {

		Pattern validPricePattern = Pattern.compile("[0-9]+([,.][0-9]{1,2})?");
		Matcher matcher = validPricePattern.matcher(price);
		return matcher.matches();

	}
	@OneToMany
    @JoinColumn(name = "order_history_uid")
    public List<OrderHistory> getOrderHistories() {
        return orderHistories;
    }
	
	public void setOrderHistories(List<OrderHistory> orderHistories) {
		this.orderHistories = orderHistories;
	}
	public void addOrderHistory(OrderHistory orderHistory) {
		orderHistories.add(orderHistory);
	}
}
