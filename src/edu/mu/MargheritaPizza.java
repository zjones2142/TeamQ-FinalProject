package edu.mu;

import java.util.*;
import edu.mu.pizzaEnums.Toppings;

public class MargheritaPizza extends AbstractPizza{
	
	private static final double DEFAULT_PRICE = 2.50;
	
	public MargheritaPizza() {
		super();
		toppingList.add(Toppings.TOMATO);
		toppingList.add(Toppings.CHEESE);
		priceWithoutToppings = DEFAULT_PRICE;
	}

	//copy constructor
	public MargheritaPizza(MargheritaPizza pizza) {
		this();
		this.toppingList = new ArrayList<>(pizza.getToppingList());
		this.priceWithoutToppings = pizza.getPriceWithoutToppings();
		this.totalPrice = pizza.getTotalPrice();
		this.pizzaOrderID = pizza.getPizzaOrderID();
		this.cookingStrategy = pizza.getCookingStrategy();
		this.cookingPrice = pizza.getCookingPrice();
	}
	
	@Override
	protected double addToppingsToPrice(double priceWithoutToppings) {
		double total = priceWithoutToppings; //initial price without toppings
		for(Toppings topping : toppingList) {
			total += topping.getPrice(); //add the price for each topping
		}
		return total;
	}
	
	@Override
	public double updatePizzaPrice() {
		totalPrice = addToppingsToPrice(priceWithoutToppings);
		totalPrice += cookingPrice;
		return totalPrice;
	}
}
