Run BeeColony.m

%Bee Colony parameters.
maxNumRuns = 1000;  % Should probably keep small  1000 iterations is ~80 seconds  500 < n < 5000 
ageThreshold = 5; % initial # of generations to hold a solution without changing  3 < n < 7
numEmployedBee = 10; % number of employeed bees (population size) 10 < n < 30
numOnlookerBee = 15; % initial number of onlookers (will work on employed bee solutions) 5 < n < 20

%Adaptive params.
minNumOnlookerbee = 5;  % minimum number of onlookers 5 < n < 10
minAgeThreshold = 3; % minimum age 1 < n < 5
abandonRateThreshold = 0.70; % threshold for too many abandoned searches, increase # onlookers and age size, 0.7 < n < 0.9
liveRateThreshold = 0.30; %threshold for too many explored sources, decrease # onlookers and age size, 0.1 < n < 0.3
