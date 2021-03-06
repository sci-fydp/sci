function [distance, price] = evaluateSoln( route, items, stores, purchaseAmountMap, distanceMap, inventoryMap, storeNames )
    
       % I think these will keep parsing those files if you just dont pass
       % them in zzz....
    %distanceMap = parse_distances('outputDistance.txt');
    %inventoryMap = parse_inventory('outputInventory.txt');
    %storeNames = store_names('outputDistance.txt');
    
    distance = 0;
    %disp(route);
    locationPrev = route{1};
    locationNext = route{1};
    % ??? magic here. https://www.mathworks.com/matlabcentral/newsreader/view_thread/263654
    % Remove all uniques without losing order of stores!!!
    % You dont need to visit a store twice.
    [u i]=unique(route,'first');
    newRoute = route(sort(i));
    
    %Append the last location back on.
    lastCell = size(newRoute, 2);
    lastCell = lastCell + 1;
    newRoute{lastCell} = newRoute{1};
  
    %Solve for distance
    for i = 2:size(newRoute,2)
        locationPrev = locationNext;
        locationNext = newRoute{i};
        vDistances = distanceMap(locationPrev);
        index = getnameidx(storeNames,locationNext);
        distanceCost = vDistances{index};
        distanceCost = str2double(distanceCost);
        distance = distance + distanceCost;
    end
    
    %Solve for price.
    price = 0;
    i = 1;
    for itemName = items
        itemCharName = itemName{1};
        storeItemMap = inventoryMap(itemCharName);
        storeKey = stores{i};
        
        itemPrice = str2double(storeItemMap(storeKey));
        amountBought = purchaseAmountMap(itemCharName);
        price = price + itemPrice * amountBought;
        i = i + 1;
    end

    %cost = 0.5*distance + 0.5*price;
    %Returns distance and price costs.
end