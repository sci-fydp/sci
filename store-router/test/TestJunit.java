import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

public class TestJunit {
	
	@Test
	public void testFrequency() {
		String item1 = "Apple";
		String item2 = "Banana";
		String item3 = "Orange";
		String item4 = "Eggs";
		String date1 = ":June 30, 2015";
		String date2 = ":July 30, 2015";
		String date3 = ":July 31, 2015";
		String date4 = ":August 01, 2015";
		String date5 = ":August 02, 2015";
		String date6 = ":August 03, 2015";
		String date7 = ":August 04, 2015";
	
		// Assume for now, date is string starting with ":", in the form <MonthName> <dd>, <yyyy>
		// TODO: Follow a common Shopping List object structure.
		// Test 1
		List<String> slSet1 = new ArrayList<String>();
		slSet1.add(":July 30, 2015");
		slSet1.add("Apple");
	
		HashMap<String, Integer> frequencyMap1 = new HashMap<String, Integer>();
		frequencyMap1.put("Apple", 1);
	
		// Test 2
		List<String> slSet2 = new ArrayList<String>();
		slSet2.add(":July 30, 2015");
		slSet2.add("Apple");
		slSet2.add(":July 31, 2015");
		slSet2.add("Apple");
		slSet2.add(":June 30, 2015");
		slSet2.add("Apple");
	
		HashMap<String, Integer> frequencyMap2 = new HashMap<String, Integer>();
		frequencyMap2.put("Apple", 3);
	
		// Test 3
		List<String> slSet3 = new ArrayList<String>();
		slSet3.add(":July 30, 2015");
		slSet3.add("Apple");
		slSet3.add("Banana");
		slSet3.add(":July 31, 2015");
		slSet3.add("Orange");
	
		HashMap<String, Integer> frequencyMap3 = new HashMap<String, Integer>();
		frequencyMap3.put("Apple", 1);
		frequencyMap3.put("Orange", 1);
		frequencyMap3.put("Banana", 1);
	
		// Test 4
		List<String> slSet4 = new ArrayList<String>();
		slSet4.add(":July 30, 2015");
		slSet4.add("Orange");
		slSet4.add("Eggs");
		slSet4.add(":July 31, 2015");
		slSet4.add("Banana");
		slSet4.add("Apple");
		slSet4.add(":August 01, 2015");
		slSet4.add("Banana");
		slSet4.add("Apple");
		slSet4.add(":August 02, 2015");
		slSet4.add("Apple");
		slSet4.add(":August 03, 2015");
		slSet4.add("Eggs");
	
		HashMap<String, Integer> frequencyMap4 = new HashMap<String, Integer>();
		frequencyMap4.put("Apple", 3);
		frequencyMap4.put("Orange", 1);
		frequencyMap4.put("Banana", 2);
		frequencyMap4.put("Eggs", 2);

		// TODO: Pass in the shopping lists data to TrendFrequency somehow (probably read from DB).
		// Assuming the TrendFrequency object takes in a list object and has a getFrequency method.
		TrendFrequency tf1 = new TrendFrequency(slSet1);
		TrendFrequency tf2 = new TrendFrequency(slSet2);
		TrendFrequency tf3 = new TrendFrequency(slSet3);
		TrendFrequency tf4 = new TrendFrequency(slSet4);
	
		// Assuming getFrequency will return what we want.
		HashMap<String, Integer> result1 = tf1.getFrequency();
		HashMap<String, Integer> result2 = tf2.getFrequency();
		HashMap<String, Integer> result3 = tf3.getFrequency();
		HashMap<String, Integer> result4 = tf4.getFrequency();
		
		assertTrue(result1.equals(frequencyMap1));
		assertTrue(result2.equals(frequencyMap2));
		assertTrue(result3.equals(frequencyMap3));
		assertTrue(result4.equals(frequencyMap4));
	}

	@Test
	public void testRecommendation() {
		String item1 = "Apple";
		String item2 = "Banana";
		String item3 = "Orange";
		String item4 = "Eggs";
		String date1 = ":June 30, 2015";
		String date2 = ":July 30, 2015";
		String date3 = ":July 31, 2015";
		String date4 = ":August 01, 2015";
		String date5 = ":August 02, 2015";
		String date6 = ":August 03, 2015";
		String date7 = ":August 04, 2015";
	
		// Test 1
		recommendation1 = "Banana";
		List<String> userList1 = new ArrayList<String>();
		userList1.add(":August 04, 2015");
		userList1.add("Orange");
		userList1.add("Eggs");
		userList1.add("Apple");

		// TODO: Pass in the shopping lists data to TrendRecoomendation somehow (probably read from DB) + current list.
		// Assuming the TrendRecommendation object takes in a list object and has a getRecommendation method.
		// Assume the above shopping lists are already in the database (slSet1, slSet2, slSet3, slSet4)
		TrendRecommendation tf1 = new TrendRecommendation(userList1);

		String result1 = tr1.getRecommendation();
		
		assertEquals(result1, recommendation1);
	}
}
