function [ updated_price_pheromones, updated_dist_pheromones ] = updatePheromones( route, productList, pricePheromones, distancePheromones, storeNames, productNames, numProducts, evaporation_rate, Q_dist, Q_price, dist_cost, price_cost, bestFactor )
%UPDATEPHEROMONES Summary of this function goes here
%   Detailed explanation goes here
    updated_price_pheromones = pricePheromones * (1 - evaporation_rate);
    updated_dist_pheromones = distancePheromones * (1 - evaporation_rate);
    
    for i = 2:numProducts+1
        prevStoreName = route{i-1};
        storeName = route(i);
        
        product = productList(i-1);
        [m, prodIndex] = ismember(product, productNames);
        
        
        [m, index] = ismember(storeName, storeNames);
        [m, prevIndex] = ismember(prevStoreName, storeNames);
        updated_dist_pheromones(index, prevIndex) = updated_dist_pheromones(index, prevIndex) + bestFactor*Q_dist/(dist_cost);
        updated_dist_pheromones(prevIndex, index) = updated_dist_pheromones(prevIndex, index) + bestFactor*Q_dist/(dist_cost);
        updated_price_pheromones(prodIndex, index) = updated_price_pheromones(i-1, index) + bestFactor*Q_price/(price_cost);
    end
end

