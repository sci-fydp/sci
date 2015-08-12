Run SA.m

%Standard SA params.
boltzman = 1; % should keep at 1, temperature is calibrated, so this doesn't do anything.
initialTempFactor = 0.8; % between 0.5 and 1
maxNumRuns = 25000; %Should probably keep under 30k to be under two minutes
alpha=0.9; % Cooling factor used is geometric, T = T * alpha, > 0.5 should be reasonable.

%Other params.
    %Stagnation/stuck in local minimum
noIterImprovement = 0; % runtime, dont change this
numNoIterImprovementExit = 10000; %Number of iterations of no improvement to break, keep high
noIterImprovementReheat = 500; % Number of iterations of no improvement to reheat, 500 < n < 1000
numNoIterImprovementSwap = 800; % Number of iterations of no improvement to add more swaps, 500 < n < 2000
numNoIterImprovementRandomStore = 600; % Number of iterations of no improvement to add more random stores, 500 < n < 1500

reheatValue = 1.05; % Reheat temp factor, on each reheat step (T = T * reheatValue) 1.01 < n < 1.11
reheatRunThreshold = maxNumRuns/2; % Stop reheating point.

    %Few iterations on initial temp, more on lower and lower temps
currTempIter = 0; % runtime, dont change this
numIterPerTempDecrease = 20; % initial # Iterations per decrease 20 < n < 50
numIterPertempDecreaseIncrement = 20; % # Iterations increment per decrease 20 < n < 200

    %Neighbourhood operator probabilities
swapProbability = 0.85; % Keep this high ~~ simulates TSP solving 0.7 < n < 1.0
numSwapsToMake = 2; % initial # swaps per swap iteration 1 < n < 10
randomStoreProbability = 0.5; % Keep medium to high ~~ simulates random store swapping (new routes to search, different prices) 0.5 < n < 0.8
numRandomStoreToMake = 3; % initial # of random stores to swap 1 < n < 5
maxRandomStore = 5; % max # of random stores to swap 1 < n < 10
maxSwaps = 5; % max # of random stores to swap 1 < n < 10