import java.util.ArrayList;
import java.util.HashSet;


public class Enter
{
	public static void main(String[] args)
	{
		SearchParams params = new SearchParams();
		HashSet<Product> products = new HashSet<Product>();
		products.add(new Product("A"));
		products.add(new Product("B"));
		products.add(new Product("C"));
		params.groceryList = products;
		params.origin = new Location(0, 0);
		ArrayList<Location> route = GrocerySolver.getInstance().solve(params);
		System.out.println("ROUTE IS");
		//Should probably return store routes instead of routes.... meh.
		for (Location loc : route)
		{
			System.out.println("LOCATION : " + loc.x + " " + loc.y);
			
		}
	}

}
