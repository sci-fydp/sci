function [result, solution] = GA(pCross, NumGen, PopSize)
%maximum number of generations
%NumGen = 500;
%pop size - should be at least 10
%PopSize = 10;
%probability of crossover/mutation
%pCross = 0.9;
pMut = 1-pCross;
%weight distribution of distance/price
weightDist = 0.5;
weightPrice = 1 - weightDist;

%input
numItems = 4;
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

%parse valueser
distanceMap = parse_distances('REAL_distances.txt');
inventoryMap = parse_inventories('REAL_inventory.txt');
storeNames = store_names('REAL_distances.txt');
numItems = size(currentPurchaseArray);

%GENERATE INITIAL POPULATION
for i= 1:PopSize
    
    dataMap = containers.Map;
    storeList = cell(numItems);
    count = 0;
    
    %Randomize buying order.
    itemPurchaseArray = currentPurchaseArray;
    Perm1 = randperm(length(currentPurchaseArray));
	itemPurchaseArray = itemPurchaseArray(Perm1);
    
    %Get Stores that sell the items we want for each item
    for itemName = itemPurchaseArray
        itemCharName = itemName{1};
        storeItemMap = inventoryMap(itemCharName);
        storeKeys = keys(storeItemMap);
        count = count + 1;
        slot = randi(size(storeKeys,2));
        storeList{count} = storeKeys{slot}; %Choose random store rather than first store.
    end

    %Select 1st store that sells each item as initial soln and generate route
    %Route is initialLoc, store1, store2...., storeN, initialLoc
    midRoute{1} = startLocation;
    currentStoreList = cell(size(itemPurchaseArray));
    count = 2;
    for loc = storeList
        size(loc);
        midRoute{count} = loc{1};
        currentStoreList{count-1} = loc{1};
        count = count + 1;
    end
    midRoute{count} = startLocation;

    %get initial soln cost
    [distCost, priceCost] = evaluateSoln(midRoute,itemPurchaseArray,currentStoreList, purchaseAmountMap, distanceMap, inventoryMap, storeNames);
    currentSolnCost = weightDist * distCost + weightPrice * priceCost;
    
    dataMap('route') = midRoute;
    dataMap('storeList') = currentStoreList;
    dataMap('bestSolnCost') = currentSolnCost;
    dataMap('bestPurchaseArray') = itemPurchaseArray;
    
    GAPop{i} = dataMap;
end

%variables for adaptation
lastCost = 1000000000;%arbitrarily large number
noChange = 0;
yesChange = 0;
threshold = 5;
quit = 0;
i = 1;

bestCost = 100000000000;
for j=1:PopSize
    popFitness(j) = GAPop{j}('bestSolnCost');
    if popFitness(j)<bestCost
        bestCost = popFitness(j);
        bestSolution = GAPop{j};
    end
end

solnXAxis = i;
solnYAxis = bestCost;
x = plot(solnXAxis, solnYAxis, 'YDataSource', 'solnYAxis', 'XDataSource', 'solnXAxis');

%EVOLVE POPULATION
while i <= NumGen && quit == 0 

    tic; 
    [GAPop, average, bestCost] = evolve(GAPop,PopSize,purchaseAmountMap,inventoryMap,currentPurchaseArray, distanceMap, storeNames, numItems, pCross, pMut, weightDist, weightPrice);
    solnXAxis = [solnXAxis i];
    solnYAxis = [solnYAxis bestCost ];
    if (mod(i, 10) == 0)
       set (x, 'XData',solnXAxis, 'YData',  solnYAxis)
       %refreshdata
       drawnow
    end    
    stats(i,:) = [bestCost, average];
    
    %ADAPT------------------------ 
    if lastCost == bestCost
         noChange = noChange + 1;
        yesChange = 0;
    else 
        noChange = 0;
        yesChange = yesChange + 1;
    end
 
    if noChange > threshold 
        if pCross > 0.3
            pCross = pCross - 0.1;
            pMut = 1- pCross;
            noChange = 0;
            threshold = threshold + 5; 
        else
            quit = 1;
            %disp(i);
        end 
    elseif yesChange > 3 && pCross < 0.8
        pCross = pCross + 0.1;
        pMut = 1- pCross;
        yesChange = 0;
    end
    lastCost = bestCost;
    i = i+1;
    time(i) = toc;
end 
numIt = i-1;
for j=1:PopSize
    bestCost = 100000;
    worstCost = 0;
    popFitness(j) = GAPop{j}('bestSolnCost');
    if popFitness(j)<bestCost
        bestCost = popFitness(j);
        bestSolution2 = GAPop{j};
    elseif popFitness(j)>worstCost(1)
        worstCost = [popFitness(j) j];
        worstSolution2 = GAPop{j};
    end
end
%disp(pCross);
disp(mean(time));
%BEST SOLUTION
plot(stats);
bestSolution = bestSolution2('bestSolnCost');
solution = bestSolution2;
result = [bestSolution numIt];

function new = copy(this)
% Instantiate new object of the same class.
new = feval(class(this));
 
% Copy all non-hidden properties.
new('bestSolnCost') = this('bestSolnCost');
new('route') = this('route');
new('storeList') = this('storeList');
new('bestPurchaseArray') = this('bestPurchaseArray');

function [GAPop, average, bestCost] = evolve(GAPop,PopSize, purchaseAmountMap, inventoryMap, currentPurchaseArray, distanceMap, storeNames, numItems, pCross, pMut, weightDist, weightPrice)
bestCost = 100000000000;
worstCost = [0, 0]; %value, index
%find best and worst solutions
for j=1:PopSize
    popFitness(j) = GAPop{j}('bestSolnCost');
    if popFitness(j)<bestCost
        bestCost = popFitness(j);
        bestSolution = GAPop{j};
    elseif popFitness(j)>worstCost(1)
        worstCost = [popFitness(j) j];
        worstSolution = GAPop{j};
    end
end

average = median(popFitness);
bestFit = min(popFitness);

%duplicate to remove passing by reference
newSoln = copy(bestSolution);
if worstCost(2) ~= PopSize
    GAPop{worstCost(2)} = GAPop{PopSize};
end %ignore last element in mutation and crossover
GAPop{PopSize} = newSoln;
%-------------------------------------------
randomIndex = randperm(PopSize-1);
index = 1;
randomIndex2 = randi(numItems,1,PopSize);
%CROSSOVER
while index <= (pCross*(PopSize-1)-2)
    %get random part of the population
    First = GAPop{randomIndex(index)};
    Second = GAPop{randomIndex(index+1)};
    %randomly select an item to swap stores for
    swapID = randomIndex2(index);
    swapItem = currentPurchaseArray(swapID);
    firstindex = 1;
    secondindex = 1;
    FirstArray = First('bestPurchaseArray');
    SecondArray = Second('bestPurchaseArray');
    %get indices of items
    done = 0;
    l = 1;
    while l<=numItems(2) && done<2
        if 1 == cellfun(@strcmp, FirstArray(l), swapItem)
            firstindex = l;
            done = done +1;
        end
        if 1 == cellfun(@strcmp, SecondArray(l), swapItem)
            secondindex = l;
            done = done + 1;
        end
        l = l+1;
    end 
    %swap stores
    FirstStores = First('storeList');
    SecondStores = Second('storeList');

    FirstRoute = First('route');
    SecondRoute = Second('route');

    swappedItem = FirstStores(firstindex);
    FirstStores(firstindex) = SecondStores(secondindex);
    FirstRoute(firstindex+1) = SecondStores(secondindex);
    SecondStores(secondindex) = swappedItem;
    SecondRoute(secondindex+1) = swappedItem;

    First('storeList') = FirstStores;
    Second('storeList') = SecondStores;

    First('route') = FirstRoute;
    Second('route') = SecondRoute;
    [distCost, priceCost] = evaluateSoln(FirstRoute,FirstArray,FirstStores, purchaseAmountMap, distanceMap, inventoryMap, storeNames);
    First('bestSolnCost') = weightDist * distCost + weightPrice * priceCost;
    [distCost, priceCost] = evaluateSoln(SecondRoute,SecondArray,SecondStores, purchaseAmountMap, distanceMap, inventoryMap, storeNames);
    Second('bestSolnCost') = weightDist * distCost + weightPrice * priceCost;

    index = index + 2;
end
%--------------
%MUTATION
randItemPerm = randperm(numItems(2));
randItemPerm(numItems+1)=randItemPerm(1);

for k = index:PopSize-1
    %choose random stores to mutate 
    First = GAPop{randomIndex(k)};
    swapID1 = randomIndex2(index);
    swapItem1 = currentPurchaseArray(swapID1);
    %can't have the same store swap, must have unique value
    if swapID1 == randItemPerm(swapID1)
        swapID2 = randItemPerm(swapID1+1);
    else 
        swapID2 =  randItemPerm(swapID1);
    end
    swapItem2 = currentPurchaseArray(swapID2);

    FirstArray = First('bestPurchaseArray');
    
    %get random item to switch stores for
    switchID = rem(randItemPerm(swapID1)+index, numItems(2)) + 1;
    switchItem = currentPurchaseArray(switchID);
   
    %get indices of items
    done = 0;
    l = 1;
    while l<=numItems(2) && done<3
        if 1 == cellfun(@strcmp, FirstArray(l), swapItem1)
            firstindex = l;
            done = done +1;
        end
        if 1 == cellfun(@strcmp, FirstArray(l), swapItem2)
            secondindex = l;
            done = done + 1;
        end
        if 1 == cellfun(@strcmp, FirstArray(l), switchItem)
            switchindex = l;
            done = done + 1;
        end
        l = l+1;
    end 
    
    FirstStores = First('storeList');
    FirstRoute = First('route');
    
    %switch bewteen store and other possible store
    %switch index is location of object in list
    storeItemMap = inventoryMap(switchItem{1});
    storeKeys = keys(storeItemMap);
    slot = randi(size(storeKeys,2));
    FirstStores{switchindex} = char(storeKeys{slot}); %Choose random store rather than first store.
       
    %swap stores for indices
    FirstArray(secondindex) = swapItem1;
    FirstArray(firstindex) = swapItem2;

    swappedItem = FirstStores(firstindex);
    FirstStores(firstindex) = FirstStores(secondindex);
    FirstRoute(firstindex+1) = FirstStores(secondindex);
    FirstStores(secondindex) = swappedItem;
    FirstRoute(secondindex+1) = swappedItem;

    First('bestPurchaseArray') = FirstArray;
    First('storeList') = FirstStores;
    First('route') = FirstRoute;
    [distCost, priceCost] = evaluateSoln(FirstRoute,FirstArray,FirstStores, purchaseAmountMap, distanceMap, inventoryMap, storeNames);
    First('bestSolnCost') = weightDist * distCost + weightPrice * priceCost;
end
