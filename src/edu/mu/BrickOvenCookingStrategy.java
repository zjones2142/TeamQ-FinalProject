package edu.mu;

public class BrickOvenCookingStrategy implements ICookingStrategy {

	@Override
	public boolean cook(AbstractPizza pizza) {
		if (pizza == null) {
            return false; // If the pizza is null, we can't cook it, so return false.
        }
        pizza.setCookingPrice(10.0); // Set the additional price for Brick Oven cooking.
        pizza.setCookingStrategy(this); // Assign this strategy to the pizza.
        pizza.updatePizzaPrice(); // Update the total price of the pizza.
        return true;
	}

}

