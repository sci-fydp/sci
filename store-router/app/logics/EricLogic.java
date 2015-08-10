package logics;

import java.util.List;

import models.UserShoppingList;
import models.UserShoppingListItem;

public class EricLogic {

	/*
	  	Generate the probable shopping list for the specified date.

	  	Few assumptions/notes
		- ALWAYS put the item on list if it has been more than 'maxInterval' days since last shopping of that item.
		- does not take into account of recency of the previous purchases


	 	Inputs:
	  		int generation_date : date of generating the list (may not necessarily be today)

		Output:
	  		List<UserShoppingListItem> List of item names

	 */
	public static List<UserShoppingListItem> generateItemsForUser(int generation_date) {
		models.User user = models.User.find.where().eq("id", 1).findUnique();
		HashMap<String, TreeSet<Date>> occurrences = organize_occurrences(user);
		List<UserShoppingListItem> final_list = null;

		Set<UserShoppingListItem> items = occurrences.keySet();
		Iterator<UserShoppingListItem> items_it = items.iterator();

		// Loop for each item:
		while (items_it.hasNext()) {
			UserShoppingListItem name = items_it.next();
			TreeSet<Date> dates = occurrences.get(name);
			
			if (dates.size() == 0) {
				// TODO: error
			} else if (dates.size() == 1) {
				// TODO: for now an item bought for the first time will never appear on a newly generated list
				// 		Later this should be fixed to incorporate item relativity values.
			} else {
				ArrayList<Date> intervals = new ArrayList<Date>();	// no need for this to be sorted
				Iterator<Date> dates_it = dates.iterator();
				// iterate through the dates and calculate intervals between consecutive elements
				int cur = dates_it.next();
				while (dates_it.hasNext()) {
					int next = dates_it.next();
					intervals.add(next - cur);
					cur = next;
				}

				int date_since_last_purchase = generation_date - dates.last();

				// ALWAYS put the item in list if it has been more than the max interval date since last purchase.
				// no need to go through sorting if this passes, so saves some computations
				int maxInterval = Collections.max(intervals);
				int minInterval = Collections.min(intervals);
				if (date_since_last_purchase >= maxInterval) {
					final_list.add(name);

				} else if (date_since_last_purchase < minInterval) {
					// TODO: for now never include in list but with a small possibility still should.
					// 		where to get that probability though?
				} else {
					double prob = calculate_purchase_probability(intervals, date_since_last_purchase);
					if (Math.random() > prob) final_list.add(name);
				}
			} // end if dates.size()
		} // end while

		return final_list;
	}


	/* 
		Create a list of dates on which each item appeared in the shopping lists batch
	
		Inputs:
			models.User user  : target user

		Output:
			<UserShoppingListItem -> list of Date> mapping
	*/
	public static HashMap<String, TreeSet<Date>> organize_occurrences(models.User user) {
		/*
			Q) Why use TreeSet (instead of, say, ArrayList) to keep track of item occurrence dates?
				- ordered insert; convenient later in calculating intervals between purchase dates.
				- prevents duplicate elements, in case the user accidentally input an item more than once on list.
				TODO: doesn't prevent duplicate elements (try printing a treeset directly to reproduce); investigate this
		 */
		HashMap<UserShoppingListItem, TreeSet<Date>> occurrences = new HashMap<UserShoppingListItem, TreeSet<Date>>();
		List<UserShoppingList> shoppingLists = UserShoppingList.find.where().eq("user_id", user.id).findList();

		for(UserShoppingList shoppingList : shoppingLists) {
			Date date = shoppingList.createdAt(); // TODO: is this correct?
			List<UserShoppingListItem> shoppingListItems = UserShoppingListItem.find.where().eq("shopping_list_id", shoppingList.id).findList();
			
			for (int itr_items = 0; itr_items < list.size(); itr_items++) {
				UserShoppingListItem item = list.get(itr_items);

				TreeSet<Date> dates = new TreeSet<Date>();
				if (occurrences.containsKey(item)) {
					// item already exists in hashmap; add the date of occurence to list of dates
					dates = occurrences.get(item);
				}
				dates.add(date);
				occurrences.put(item, dates);
			}
			
		}
	
		// Print the contents of the hashmap by key (TODO: need to fix first if using)
		// Set<String> items = occurrences.keySet();
		// Iterator<String> it = items.iterator();
		// while (it.hasNext()) {
		// 	String ele = it.next();
		// 	System.out.print(ele + ": ");
		// 	TreeSet<Integer> dates = occurrences.get(ele);
		// 	for (int i = 0; i < dates.size(); i++) {
		// 		System.out.print(dates);
		// 	}
		// 	System.out.println();
		// }
		
		return occurrences;
	} // end organize_occurences


	/*
	  	Compute the probability of an item being purchased, based on how long has it been since it was last bought
	  	and the intervals between purchases of that item so far. The calculation algorithm is as follows:

		For every interval in ascending order, add to the probability (# of that interval in array) / (size of array).
		ex) intervals = [2,2,4,4,6]
			p(0) = 0
			p(1) = 0
			p(2) = 0 + 2/5 = .4
			p(3) = .4
			p(4) = .4 + 2/5 = .8
			p(5) = .8
			p(6) = .8 + 1/5 = 1.0

		(Only calculates up to the date of the second parameter and return that value)

		TODO: improve this (Taylor series? Metaheuristics?)
			also need to incorporate recency here... but how?

	 	Inputs:
	  		ArrayList<Integer> intervals : list of intervals between dates of purchase
	  		int date_since_last_purchase

		Output:
	  		double Probability

	 */
	public static double calculate_purchase_probability(ArrayList<Integer> intervals, int date_since_last_purchase) {
		double prob = 0;
		Collections.sort(intervals);

		int num_elements = intervals.size();
		int count = 0;

		for (int i = 0; i < num_elements; i++) {
			if (intervals.get(i) <= date_since_last_purchase) count++;
		}

		// This thing can obviously be done much more easily via binarysearch (and O(log n) instead of O(n) too!)
		// but using this temporarily since we're (hopefully?) gonna improve anyway.

		return count / num_elements;
	}

}
