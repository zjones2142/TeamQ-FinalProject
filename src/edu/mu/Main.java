package edu.mu;

import java.util.Scanner;

import edu.mu.pizzaEnums.CookingStyleType;
import edu.mu.pizzaEnums.PizzaType;
import edu.mu.pizzaEnums.Toppings;

public class Main {
	public static void main(String[] args) {
		// Instantiate a pizzaOrder, perform operations based on the requirements.
		PizzaOrder order = new PizzaOrder();
		/*Adds pizza(s) to the cart, selects cooking strategies for the pizza(s) in the cart,
		prints pizza order cart. Calls checkout to calculate the bill, throws exception if
		triggered.*/
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("What pizza would you like (1-4):\n1. Margherita\n2. Vegetarian\n3. Hawaiian\n4. Supreme");
		int pizzaOrder = scanner.nextInt();

		//add pizza to the cart
		switch (pizzaOrder) {
			case 1:
				order.addPizzaToCart(PizzaType.MARGHERITA);
				break;
			case 2:
				order.addPizzaToCart(PizzaType.VEGETARIAN);
				break;
			case 3:
				order.addPizzaToCart(PizzaType.HAWAIIAN);
				break;
			case 4:
				order.addPizzaToCart(PizzaType.SUPREME);
			default:
				break;
		}
		
		
		System.out.println("How would you like it cooked?\n1. Brick Oven\n2. Conventional Oven\n3. Microwave");
		int style = scanner.nextInt();

		//select cooking style for pizza on the cart
		int orderID = 0; 	
		for(int i = 0; i < order.getPizzaOrderList().size(); i++){
			orderID = i+1;
			switch (style) {
				case 1:
					order.selectCookingStrategyByPizzaOrderID((i + 1), CookingStyleType.BRICK_OVEN);
					break;
				case 2:
					order.selectCookingStrategyByPizzaOrderID((i + 1), CookingStyleType.CONVENTIONAL_OVEN);
					break;
				case 3:
					order.selectCookingStrategyByPizzaOrderID((i + 1), CookingStyleType.MICROWAVE);
					break;
				default:
					break;
			}
		}
		
		System.out.println("Would you like to add (1) or remove (2) toppings? Enter any other number to skip.");
        int toppingChoice = scanner.nextInt();
        
        if (toppingChoice == 1 || toppingChoice == 2) {
            System.out.println("Select a topping:\n1. Tomato\n2. Cheese\n3. Pineapple\n4. Black Olive\n5. Italian Sausage\n6. Pepperoni\n7. Bell Pepper\n8. Mushroom\n9. Canadian Bacon");
            int toppingSelection = scanner.nextInt();
            Toppings selectedTopping = Toppings.values()[toppingSelection - 1]; // Assuming the order and numbering match exactly
            
            if (toppingChoice == 1) {
                boolean added = order.addNewToppingToPizza(orderID, selectedTopping);
                if (added) {
                    System.out.println("Topping added.");
                } else {
                    System.out.println("Could not add topping (it might already be on the pizza).");
                }
            } else if (toppingChoice == 2) {
                boolean removed = order.removeToppingFromPizza(orderID, selectedTopping);
                if (removed) {
                    System.out.println("Topping removed.");
                } else {
                    System.out.println("Could not remove topping (it might not be on the pizza).");
                }
            }
        }
        scanner.close();

		try{
			double total = order.checkout();
			System.out.println("Total: " + total);
		} catch(Exception e){
			System.err.println(e);
		}
	}
}