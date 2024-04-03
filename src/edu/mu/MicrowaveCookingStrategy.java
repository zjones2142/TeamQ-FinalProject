package edu.mu;

public class MicrowaveCookingStrategy implements ICookingStrategy {
    @Override
    public boolean cook(AbstractPizza pizza) {
        if (pizza == null) {
            return false; // If the pizza is null, we can't cook it, so return false.
        }
        pizza.setCookingPrice(1.0); // Set the additional price for Microwave cooking.
        pizza.setCookingStrategy(this); // Assign this strategy to the pizza.
        pizza.updatePizzaPrice(); // Update the total price of the pizza.
        return true; // Cooking process successful.
    }
}