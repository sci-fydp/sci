
public class Product
{

	int quantity = 0;
	String name;
	public Product()
	{
	}
	public Product(String s)
	{
		name = s;
	}
	public Product(Product product)
	{
		name = product.name;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o == this)
			return true;
		if (!(o instanceof Product))
		{
			return false;
		}
		else
		{
			Product product = (Product)o;
			return product.name.equals(this.name);
		}
	}
	
	@Override 
	public int hashCode() {
        return name.hashCode();
    }
	
}
