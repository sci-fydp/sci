
public class Grocery
{
	double unitPrice;
	Product product;
	
	
	public Product getProduct()
	{
		return new Product(product);
	}
}
