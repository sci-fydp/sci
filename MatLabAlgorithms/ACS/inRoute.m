function output = inRoute( route, i, storeName, numProducts )
%INROUTE Summary of this function goes here
%   Detailed explanation goes here
    route = getRouteForAnt(route, i, numProducts);
%     C = ['xyz' 'xxy' 'zyx']
%     type = 'xyz';
    output = 0;
    for r = route
        if strcmp(r, storeName)
            output = 1;
            break;
        end
    end
end

