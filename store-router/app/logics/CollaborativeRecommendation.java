import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

public class CollaborativeRecommendation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ComputeRelation();
		RecomputeListRemoved();
		NewListAdded();
		RecomputeListChanged();
		
		ShowRecommendations();
	}
	
	/**
	 * Get all user's shopping list.
	 * For all distinct items, create user list.
	 * Do operation for each distinct 2-pair of the item list.
	 */
	public static void ComputeRelation()
	{
		
	}
	
	/**
	 * For each item in the shopping list, re-generate matrix:
	 * if item exists:
	 * append a 0 for each item not in the list
	 * append a 1 for each item in the list
	 * else:
	 * create item with all 0s with last one 1.
	 */
	public static void NewListAdded()
	{
		
	}
	
	/**
	 * What if a user deletes a list? 
	 */
	public static void RecomputeListRemoved()
	{
		
	}
	
	/**
	 * What if a user changes a previously saved list?
	 */
	public static void RecomputeListChanged()
	{
		
	}
	
    /**
	 * For each item in the shopping list, look at all 2-pairs with that item,
	 * and pick the highest value.
	 * From that list, pick highest 5.
	 * Display to user.
	 */
	public static void ShowRecommendations()
	{
		// Initial testing data.
		ArrayList<ArrayList<String>> shoppingLists = new ArrayList<ArrayList<String>>();
		ArrayList<String> slA = new ArrayList<String>();
		ArrayList<String> slB = new ArrayList<String>();
		ArrayList<String> slC = new ArrayList<String>();
		
		slA.add("item1");
		slA.add("item3");
		
		slB.add("item2");
		slB.add("item3");
		
		slC.add("item2");
		
		shoppingLists.add(slA);
		shoppingLists.add(slB);
		shoppingLists.add(slC);
		
		ArrayList<String> uniqueListOfItems = new ArrayList<String>();
		
		for(ArrayList<String> l : shoppingLists)
		{
			for (String i : l)
			{
				if (!uniqueListOfItems.contains(i))
				{
					uniqueListOfItems.add(i);
				}
			}
		}
		
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
		for (String item: uniqueListOfItems)
		{
			ArrayList<Integer> userList = new ArrayList<Integer>();
			for (int i = 0; i < shoppingLists.size(); i++)
			{
				userList.add(shoppingLists.get(i).contains(item) ? 1: 0);
			}
			matrix.add(userList);
		}
		
		Map<String, Double> values = new HashMap<String, Double>();

		for (int i = 0; i < matrix.size(); i++)
		{
			for (int j = i+1; j < matrix.size(); j++)
			{
				String key = uniqueListOfItems.get(i)+uniqueListOfItems.get(j);
				values.put(key, CalculateValue(matrix.get(i), matrix.get(j)));
				System.out.println(key + " - " + values.get(key));
			}
		}

	}
	
	/**
	 * Example: l1 = (1, 0, 0) and l2 = (1, 1, 0)
	 * Output = [(1)(1) + (0)(1) + (0)(0)] / [ ((1^2+0^2+0^2)^0.5) ((1^2+1^2+0^2)^0.5) ]
	 * Output = [1] / [ (1) (1.414) ]
	 * Output = 0.707
	 */
	public static double CalculateValue(ArrayList<Integer> l1, ArrayList<Integer> l2)
	{
		int numUsers = l1.size();
		if (l1.size() != l2.size())
		{
			return 0.0;
		}
		
		int temp1 = 0;
		for (int i = 0; i < numUsers; i++)
		{
			temp1+= l1.get(i)*l2.get(i);
		}
    	
    	int temp2 = 0;
    	for (int i = 0; i < numUsers; i++)
		{
      		temp2 += l1.get(i)*l1.get(i);
    	}
    	
    	int temp3 = 0;
    	for (int i = 0; i < numUsers; i++)
		{
      		temp3 += l2.get(i)*l2.get(i);
    	}
    	
    	double tx = Math.sqrt(temp2)*Math.sqrt(temp3);

		return (temp2 == 0 || temp3 == 0) ? 0 : temp1/tx;
	}

}
