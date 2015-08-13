package logics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import models.UserShoppingList;
import models.UserShoppingListItem;

import org.joda.time.DateTime;

public class TrendAnalysisLogic {

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

 	private static final double FREQUENT_ITEM_PROBABILITY = 0.5;
 	private static final double NUM_MAX_INTERVAL_TOLERATED = 5;

	public static List<UserShoppingListItem> generateItemsForUser() {
		long generation_timestamp = DateTime.now().getMillis();
		
		models.User user = models.User.find.where().eq("id", 1).findUnique();
		HashMap<UserShoppingListItem, TreeSet<DateTime>> occurrences = organize_occurrences(user);
		List<UserShoppingListItem> final_list = null;

		Set<UserShoppingListItem> items = occurrences.keySet();
		Iterator<UserShoppingListItem> items_it = items.iterator();

		for(UserShoppingListItem item : items) {
			TreeSet<DateTime> dates = occurrences.get(item);
			
			if (dates.size() == 0) {
				// TODO: error
			} else if (dates.size() == 1) {
				// TODO: for now an item bought for the first time will never appear on a newly generated list
				// 		Later this should be fixed to incorporate item relativity values.
			} else {
				List<Long> intervals = new ArrayList<Long>();	// no need for this to be sorted

				Iterator<DateTime> dates_it = dates.iterator();
				// iterate through the dates and calculate intervals between consecutive elements
				DateTime cur = dates_it.next();
				while (dates_it.hasNext()) {
					DateTime next = dates_it.next();
					intervals.add(next.getMillis() - cur.getMillis());
					cur = next;
				}

				long date_since_last_purchase = generation_timestamp - dates.last().getMillis();

				// ALWAYS put the item in list if it has been more than the max interval date since last purchase.
				// no need to go through sorting if this passes, so saves some computations
				long maxInterval = Collections.max(intervals);
				long minInterval = Collections.min(intervals);
				double prob = 0;
				if (date_since_last_purchase >= maxInterval) {
					// always put in list if the date since last purchase is within [maxInterval, 2*maxInterval]
					// the probability linear decays afterwards, guessing that the user is no longer interested
					prob = 1; // TODO: implement this


				} else if (date_since_last_purchase < minInterval) {
					// TODO: for now never include in list but with a small possibility still should.
					// 		where to get that probability though?
				} else {
					prob = calculate_purchase_probability(intervals, date_since_last_purchase);
				}
				if (Math.random() > prob) final_list.add(item);
			} // end if dates.size()
		}

		return final_list;
	}


	/* 
		Create a list of dates on which each item appeared in the shopping lists batch
	
		Inputs:
			models.User user  : target user

		Output:
			<UserShoppingListItem -> list of Date> mapping
	*/
	public static HashMap<UserShoppingListItem, TreeSet<DateTime>> organize_occurrences(models.User user) {
		/*
			Q) Why use TreeSet (instead of, say, ArrayList) to keep track of item occurrence dates?
				- ordered insert; convenient later in calculating intervals between purchase dates.
				- prevents duplicate elements, in case the user accidentally input an item more than once on list.
				TODO: doesn't prevent duplicate elements (try printing a treeset directly to reproduce); investigate this
		 */
		HashMap<UserShoppingListItem, TreeSet<DateTime>> occurrences = new HashMap<UserShoppingListItem, TreeSet<DateTime>>();
		List<UserShoppingList> shoppingLists = UserShoppingList.find.where().eq("user_id", user.id).findList();

		for(UserShoppingList shoppingList : shoppingLists) {
			DateTime date = shoppingList.creationDate; // TODO: is this correct?
			List<UserShoppingListItem> shoppingListItems = UserShoppingListItem.find.where().eq("shopping_list_id", shoppingList.id).findList();
			
			for(UserShoppingListItem item : shoppingListItems) {
				TreeSet<DateTime> dates = null;
				if (occurrences.containsKey(item)) {
					// item already exists in hashmap; add the date of occurence to list of dates
					dates = occurrences.get(item);
				}
				else {
					 dates = new TreeSet<DateTime>();
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
	public static double calculate_purchase_probability(List<Long> intervals, long date_since_last_purchase) {
		double prob = 0;
		Collections.sort(intervals);

		int num_elements = intervals.size();
		double count = 0;

		for (int i = 0; i < num_elements; i++) {
			if (intervals.get(i) <= date_since_last_purchase) count++;
		}

		// This thing can obviously be done much more easily via binarysearch (and O(log n) instead of O(n) too!)
		// but using this temporarily since we're (hopefully?) gonna improve anyway.

		return count / num_elements;
	}

}
