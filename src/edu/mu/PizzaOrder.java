package edu.mu;

import java.util.*;
import edu.mu.pizzaEnums.*;

public class PizzaOrder {
	
	private PizzaCookingFactory pizzaFactory;
	private ICookingStrategy cookingStrategy;
	private List<AbstractPizza> pizzaOrderList; 

	public PizzaOrder() {
		this.pizzaFactory = new PizzaCookingFactory();
		this.pizzaOrderList = new ArrayList<>();
	}
	
	public void printListOfToppingsByPizzaOrderID(int orderID) {
		AbstractPizza pizza = getPizzaByOrderID(orderID);
		if(pizza != null){
			System.out.println("Toppings for pizza " + orderID + ":");
			for(Toppings topping : pizza.getToppingList()){
				System.out.println(topping);
			}
		} else {
			System.out.println("Pizza order " + orderID + " not found");
		}
	}
	
	public void printPizzaOrderCart(int orderID) {
		System.out.println("Pizza Order List:");
		for(AbstractPizza pizza : pizzaOrderList){
			System.out.println(pizza.toString());
		}
	}
	
	public AbstractPizza getPizzaByOrderID(int orderID) {
		for(AbstractPizza pizza : pizzaOrderList){
			if(pizza.getPizzaOrderID() == orderID){
				return pizza;
			}
		}
		System.out.println("Pizza order " + orderID + " not found");
		return null;
	}
	
	public boolean addPizzaToCart(PizzaType pizzaType) {
		AbstractPizza pizza = pizzaFactory.createPizza(pizzaType);
		int b4size = pizzaOrderList.size();
		pizzaOrderList.add(pizza);
		if(b4size == pizzaOrderList.size()) return false;
		else return true;
	}
	
	public boolean addNewToppingToPizza(int orderID, Toppings topping) {
		for (AbstractPizza pizza : pizzaOrderList) {
            if (pizza.getPizzaOrderID() == orderID && !pizza.getToppingList().contains(topping)) {
                pizza.getToppingList().add(topping);
                pizza.updatePizzaPrice();
                return true;
            }
        }
        return false;
	}
	
	public boolean removeToppingFromPizza(int orderID, Toppings topping) {
		for (AbstractPizza pizza : pizzaOrderList) {
            if (pizza.getPizzaOrderID() == orderID && pizza.getToppingList().contains(topping)) {
                pizza.getToppingList().remove(topping);
                pizza.updatePizzaPrice();
                return true;
            }
        }
        return false;
	}
	
	public boolean isThereAnyUncookedPizza() {
		for (AbstractPizza pizza : pizzaOrderList) {
            if (pizza.getCookingStrategy() == null) {
                return true;
            }
        }
        return false;
	}
	
	public double checkout() throws Exception {
		if (isThereAnyUncookedPizza()) {
            throw new Exception("There are uncooked pizzas in your order.");
        }
        double total = 0;
        for (AbstractPizza pizza : pizzaOrderList) {
            total += pizza.getTotalPrice();
        }
        return total;
	}
	
	public boolean selectCookingStrategyByPizzaOrderID(int orderID, CookingStyleType cookingStrategyType) {
	    for (AbstractPizza pizza : pizzaOrderList) {
	        if (pizza.getPizzaOrderID() == orderID) {
	            ICookingStrategy strategy = null; // Initialize strategy reference

	            // Determine the correct cooking strategy based on the cookingStrategyType
	            switch (cookingStrategyType) {
	                case MICROWAVE:
	                    strategy = new MicrowaveCookingStrategy();
	                    break;
	                case CONVENTIONAL_OVEN:
	                    strategy = new ConventionalOvenCookingStrategy();
	                    break;
	                case BRICK_OVEN:
	                    strategy = new BrickOvenCookingStrategy();
	                    break;
	                default:
	                    return false; // If the cooking strategy type is unrecognized, return false.
	            }

	            if (strategy != null) {
	                pizza.setCookingStrategy(strategy); // Assign the strategy to the pizza
	                strategy.cook(pizza); // Now we can safely call cook method since strategy is not null
	                return true; // Cooking strategy successfully assigned and executed
	            }
	        }
	    }
	    return false; // If no pizza with the given ID was found in the order
	}


	//get pizza order list
	public List<AbstractPizza> getPizzaOrderList(){
		return this.pizzaOrderList;
	}
}
