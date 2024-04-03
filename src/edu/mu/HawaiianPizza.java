package edu.mu;
import java.util.*;
import edu.mu.pizzaEnums.*;

public class HawaiianPizza extends AbstractPizza{
	private static final double DEFAULT_PRICE = 3.00;

	public HawaiianPizza() {
		// TODO Auto-generated constructor stub
		super();
		toppingList.add(Toppings.CANADIAN_BACON);
		toppingList.add(Toppings.CHEESE);
		toppingList.add(Toppings.PINEAPPLE);
		priceWithoutToppings = DEFAULT_PRICE;
		
	}
	
	public HawaiianPizza(HawaiianPizza pizza) {
		
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
