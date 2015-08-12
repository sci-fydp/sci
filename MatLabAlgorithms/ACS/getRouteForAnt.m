function [ route ] = getRouteForAnt( antsRoute, i, numProducts )
%GETROUTEFORANT Summary of this function goes here
%   Detailed explanation goes here
    for j = 1:numProducts+2
       	storeName = antsRoute{i, j};
        route{j} = storeName;
    end
end

