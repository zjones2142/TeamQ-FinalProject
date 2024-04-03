package edu.mu;

public class ConventionalOvenCookingStrategy implements ICookingStrategy {
    @Override
    public boolean cook(AbstractPizza pizza) {
        if (pizza == null) {
            return false; // If the pizza is null, we can't cook it, so return false.
        }
        pizza.setCookingPrice(8.0); 
        pizza.setCookingStrategy(this);
        pizza.updatePizzaPrice();
        return true;
    }
}
