import java.util.List;


public class Store
{
	private Location location;
	private List<Product> products;
	public String name;
	
	public Store(Location l, List<Product> gr, String name)
	{
		location = l;
		products = gr;
		this.name = name;
	}
	
	public Location getLocation()
	{
		return location;
	}
	public void setLocation(Location location)
	{
		this.location = location;
	}

	public List<Product> getProducts()
	{
		return products;
	}

	public void setProducts(List<Product> products)
	{
		this.products = products;
	}

}
