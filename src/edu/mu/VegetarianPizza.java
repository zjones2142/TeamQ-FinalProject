package edu.mu;
import java.util.*;
import edu.mu.pizzaEnums.*;

public class VegetarianPizza extends AbstractPizza {
	private static final double DEFAULT_PRICE = 1.50;
	
	public VegetarianPizza() {
		// TODO Auto-generated constructor stub
		super();
		toppingList.add(Toppings.TOMATO);
		toppingList.add(Toppings.CHEESE);
		toppingList.add(Toppings.BELL_PEPPER);
		toppingList.add(Toppings.BLACK_OLIVE);
		toppingList.add(Toppings.MUSHROOM);
		priceWithoutToppings = DEFAULT_PRICE;
		
	}
	public VegetarianPizza(VegetarianPizza pizza) {
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
		// TODO Auto-generated method stub
		double total = priceWithoutToppings;
		for(Toppings topping : toppingList) {
			total = total + topping.getPrice();
		}
		return total;
	}

	@Override
	public double updatePizzaPrice() {
		// TODO Auto-generated method stub
		totalPrice = addToppingsToPrice(priceWithoutToppings);
		totalPrice = totalPrice + cookingPrice;
		return totalPrice;
	}

}
