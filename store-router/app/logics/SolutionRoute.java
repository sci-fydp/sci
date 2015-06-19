package logics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.LocationItem;
import models.StoreLocation;


public class SolutionRoute
{
	public ArrayList<StoreLocation> stores;
	//ArrayList<Location> locations;
	public double price;
	public double distance;
	public HashMap<StoreLocation, List<LocationItem>> buyFromStore;

}
