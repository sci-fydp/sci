function [results, route, items] = ACS(evaporation_rate, numAnts, beta, maxIters)

%required items
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

%get values
distanceMap = parse_distances('REAL_distances.txt');
inventoryMap = parse_inventories('REAL_inventory.txt');
storeNames = store_names('REAL_distances.txt');
numItems = size(currentPurchaseArray);

numStores = length(storeNames);
numProducts = length(currentPurchaseArray);
%evaporation_rate = 0.1;

%maxIters = 10000;
%beta = 1;
currentIter = 0;
%numAnts = 15;

weightDist = 0.5;
weightPrice = 1 - weightDist;

%Generating an initial soln------------
count = 0;

% need to generate two pheromone matrices
% one for distances, another for cost
pricePheromones = zeros(numProducts, numStores);
distancePheromones = ones(numStores);

productIndex = 1;
% Fill price pheromones
for product = currentPurchaseArray
    productName = product{1};
    storeMap = inventoryMap(productName);
    storeKeys = keys(storeMap);
%     fprintf('');
    for key = storeKeys
        [m, index] = ismember(key, storeNames);
%         fprintf('Index for a product %s is %f\n', productName, index);
        if index
            pricePheromones(productIndex, index) = 1;
        end
    end
    productIndex = productIndex + 1;
end
stores_visited = cell(1);
stores_visited{1} = startLocation;
store_iteration = 2;
% each ant's route is start location + # of products + start location
best_ant_index = 1;
current_best_price_cost = inf;
current_best_dist_cost = inf;
% product and price picked
totalLoopTimeTaken = 0;
antsSolutions = zeros(1, maxIters);
antsBestSolutions = inf(numAnts, 1);
% GA
% pop. size = 4
% beta = [1, 32]
% betaParameters = randi([1 32], numAnts*4, 1);
% pheromones = [0.01, 0.99] (divide by 100!)
% pheromoneParameters = randi([1 99], numAnts*4, 1);
% greedyParameter = [0, 1]
% 0 - pick greedy, 1 - pick randomly
% greedyParameters = randi([0 1], numAnts*4, 1);
bestSolutionTotal = inf;
antsProducts = cell(numAnts, numProducts);

lastSolutions = zeros(1, 10);

% itemPurchaseArray = currentPurchaseArray;
% Perm = randperm(length(currentPurchaseArray));
% itemPurchaseArray = itemPurchaseArray(Perm);

numIterationsWithoutChange = 0;
parametersChanged = 0;
improvedBestFactor = 1.5;
% antsParameters = zeros(numAnts,
quit = 0;
while currentIter < maxIters && quit == 0
    bestFactor = 1;
    loopStartTime = cputime;
%     set initial cost to a big number
    cost = inf;
%     products_bought{1} = 'product';
    
    bestSolnCost = inf;
    for i = 1:numAnts
        antsRoute{i, 1} = startLocation;
        products_index = 1;
        itemPurchaseArray = currentPurchaseArray;
        Perm = randperm(length(currentPurchaseArray));
        itemPurchaseArray = itemPurchaseArray(Perm);
        for k = 1:numProducts
            antsProducts{i, k} = itemPurchaseArray{k};
        end
        while products_index < numProducts + 1
            product = itemPurchaseArray(products_index);
            productName = product{1};
            storeMap = inventoryMap(productName);
            storeKeys = keys(storeMap);
%             disp(keys(storeMap));
%             disp(values(storeMap));
            sumPrices = 0;
            sumDistances = 0;
            [m, productIndex] = ismember(productName, currentPurchaseArray);
            for key = storeKeys
                [m, index] = ismember(key, storeNames);
                price = storeMap(key{1});
                pricePheromone = pricePheromones(productIndex, index);
                sumPrices = sumPrices + pricePheromone/str2double(price);
                [m, prevCityIndex] = ismember(stores_visited{store_iteration-1}, storeNames);
                prev_store = stores_visited{store_iteration-1};
                distancesFromPrevStore = distanceMap(prev_store);
                distance = distancesFromPrevStore(index);
                sumDistances = sumDistances + distancePheromones(prevCityIndex, index)/(str2double(distance)^beta);
            end
            probability_index = 1;
            for key = storeKeys
                [m, index] = ismember(key, storeNames);
                price = storeMap(key{1});
                pricePheromone = pricePheromones(productIndex, index);
                [m, prevCityIndex] = ismember(stores_visited{store_iteration-1}, storeNames);
                prev_store = stores_visited{store_iteration-1};
                distancesFromPrevStore = distanceMap(prev_store);
                distance = distancesFromPrevStore(index);
                price_probabilities(probability_index) = (pricePheromone/str2double(price))/sumPrices;
                distance_probabilities(probability_index) = (distancePheromones(prevCityIndex, index)/(str2double(distance)^beta))/sumDistances;
                probability_index = probability_index + 1;
            end
            random_number = rand();
            r = randi([0 1], 1, 1);
            % if the global best is the same for 50 iterations
            % do more explorations by not picking greedy solution
            if numIterationsWithoutChange > 50 && parametersChanged < 5
                parametersChanged = parametersChanged + 1;
                rand_beta = rand();
                % change beta to be in a range [0.5 1.5]
                beta = 0.5 + 1*rand_beta;
                % decrease evaporation rate -> encourage exploration
                evaporation_rate = evaporation_rate - 0.01;
                improvedBestFactor = improvedBestFactor + 0.5;
                if evaporation_rate < 0
                    evaporation_rate = 0.01;
                end
                numIterationsWithoutChange = 0;
            end
            
            
            price_dist_prob = price_probabilities.*distance_probabilities;
            index_to_pick = PickFromProbabilities(price_dist_prob, random_number, r);
            storeName = storeKeys(index_to_pick);
            store_chosen = storeName{1};
            antsRoute{i, products_index + 1} = store_chosen;
            distance_probabilities = [];
            price_probabilities = [];
            products_index = products_index + 1;
        end
        antsRoute{i, products_index + 1} = startLocation;
    end
    
    for i = 1:numAnts
        route = getRouteForAnt( antsRoute, i, numProducts );
        
        for k = 1:numProducts
            itemPurchaseArray{k} = antsProducts{i, k};
        end
        
        [distCost, priceCost] = evaluateSoln(route, itemPurchaseArray, route(2:numProducts+1), purchaseAmountMap, distanceMap, inventoryMap, storeNames);
        currentSolnCost = weightDist * distCost + weightPrice * priceCost;
        if currentSolnCost < antsBestSolutions(i, 1)
            antsBestSolutions(i, 1) = currentSolnCost;
        end
        if currentSolnCost < bestSolnCost
            bestSolnCost = currentSolnCost;
            best_ant_index = i;
            bestRoute = route;
            current_best_price_cost = priceCost;
            current_best_dist_cost = distCost;
            currentBestPurchaseArray = itemPurchaseArray;
            if currentSolnCost < bestSolutionTotal
                bestSolutionTotal = currentSolnCost;
                bestRouteTotal = route;
                bestFactor = improvedBestFactor;
                bestPurchaseArray = itemPurchaseArray;
                numIterationsWithoutChange = 0;
            end
        end
    end
    antsSolutions(1, currentIter + 1) = bestSolnCost;
    [pricePheromones, distancePheromones] = updatePheromones( bestRoute, currentPurchaseArray, pricePheromones, distancePheromones, storeNames, currentPurchaseArray, numProducts, evaporation_rate, 10, 1, current_best_dist_cost, current_best_price_cost, bestFactor );
    currentIter = currentIter + 1;
    numIterationsWithoutChange = numIterationsWithoutChange + 1;
    loopEndTime = cputime;
    loopTimeTaken = loopEndTime - loopStartTime;
    totalLoopTimeTaken = totalLoopTimeTaken + loopTimeTaken;
    if mod(currentIter, 10) == 0
        refreshdata;
        plot([1:currentIter], antsSolutions(1, [1:currentIter]));
        drawnow;
    end
    %converged stop
    if numIterationsWithoutChange > 500
        quit = 1;
    end
end

%fprintf('It took %f seconds\n', totalLoopTimeTaken);
results = [bestSolutionTotal currentIter];
route = bestRouteTotal;
items = bestPurchaseArray;
%disp(bestPurchaseArray);
%disp(bestRouteTotal);
%disp(bestSolutionTotal);