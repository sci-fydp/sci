package randomData;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class RandomData
{
	static final String OUTPUT_DISTANCE_FILE_NAME = "outputDistance.txt";
	static final String OUTPUT_INVENTORY_FILE_NAME = "outputInventory.txt";
	static final String INPUT_STORE_NAME = "stores.txt";
	static final String INPUT_LOCATION_NAME = "locations.txt";
	static final String INPUT_ITEMS_NAME = "items.txt";
	final static Charset ENCODING = StandardCharsets.UTF_8;

	
	static final int MAX_DISTANCE = 2000;
	static final int MIN_DISTANCE = 1;
	
	static final int maxStoreValue = 100;
	static final int minStoreValue = 5;
	
	static final int maxItemValue = 1000;
	static final int minItemValue = 1;
	
	static final int seed = 0;
	static Random rand = new Random(seed);
	
	public static void main(String[] args)
	{
		List<String> storeNames;
		List<String> itemNames;
		List<String> locationNames;
		
		int[][] distances = null;
		List<RandomItem> items;
		
		try
		{
			storeNames = readSmallTextFile(INPUT_STORE_NAME);
			locationNames = readSmallTextFile(INPUT_LOCATION_NAME);
			itemNames = readSmallTextFile(INPUT_ITEMS_NAME);
			PrintWriter writer = new PrintWriter(OUTPUT_DISTANCE_FILE_NAME, "UTF-8");
			StringBuilder builder = new StringBuilder();
			
			//Write distances
			int totalLocations = storeNames.size() + locationNames.size();
			distances = new int[totalLocations][totalLocations];
			
			for (int row = 0; row < totalLocations; row++)
			{
				for (int col = 0; col < totalLocations; col++)
				{
					if (row == col || distances[row][col] != 0)
					{
						continue;
					}
					int distance = rand.nextInt(MAX_DISTANCE - MIN_DISTANCE) + MIN_DISTANCE;
					
					distances[row][col] = distance;
					distances[col][row] = distance;
				}
			}
			
			int row = 0;
			for (String store : storeNames)
			{
				builder.setLength(0);
				builder.append(store);
				builder.append("\t\t\t");
				for (int col = 0; col < totalLocations; col++)
				{
					
					builder.append(distances[row][col]);
					builder.append(" ");
				}
				
				writer.println(builder.toString());
				//System.out.println(store);
				row++;
			}
			
			for (String location : locationNames)
			{
				builder.setLength(0);
				builder.append(location);
				builder.append("\t\t\t");
				for (int col = 0; col < totalLocations; col++)
				{
					builder.append(distances[row][col]);
					builder.append(" ");
				}
				
				writer.println(builder.toString());
				//System.out.println(store);
				row++;
			}
			
			
			//writer.println("The first line");
			//writer.println("The second line");
			writer.close();
			
			
			writer = new PrintWriter(OUTPUT_INVENTORY_FILE_NAME, "UTF-8");
			HashSet<String> storeItems = new HashSet<String>();
			///Write INVENTORY
			for (String store : storeNames)
			{
				storeItems.clear();
				builder.setLength(0);
				builder.append(store);
				builder.append("\t\t\t");
				
				///whatever... get items
				for (int i = 0; i < 3; i++)
				{
					String itemName = itemNames.get(rand.nextInt(itemNames.size()));
					if (storeItems.contains(itemName))
						continue;
					int cost = rand.nextInt(maxItemValue - minItemValue) + minItemValue;
					builder.append(new RandomItem(itemName, cost).toString());
					storeItems.add(itemName);
				}
				
				writer.println(builder.toString());
				//System.out.println(store);
			}
			writer.close();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	private static List<String> readSmallTextFile(String aFileName)
			throws IOException
	{
		Path path = Paths.get(aFileName);
		return Files.readAllLines(path, ENCODING);
	}

}
