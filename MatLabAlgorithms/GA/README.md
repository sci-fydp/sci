# ece457project

The Generator

To change or generate new data sets, just change any of the 3 initial parameters in the RandomData class.

static final int numStores = 100;
static final int numLocations = 10;
static final int seed = 0;

Stores are places that sell items.
Locations are possible locations that the user may begin in.
Seed is the random number generator seed.

static final String OUTPUT_DISTANCE_FILE_NAME = "outputDistanceLarge.txt";
static final String OUTPUT_INVENTORY_FILE_NAME = "outputInventoryLarge.txt";

Represent the name of the two files that will be written to.

static final String INPUT_ITEMS_NAME = "items.txt";

Is the input file location of the items & their prices. Do not put prices below 10, or the program may crash.
	
To have concrete prices/no price deviation, change the RandomItem class' toString method to just return the cost rather than the addition of the random values.