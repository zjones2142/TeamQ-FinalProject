package edu.mu;
import java.util.*;
import edu.mu.pizzaEnums.Toppings;

public class SupremePizza extends AbstractPizza{

	private static final double DEFAULT_PRICE = 3.50;
	
	public SupremePizza() {
		// TODO Auto-generated constructor stub
		super();
		toppingList.add(Toppings.TOMATO);
		toppingList.add(Toppings.CHEESE);
		toppingList.add(Toppings.BELL_PEPPER);
		toppingList.add(Toppings.ITALIAN_SAUSAGE);
		toppingList.add(Toppings.PEPPERONI);
		toppingList.add(Toppings.BLACK_OLIVE);
		toppingList.add(Toppings.MUSHROOM);
		priceWithoutToppings = DEFAULT_PRICE;
		
	}

	public SupremePizza(SupremePizza pizza) {
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
