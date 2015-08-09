
// import java.io.*;
// import java.util.*;
// import java.lang.Math;

// /*
// 	<List of Methods>

// 	HashMap<String, TreeSet<Integer>> organize_occurrences(List<ShoppingList> lists)
// 	 : create a map of <item name -> dates of occurrence>

// 	HashMap<String, Double> compute_frequency(double num_dates, HashMap<String, ArrayList<Integer>> occurrences)
// 	 : compute the frequency of each item appeared in the shopping lists so far


// 	List<String> generate_frequently_bought_items(HashMap<String, Double> frequency_map)
// 	 : generate a potential shopping list only by analyzing frequency of items


// 	List<String> generate_list(HashMap<String, TreeSet<Integer>> occurrences, int generation_date)
// 	 : generate a potential shopping list based on date intervals

// 	double calculate_purchase_probability(ArrayList<Integer> intervals, int date_since_last_purchase)
// 	 : called from generate_list




// 	TODO:
// 	<Priority: High>
// 	* Change all int dates to Date type

// 	<Priority: Low>
// 	* Instead of evaluating items individually, consider looking at a whole shopping list and using metaheuristics



// 	Q) Why use TreeSet to keep track of item occurrence dates?
// 	- prevents duplicate elements, in case the user accidentally input an item more than once on list.
// 		TODO: doesn't prevent duplicate elements (try printing a treeset directly); investigate this
// 	- ordered insert; convenient later in calculating intervals between purchase dates.


//  */


// public class TrendAnalysis {

// 	private static final double FREQUENT_ITEM_PROBABILITY = 0.5;

// 	public static void main(String[] argv) {
// 		List<ShoppingList> allList = new ArrayList<ShoppingList>(0);
		
// 		ShoppingList day1 = new ShoppingList(1);
// 		day1.items.add("apple");
// 		day1.items.add("banana");
// 		day1.items.add("carrot");

// 		ShoppingList day2 = new ShoppingList(8);
// 		day2.items.add("apple");
// 		day2.items.add("carrot");

// 		ShoppingList day3 = new ShoppingList(15);
// 		day3.items.add("apple");
// 		day3.items.add("dandelion");
// 		day3.items.add("eggs");

// 		ShoppingList day4 = new ShoppingList(22);
// 		day4.items.add("apple");

// 		allList.add(day1);
// 		allList.add(day2);
// 		allList.add(day4);
// 		allList.add(day3);


// 		// TODO: print for testing; remove
// 		for (int i = 0; i < allList.size(); i++) {
// 			ShoppingList s = allList.get(i);
// 			System.out.print(s.getDate() + ": ");
// 			for (int j = 0; j < s.getList().size(); j++) {
// 				System.out.print(s.getList().get(j) + " ");
// 			}
// 			System.out.println();
// 		}
// 		System.out.println();


// 		HashMap<String, TreeSet<Integer>> occurrences = organize_occurrences(allList);


// 		List<String> list = generate_list(occurrences, 25);
// 		System.out.println(list);



// 		// TODO: update the db with these frequencies...?


// 	} // end main



// 	/* 
// 		Create a list of dates on which each item appeared in the shopping lists batch
	
// 		Inputs:
// 			List<ShoppingList> lists 	batch of shopping lists

// 		Output:
// 			<item name -> list of dates> mapping
// 	*/
// 	public static HashMap<String, TreeSet<Integer>> organize_occurrences(List<ShoppingList> lists) {
// 		HashMap<String, TreeSet<Integer>> occurrences = new HashMap<String, TreeSet<Integer>>();

// 		for (int itr_list = 0; itr_list < lists.size(); itr_list++) {
// 			int date = lists.get(itr_list).getDate();
// 			List<String> list = lists.get(itr_list).getList();

// 			for (int itr_items = 0; itr_items < list.size(); itr_items++) {
// 				String item = list.get(itr_items);

// 				TreeSet<Integer> dates = new TreeSet<Integer>();
// 				if (occurrences.containsKey(item)) {
// 					// item already exists in hashmap; add the date of occurence to list of dates
// 					dates = occurrences.get(item);
// 				}
// 				dates.add(date);
// 				occurrences.put(item, dates);
// 			}
// 		}


// 		// TODO: remove		
// 		// Print the contents of the hashmap by key
// 		Set<String> items = occurrences.keySet();
// 		Iterator<String> it = items.iterator();
// 		while (it.hasNext()) {
// 			String ele = it.next();
// 			System.out.print(ele + ": ");
// 			TreeSet<Integer> dates = occurrences.get(ele);
// 			for (int i = 0; i < dates.size(); i++) {
// 				System.out.print(dates);
// 			}
// 			System.out.println();
// 		}
		
// 		return occurrences;
// 	} // end organize_occurences


// 	/*
// 		For a given item, compute its frequency

// 	   	Inputs:
// 	   		int num_lists 					number of shopping lists so far
// 	   		String item_name					name of the item of concern
// 	   		ArrayList<Integer> occurrences 	list of dates in which the item was included while shopping
	 	
// 	 	Output:
// 	   		Frequency of the item
// 	*/
// 	public static double compute_frequency(int num_lists, String item_name, ArrayList<Integer> occurrences) {
// 		return occurrences.size() / num_lists;

// 		// TODO: erase below after use
// 		// HashMap<String, Double> freq = new HashMap<String, Double>();
// 		// Set<String> items = occurrences.keySet();
// 		// Iterator<String> it = items.iterator();
// 		// while (it.hasNext()) {
// 		// 	String item = it.next();
// 		// 	ArrayList<Integer> dates = occurrences.get(item);
// 		// 	freq.put(item, dates.size() / num_dates);
// 		// }

// 		// // TODO: remove
// 		// // Print the contents of the hashmap by key
// 		// System.out.println("\n<Frequencies>");
// 		// items = freq.keySet();
// 		// it = items.iterator();
// 		// while (it.hasNext()) {
// 		// 	String ele = it.next();
// 		// 	System.out.print(ele + ": " + freq.get(ele) + '\n');
// 		// }

// 		// return freq;
// 	}


// 	/* 
// 		Generate a list of frequently appeared items.
// 		This wouldn't be the actual shopping list, but would be more used in asking, for example,
// 		what are the most frequently bought item by the user.
	
// 		Inputs:
// 	  		HashMap<String, Double> frequency_map 	map of item to its appearance frequency

// 		Output:
// 			List of item names
// 	*/
// 	public static List<String> generate_frequently_bought_items(HashMap<String, Double> frequency_map) {
// 		ArrayList<String> final_list = new ArrayList<String>();

// 		Set<String> items = frequency_map.keySet();
// 		Iterator<String> it = items.iterator();
// 		while (it.hasNext()) {
// 			String item = it.next();
// 			// TODO: is 50% appearance good enough?
// 			if (frequency_map.get(item) >= FREQUENT_ITEM_PROBABILITY) {
// 				final_list.add(item);
// 			}

// 		}

// 		return final_list;
// 	}


// 	/*
// 	  	Generate the probable shopping list for the specified date.

// 	  	Few assumptions/notes
// 		- ALWAYS put the item on list if it has been more than 'maxInterval' days since last shopping of that item.
// 		- does not take into account of recency of the previous purchases


// 	 	Inputs:
// 	  		HashMap<String, TreeSet<Integer>> occurrences	: map of item by their dates of purchase
// 	  		int generation_date : date of generating the list (may not necessarily be today)

// 		Output:
// 	  		List of item names

// 	 */
// 	public static List<String> generate_list(HashMap<String, TreeSet<Integer>> occurrences, int generation_date) {
// 		ArrayList<String> final_list = new ArrayList<String>();

// 		Set<String> items = occurrences.keySet();
// 		Iterator<String> items_it = items.iterator();

// 		// Loop for each item:
// 		while (items_it.hasNext()) {
// 			String name = items_it.next();
// 			TreeSet<Integer> dates = occurrences.get(name);
			
// 			if (dates.size() == 0) {
// 				// TODO: error
// 			} else if (dates.size() == 1) {
// 				// TODO: for now an item bought for the first time will never appear on a newly generated list
// 				// 		Later this should be fixed to incorporate item relativity values.
// 			} else {
// 				ArrayList<Integer> intervals = new ArrayList<Integer>();	// no need for this to be sorted
// 				Iterator<Integer> dates_it = dates.iterator();
// 				// iterate through the dates and calculate intervals between consecutive elements
// 				int cur = dates_it.next();
// 				while (dates_it.hasNext()) {
// 					int next = dates_it.next();
// 					intervals.add(next - cur);
// 					cur = next;
// 				}

// 				int date_since_last_purchase = generation_date - dates.last();

// 				// ALWAYS put the item in list if it has been more than the max interval date since last purchase.
// 				// no need to go through sorting if this passes, so saves some computations
// 				int maxInterval = Collections.max(intervals);
// 				int minInterval = Collections.min(intervals);
// 				if (date_since_last_purchase >= maxInterval) {
// 					final_list.add(name);

// 				} else if (date_since_last_purchase < minInterval) {
// 					// TODO: for now never include in list but with a small possibility still should.
// 					// 		where to get that probability though?
// 				} else {
// 					double prob = calculate_purchase_probability(intervals, date_since_last_purchase);
// 					if (Math.random() > prob) final_list.add(name);
// 				}
// 			} // end if dates.size()
// 		} // end while

// 		return final_list;
// 	}


// 	/*
// 	  	Compute the probability of an item being purchased, based on how long has it been since it was last bought
// 	  	and the intervals between purchases of that item so far. The calculation algorithm is as follows:

// 		For every interval in ascending order, add to the probability (# of that interval in array) / (size of array).
// 		ex) intervals = [2,2,4,4,6]
// 			p(0) = 0
// 			p(1) = 0
// 			p(2) = 0 + 2/5 = .4
// 			p(3) = .4
// 			p(4) = .4 + 2/5 = .8
// 			p(5) = .8
// 			p(6) = .8 + 1/5 = 1.0

// 		(Only calculates up to the date of the second parameter and return that value)

// 		TODO: improve this (Taylor series? Metaheuristics?)
// 			also need to incorporate recency here... but how?

// 	 	Inputs:
// 	  		ArrayList<Integer> intervals : list of intervals between dates of purchase
// 	  		int date_since_last_purchase

// 		Output:
// 	  		double Probability

// 	 */
// 	public static double calculate_purchase_probability(ArrayList<Integer> intervals, int date_since_last_purchase) {
// 		double prob = 0;
// 		Collections.sort(intervals);

// 		int num_elements = intervals.size();
// 		int count = 0;

// 		for (int i = 0; i < num_elements; i++) {
// 			if (intervals.get(i) <= date_since_last_purchase) count++;
// 		}

// 		// This thing can obviously be done much more easily via binarysearch (and O(log n) instead of O(n) too!)
// 		// but using this temporarily since we're (hopefully?) gonna improve anyway.

// 		return count / num_elements;
// 	}



// 	/* HELPERS */

// 	// string to int converter
// 	public static boolean isInteger(String str) {
// 		if (str == null) return false;
// 		int length = str.length();
// 		if (length == 0) return false;

// 		for (int i = 0; i < length; i++) {
// 			char c = str.charAt(i);
// 			if (c < '0' || c > '9') {
// 				return false;
// 			}
// 		}
// 		return true;
// 	} // end isInteger


// }