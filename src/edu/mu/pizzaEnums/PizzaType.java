package edu.mu.pizzaEnums;

public enum PizzaType {
	HAWAIIAN(3.00),
	MARGHERITA(2.50),
	SUPREME(3.50),
	VEGETARIAN(1.50);
	
	private final double toppingPrice;
	
	PizzaType(double toppingPrice) {
		this.toppingPrice = toppingPrice;
	}
	
	public double getToppingPrice() {
		return toppingPrice;
	}
}
