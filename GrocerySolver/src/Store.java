import java.util.List;
import java.util.Set;


public class Store
{
	private Location location;
	private Set<Product> products;
	public String name;
	
	public Store(Location l, Set<Product> gr, String name)
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

	public Set<Product> getProducts()
	{
		return products;
	}

	public void setProducts(Set<Product> products)
	{
		this.products = products;
	}
	
	public boolean hasProduct(Product product)
	{
		return products.contains(product);
	}


}
