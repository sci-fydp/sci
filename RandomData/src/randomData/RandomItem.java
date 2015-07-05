package randomData;

import java.util.Random;

public class RandomItem
{
	public String name;
	public int cost;
	private static final int randomMultiplierPercent = 20;
	Random rand = new Random();
	public RandomItem(String n, int c)
	{
		name = n;
		cost = c;
	}
	
	public RandomItem(String n, String co)
	{
		this(n, Integer.parseInt(co));
	}

	@Override
	public String toString()
	{

		int randomValue = cost * randomMultiplierPercent / 100;
		int costText = cost + rand.nextInt(randomValue * 2) - randomValue;
		return name + " " +  costText + " ";
	}
}
