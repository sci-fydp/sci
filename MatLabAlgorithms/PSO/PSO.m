function [result, solutionStore, solutionPurchase] = PSO(weight, accCoeff, max_num_iter, pop_size)

%User shopping list and location.
%Random data shopping list
% currentPurchaseArray = {'Apples', 'Chicken', 'Oranges', 'Duck', 'VeryExpensiveItem', 'Stationery', 'MediumItem'};
% purchaseAmountMap = containers.Map;
% purchaseAmountMap('Apples') = 5;
% purchaseAmountMap('Chicken') = 1;
% purchaseAmountMap('Oranges') = 1;
% purchaseAmountMap('Duck') = 1;
% purchaseAmountMap('VeryExpensiveItem') = 5;
% purchaseAmountMap('Stationery') = 1;
% purchaseAmountMap('MediumItem') = 5;
% startLocation = 'Location_1';

%Real data shopping list
currentPurchaseArray = {'fish_fillet', 'astro_yogurt', 'boneless_pork_chop', 'shredded_cheese', 'juice', 'coffee', 'grape', 'post_cereal', 'pepsi', 'cheese_bar', 'pc_chicken_breast', 'entree', 'water', 'salsa', 'salad'};
purchaseAmountMap = containers.Map;
purchaseAmountMap('fish_fillet') = 5;
purchaseAmountMap('astro_yogurt') = 10;
purchaseAmountMap('boneless_pork_chop') = 1;
purchaseAmountMap('shredded_cheese') = 1;
purchaseAmountMap('juice') = 5;
purchaseAmountMap('coffee') = 1;
purchaseAmountMap('grape') = 1;
purchaseAmountMap('post_cereal') = 1;
purchaseAmountMap('pepsi') = 6;
purchaseAmountMap('cheese_bar') = 1;
purchaseAmountMap('pc_chicken_breast') = 1;
purchaseAmountMap('entree') = 1;
purchaseAmountMap('water') = 6;
purchaseAmountMap('salsa') = 1;
purchaseAmountMap('salad') = 2;
startLocation = 'location_university_of_waterloo_1';

%Get files
% distanceMap = parse_distances('outputDistance.txt');
% inventoryMap = parse_inventories('outputInventory.txt');
% storeNames = store_names('outputDistance.txt');
distanceMap = parse_distances('REAL_distances.txt');
inventoryMap = parse_inventories('REAL_inventory.txt');
storeNames = store_names('REAL_distances.txt');
numStores = length(storeNames);
numItems = size(currentPurchaseArray);

%Initial paramters
numParticles = pop_size; %10;  %size of the swarm
probDim = 2;    %unused, dimension of the problem
maxIterations = max_num_iter; %5000;   %maximum number of iterations
swarmSize = 10; %unused
neighbourhoodSize = 1;  %unused (whole swarm is a neighbourhood)
c1 = accCoeff; %1.1; %acceleration coefficient - cognitive parameter
c2 = 4-c1;  %acceleration coefficient - social parameter
w = weight; %0.6;  %inertia weight, used for adaptation

%Other parameters
noIterImprovement = 0;
noIterImprovementExit = maxIterations/4;

%Adaptation paramters
adaptCounter = 0;
noIterImprovementAdapt = noIterImprovementExit/10;

%Objective function
weightDist = 0.5;
weightPrice = 1 - weightDist;

%Generate initial solution
%Select a random store that sells each item as initial soln and generate route
%Route is initialLoc, random permutation of store list, initialLoc
pbest = zeros(numParticles, 1);
iterSol = zeros(numParticles, 1);
[m, n] = size(currentPurchaseArray);
pbestPurchaseArray = cell(numParticles, n);
pbestStoreList = cell(numParticles, n);
currStoreListt = cell(numParticles, n);
currPurchaseArray = cell(numParticles, n);
velocity = zeros(0,2,numParticles);
velStores = zeros(numParticles, n);
probabilityDistribution = [1/3 1/3 1/3];    %[inertia cognitive social]

possibleStores = cell(n, 1);
count = 1;
originalItemsList = currentPurchaseArray;

%Get stores that sell the items we want
for itemName = currentPurchaseArray
    itemCharName = itemName{1};
    storeItemMap = inventoryMap(itemCharName);
    storeKeys = keys(storeItemMap);
    [t1, t2] = size(storeKeys);
    for i = 1:t2
        possibleStores(count, i) = storeKeys(i);
    end
    count = count + 1;
end

for i = 1:numParticles
    storeList = cell(numItems);
    count = 0;
    %Get stores that sell the items we want
    for itemName = currentPurchaseArray
        itemCharName = itemName{1};
        storeItemMap = inventoryMap(itemCharName);
        storeKeys = keys(storeItemMap);
        count = count + 1;
        %Choose random store rather than the first store.
        slot = randi(size(storeKeys,2));
        storeList{count} = storeKeys{slot};
    end

    %Generate a random route.
    midRoute{1} = startLocation;
    currentStoreList = cell(m, n);
    count = 2;
    permutation = randperm(length(storeList));
    t1 = storeList(permutation);
    t2 = currentPurchaseArray(permutation);
    for j = 1:n
        currStoreList(i, j)= t1(j);
        currPurchaseArray(i, j) = t2(j);
    end
    
    for loc = currStoreList
        midRoute{count} = loc{1};
        count = count + 1;
    end
    midRoute{count} = startLocation;
    
    %Get initial solution cost
    [distCost, priceCost] = evaluateSoln(midRoute, currPurchaseArray(i,:), currStoreList(i,:), purchaseAmountMap, distanceMap, inventoryMap, storeNames);
    currentSolnCost = weightDist * distCost + weightPrice * priceCost;
    pbest(i) = currentSolnCost;
    pbestPurchaseArray(i, :) = currPurchaseArray(i, :);
    pbestStoreList(i, :) = currStoreList(i, :);
end

%Stored best solution for output
[gbest, index] = min(pbest);
bestcurrentPurchaseArray = pbestPurchaseArray(index,:);
bestStoreList = pbestStoreList(index,:);

%Graphing
solnXAxis = 0;
solnYAxis = gbest;

thePlot = plot(solnXAxis, solnYAxis, 'YDataSource', 'solnYAxis', 'XDataSource', 'solnXAxis');

%Iteration loop
iter = 0;
totalLoopTimeTaken = 0;
while (iter < maxIterations && noIterImprovement < noIterImprovementExit)
    tic
    for i = 1:numParticles
       %Get random values between 0 and 1
       r1 = rand;
       r2 = rand;
       inertia = velStores(i,:)*w;
       cognitive = FindDiff(pbestPurchaseArray(i,:), currPurchaseArray(i,:),  pbestStoreList(i, :), currStoreList(i,:))*r1*c1;
       social = FindDiff(bestcurrentPurchaseArray, currPurchaseArray(i,:), bestStoreList, currStoreList(i,:))*r1*c1;
    
       %v[t+1] = w*v[t] + c1*rand()*(pbest[]-x[t]) + c2*rand()*(gbest[]-x[t])
       temp = inertia*probabilityDistribution(1) + cognitive*probabilityDistribution(2) + social*probabilityDistribution(3);

       [a, b] = size(temp);
       for j = 1:b
           velStores(i,j) = temp(j);
       end
       %x[t+1] = x[t] + v[t+1]
       currStoreList(i,:) = GetDiffStore(currStoreList(i,:), currPurchaseArray(i,:), originalItemsList, temp, possibleStores);

       %Get random values between 0 and 1
       r1 = rand;
       r2 = rand;
       
       particleVelocity = velocity(:,:,i);
       %Reset the old velocity vector
       velocity(:,:,i) = 0;
       particleVelocity(any(particleVelocity==0,2),:) = [];
    
       inertia = Multiply(particleVelocity, w);
       cognitive = Multiply(Subtracting(pbestPurchaseArray(i, :), currPurchaseArray(i,:)), r1*c1);
       social = Multiply(Subtracting(bestcurrentPurchaseArray, currPurchaseArray(i,:)), r2*c2);
       %v[t+1] = w*v[t] + c1*rand()*(pbest[]-x[t]) + c2*rand()*(gbest[]-x[t])
       temp = [inertia;cognitive;social];
       [a, b] = size(temp);
       if (a ~= 0)
           for j = 1:a
               for k = 1:b
                   velocity(j,k,i) = temp(j,k);
               end
           end
       end
       %x[t+1] = x[t] + v[t+1]
       currPurchaseArray(i,:) = Adding(currPurchaseArray(i,:), temp);
       currStoreList(i,:) = Adding(currStoreList(i,:), temp);

    end

    for i = 1:numParticles
        %Generate a random route.
        midRoute{1} = startLocation;
        count = 2;
        for loc = currStoreList(i,:)
            midRoute{count} = loc{1};
            count = count + 1;
        end
        midRoute{count} = startLocation;
        
        %Calculate fitness value
        [distCost, priceCost] = evaluateSoln(midRoute,currPurchaseArray(i,:),currStoreList(i,:), purchaseAmountMap, distanceMap, inventoryMap, storeNames);
        currentSolnCost = weightDist * distCost + weightPrice * priceCost;
        iterSol(i) = currentSolnCost;
        if (currentSolnCost < pbest(i))
           pbest(i) = currentSolnCost;
           pbestPurchaseArray(i, :) = currPurchaseArray(i, :);
           pbestStoreList(i, :) = currStoreList(i, :);
        end
    end
    
    %Choose the particle with best fitness value of all the particles as the gbest
    [min_pbest, index] = min(pbest);
    [min_iter, ~] = min(iterSol);
    if (min_pbest < gbest)
       gbest = min_pbest;
       bestcurrentPurchaseArray = pbestPurchaseArray(index,:);
       bestStoreList = pbestStoreList(index,:);
       noIterImprovement = 0;
    else
       noIterImprovement = noIterImprovement + 1;
    end
   
    % Explore or intensify if solution is improving or not.
    if (adaptCounter >= noIterImprovementAdapt)
        adaptCounter = 0;
        if(iter < maxIterations/10)
            w = w + 0.1;
        else
            w = w - 0.1;
        end
        if (w > 0.9)
            w = 0.9;
        elseif (w < 0.5)
            w = 0.5;
        end
    else
        adaptCounter = adaptCounter + 1;
    end
    
    iter = iter + 1;
    
    solnXAxis = [solnXAxis iter];
    solnYAxis = [solnYAxis gbest];

    %Do not include graph draw time in the loop time
    loopTimeTaken = toc;
    totalLoopTimeTaken = totalLoopTimeTaken + loopTimeTaken;

    %Graph update
    if (mod(iter, 10) == 0)
       set (thePlot, 'XData',solnXAxis, 'YData', solnYAxis);
       %refreshdata(thePlot);
       %refreshdata(thePlot2);
       drawnow
    end
    stats(iter,:) = [gbest, min_iter];

end

%refreshdata
%drawnow

avgLoopTimeTaken = totalLoopTimeTaken/iter;
fprintf('Best soln in %d runs\n', iter);
fprintf('Avg loop time %d seconds, full time taken %d\n', avgLoopTimeTaken, totalLoopTimeTaken);
disp(bestcurrentPurchaseArray);
disp(bestStoreList);
disp(gbest);
plot(stats);
result = [gbest iter avgLoopTimeTaken];
solutionStore = [startLocation bestStoreList startLocation];
solutionPurchase = bestcurrentPurchaseArray;
end
%Best soln in 1123 runs
%Avg loop time 3.439914e-01 seconds, full time taken 3.863023e+02
%    'VeryExpensiveItem'    'Chicken'    'Apples'    'Duck'    'Stationery'    'MediumItem'    'Oranges'
%
%    'Store_4'    'Store_6'    'Store_19'    'Store_18'    'Store_27'    'Store_27'    'Store_23'
%
%  4.1525e+03

%Best soln in 512 runs
%Avg loop time 1.282486e-01 seconds, full time taken 6.566331e+01
%    'Stationery'    'Oranges'    'Duck'    'VeryExpensiveItem'    'Chicken'    'MediumItem'    'Apples'
%
%    'Store_5'    'Store_1'    'Store_5'    'Store_4'    'Store_2'    'Store_27'    'Store_11'
%
%       4240

%Best soln in 1000 runs
%Avg loop time 2.353916e-01 seconds, full time taken 2.353916e+02
%    'MediumItem'    'Oranges'    'Duck'    'Chicken'    'Apples'    'Stationery'    'VeryExpensiveItem'
%
%    'Store_27'    'Store_13'    'Store_5'    'Store_6'    'Store_16'    'Store_16'    'Store_4'
%
%   4.0375e+03

% Best soln in 6713 runs
% Avg loop time 2.754413e-02 seconds, full time taken 1.849037e+02
%     'Duck'    'Chicken'    'Apples'    'MediumItem'    'Stationery'    'VeryExpensiveItem'    'Oranges'
% 
%     'Store_0'    'Store_12'    'Store_4'    'Store_24'    'Store_4'    'Store_4'    'Store_3'
% 
%    4.0165e+03

% Best soln in 3388 runs
% Avg loop time 2.813805e-02 seconds, full time taken 9.533172e+01
%     'MediumItem'    'Oranges'    'Apples'    'Stationery'    'VeryExpensiveItem'    'Chicken'    'Duck'
% 
%     'Store_9'    'Store_5'    'Store_27'    'Store_27'    'Store_4'    'Store_12'    'Store_0'
% 
%    4.0005e+03

% Best soln in 2834 runs
% Avg loop time 2.688366e-02 seconds, full time taken 7.618830e+01
%     'Duck'    'Stationery'    'MediumItem'    'VeryExpensiveItem'    'Apples'    'Chicken'    'Oranges'
% 
%     'Store_15'    'Store_5'    'Store_27'    'Store_4'    'Store_27'    'Store_12'    'Store_23'
% 
%    4.0765e+03

% Best soln in 3156 runs
% Avg loop time 2.661960e-02 seconds, full time taken 8.401145e+01
%     'MediumItem'    'Oranges'    'Stationery'    'Apples'    'Duck'    'Chicken'    'VeryExpensiveItem'
% 
%     'Store_9'    'Store_5'    'Store_5'    'Store_11'    'Store_5'    'Store_12'    'Store_0'
% 
%    3.9645e+03

% Best soln in 3163 runs
% Avg loop time 6.163827e-02 seconds, full time taken 1.949619e+02
%   Columns 1 through 8
% 
%     'salad'    'salsa'    'juice'    'astro_yogurt'    'cheese_bar'    'entree'    'pepsi'    'grape'
% 
%   Columns 9 through 14
% 
%     'post_cereal'    'coffee'    'fish_fillet'    'pc_chicken_breast'    'boneless_pork_chop'    'water'
% 
%   Column 15
% 
%     'shredded_cheese'
% 
%   Columns 1 through 5
% 
%     'store_zehrs_2'    'store_zehrs_2'    'store_giant_tiger_1'    'store_giant_tiger_1'    'store_valu_mart_1'
% 
%   Columns 6 through 9
% 
%     'store_food_basics_2'    'store_giant_tiger_1'    'store_food_basics_2'    'store_giant_tiger_1'
% 
%   Columns 10 through 13
% 
%     'store_food_basics_2'    'store_giant_tiger_1'    'store_zehrs_3'    'store_zehrs_2'
% 
%   Columns 14 through 15
% 
%     'store_food_basics_2'    'store_zehrs_3'
% 
%        13719

% Best soln in 3726 runs
% Avg loop time 6.188752e-02 seconds, full time taken 2.305929e+02
%   Columns 1 through 7
% 
%     'shredded_cheese'    'coffee'    'water'    'pc_chicken_breast'    'grape'    'post_cereal'    'juice'
% 
%   Columns 8 through 14
% 
%     'salsa'    'fish_fillet'    'astro_yogurt'    'entree'    'cheese_bar'    'pepsi'    'salad'
% 
%   Column 15
% 
%     'boneless_pork_chop'
% 
%   Columns 1 through 5
% 
%     'store_zehrs_3'    'store_zehrs_3'    'store_food_basics_2'    'store_zehrs_3'    'store_food_basics_2'
% 
%   Columns 6 through 10
% 
%     'store_zehrs_3'    'store_giant_tiger_1'    'store_sobeys_1'    'store_sobeys_1'    'store_giant_tiger_1'
% 
%   Columns 11 through 15
% 
%     'store_food_basics_2'    'store_valu_mart_1'    'store_valu_mart_1'    'store_zehrs_3'    'store_zehrs_3'
% 
%    1.2386e+04

% possibleStores = 
% 
%     'Store_11'    'Store_16'    'Store_19'    'Store_27'    'Store_3'     'Store_4'            []
%     'Store_12'    'Store_14'    'Store_17'    'Store_2'     'Store_22'    'Store_6'            []
%     'Store_1'     'Store_13'    'Store_22'    'Store_23'    'Store_3'     'Store_5'            []
%     'Store_0'     'Store_11'    'Store_15'    'Store_18'    'Store_2'     'Store_5'     'Store_7'
%     'Store_0'     'Store_29'    'Store_4'     'Store_8'     'Store_9'             []           []
%     'Store_11'    'Store_12'    'Store_16'    'Store_2'     'Store_27'    'Store_4'     'Store_5'
%     'Store_12'    'Store_18'    'Store_2'     'Store_24'    'Store_26'    'Store_27'    'Store_9'