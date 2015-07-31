
import java.io.*;
import java.util.*;


public class TrendAnalysis {

	public static void main(String[] argv) {
		List<ShoppingList> allList = new ArrayList<ShoppingList>(0);
		
		ShoppingList day1 = new ShoppingList(1);
		day1.items.add("apple");
		day1.items.add("banana");
		day1.items.add("carrot");

		ShoppingList day2 = new ShoppingList(8);
		day2.items.add("apple");
		day2.items.add("carrot");

		ShoppingList day3 = new ShoppingList(15);
		day3.items.add("apple");
		day3.items.add("dandelion");
		day3.items.add("eggs");

		ShoppingList day4 = new ShoppingList(22);
		day4.items.add("apple");

		allList.add(day1);
		allList.add(day2);
		allList.add(day4);
		allList.add(day3);


		// TODO: print for testing; remove
		for (int i = 0; i < allList.size(); i++) {
			ShoppingList s = allList.get(i);
			System.out.print(s.getDate() + ": ");
			for (int j = 0; j < s.getList().size(); j++) {
				System.out.print(s.getList().get(j) + " ");
			}
			System.out.println();
		}
		System.out.println();


		HashMap<String, TreeSet<Integer>> map = organize_occurrences(allList);
		HashMap<String, Double> freq = compute_frequency(4, map);
		// TODO: update the db with these frequencies





	} // end main


	/*
		We use TreeSet instead of ArrayList since it adds element in sorted order
		(which we want for item names and their purchase dates, for usability purposes)

		Note that TreeSet is not synchronized.
	 */

	// TODO: add the dates in order instead of having to reorder every list of dates?
	public static HashMap<String, TreeSet<Integer>> organize_occurrences(List<ShoppingList> lists) {
		HashMap<String, TreeSet<Integer>> occurrences = new HashMap<String, TreeSet<Integer>>();

		for (int itr_list = 0; itr_list < lists.size(); itr_list++) {
			int date = lists.get(itr_list).getDate();
			List<String> list = lists.get(itr_list).getList();

			for (int itr_items = 0; itr_items < list.size(); itr_items++) {
				String item = list.get(itr_items);

				TreeSet<Integer> dates = new TreeSet<Integer>();
				if (occurrences.containsKey(item)) {
					// item already exists in hashmap; add the date of occurence to list of dates
					dates = occurrences.get(item);
				}
				dates.add(date);
				occurrences.put(item, dates);
			}
		}


		// TODO: remove		
		// Print the contents of the hashmap by key
		Set<String> items = occurrences.keySet();
		Iterator<String> it = items.iterator();
		while (it.hasNext()) {
			String ele = it.next();
			System.out.print(ele + ": ");
			ArrayList<Integer> dates = occurrences.get(ele);
			for (int i = 0; i < dates.size(); i++) {
				System.out.print(dates.get(i) + " ");
			}
			System.out.println();
		}
		
		return occurrences;
	}

	// For each item, compute the frequency of showing up on shopping lists.
	// @return : item name(String) -> frequency(Double) hashmap
	public static HashMap<String, Double> compute_frequency(double num_dates, HashMap<String, ArrayList<Integer>> occurrences) {
		HashMap<String, Double> freq = new HashMap<String, Double>();
		Set<String> items = occurrences.keySet();
		Iterator<String> it = items.iterator();
		while (it.hasNext()) {
			String item = it.next();
			ArrayList<Integer> dates = occurrences.get(item);
			freq.put(item, dates.size() / num_dates);
		}

		// TODO: remove
		// Print the contents of the hashmap by key
		System.out.println("\n<Frequencies>");
		items = freq.keySet();
		it = items.iterator();
		while (it.hasNext()) {
			String ele = it.next();
			System.out.print(ele + ": " + freq.get(ele) + '\n');
		}

		return freq;
	}




	/* HELPERS */

	// string to int converter
	public static boolean isInteger(String str) {
		if (str == null) return false;
		int length = str.length();
		if (length == 0) return false;

		for (int i = 0; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	} // end isInteger


}