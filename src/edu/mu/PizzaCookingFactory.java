package edu.mu;
import edu.mu.pizzaEnums.PizzaType;

public class PizzaCookingFactory {

	private static int orderIDCounter = 1;
	public PizzaCookingFactory() {
		// TODO Auto-generated constructor stub
	}
	public AbstractPizza createPizza(PizzaType pizzaType) {
		AbstractPizza pizza;
		switch (pizzaType) {
		case HAWAIIAN:
			pizza = new HawaiianPizza();
			break;
		case MARGHERITA:
			pizza = new MargheritaPizza();
			break;
		case SUPREME:
			pizza = new SupremePizza();
			break;
		case VEGETARIAN:
			pizza = new VegetarianPizza();
			break;
		default:
			throw new IllegalArgumentException("Unrecognized type" + pizzaType);
		}
		pizza.setPizzaOrderID(orderIDCounter);
		orderIDCounter++;
		return pizza;
	}
}
