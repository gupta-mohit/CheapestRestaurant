package com.mohit.restaurant;
import java.util.HashSet;
import java.util.Set;
/*
 * Model class for menu item
 */
class MenuItem {

	private String name;
	private Double price;
	private Integer restaurantID;	
	private Set<String> setOfDesiredLabelsInCurrItem;	

	public MenuItem(String name, Double price, Integer restaurantID) {
		super();
		this.name = name;
		this.price = price;
		this.restaurantID = restaurantID;
		this.setOfDesiredLabelsInCurrItem = new HashSet<String>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}	
	public Integer getRestaurantID() {
		return restaurantID;
	}	
	public Set<String> getSetOfOrderedItemInCurrItem() {
		return setOfDesiredLabelsInCurrItem;
	}
	public void setSetOfDesiredLabelsInCurrItem(
			Set<String> setOfDesiredLabelsInCurrItem) {
		this.setOfDesiredLabelsInCurrItem = setOfDesiredLabelsInCurrItem;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public MenuItem(String name) {
		super();
		this.name = name;
	}
	@Override
	public String toString() {
		return "Item [name=" + name + ", price=" + price + ", restaurantID=" + restaurantID + "]";
	}	
}