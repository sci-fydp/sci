import java.util.ArrayList;
import java.util.List;


public class RandomDatabase
{

	private static RandomDatabase instance;
	private static ArrayList<Store> allStores;
	
	String []randomInitData = new String[] {
			"ABCD COMPANY", "0", "10", "A, B, C, D",
			"AB COMPANY", "10", "0", "A, B",
			"BD COMPANY", "5", "5", "B, D",
			"AB COMPANY", "7", "3", "A, B"
	};
	private RandomDatabase()
	{
		allStores = new ArrayList<Store>();
		for (int i = 0; i < randomInitData.length; i+=4)
		{
			String name = randomInitData[i];
			int locx = Integer.parseInt(randomInitData[i+1]);
			int locy = Integer.parseInt(randomInitData[i+2]);
			String[] productNames = randomInitData[i+3].split(",");
			List<Product> products = new ArrayList<Product>();
			for (String productName : productNames)
			{
				products.add(new Product(productName.trim()));
			}
			Store store = new Store(new Location(locx, locy), products, name);
			allStores.add(store);
		}
		
	}
	
	public static RandomDatabase getInstance()
	{
		if (instance == null)
		{
			instance = new RandomDatabase();
		}
		return instance;
	}
	
	public ArrayList<Store> getStoresInRegion(double beginRange,
			double endRange, Location origin)
	{
		ArrayList<Store> stores = new ArrayList<Store>();
		for (Store store : allStores)
		{
			double distance = Location.getDistance(origin, store.getLocation());
			if (distance >= beginRange && distance <= endRange)
			{
				stores.add(store);
			}
		}
		return stores;
	}

	
}
