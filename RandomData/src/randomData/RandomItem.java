package randomData;

public class RandomItem
{
	public String name;
	public int cost;
	
	public RandomItem(String n, int c)
	{
		name = n;
		cost = c;
	}
	
	@Override
	public String toString()
	{
		return name + " " +  cost + " ";
	}
}
