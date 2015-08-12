function [ new_stores ] = GetDiffStore( stores, items, originalList, getNew, possibleStores )
%GetDiffStore Switches the store from where to purchase an item from a list
%   of possible stores that item can be purchased from based on the getNew
%   vector.

% Test case:
%stores = [14 1 0 12 11 16 1];
%originalItemsList = [1 2 3 4 5 6 7];
%items = [2 3 5 7 6 1 4];
%getNew = [1 0 0 0 0 1 0];
%possibleStores = [11 16 19 27 3 4; 12 14 17 2 22 6; 1 13 22 23 3 5; 1 11 15 18 2 5; 0 29 4 8 9 7; 12 16 2 27 4 5; 18 2 24 26 27 9];
%newStores = GetDiffStore(stores, items, originalItemsList, getNew, possibleStores);
% Confirm that newStores = [X 1 0 12 11 X 1]

numStores = length(stores);
numItems = length(items);
new_stores = stores;
indices = zeros(numItems, 1);
index = 0;
for j = 1:numItems
    for k = 1:numItems
        if (isequal(items(j),originalList(k)))
            index = k;
        end
    end
    indices(j) = index;
end

for i = 1:numStores
    r = rand;
    if (r < getNew(i))
        slot = randi(size(possibleStores(indices(i),:)));
        x = possibleStores(indices(i), slot);
        [x_size, ~] = size(x{1});
        while (x_size == 0)
            slot = randi(size(possibleStores(indices(i),:)));
            x = possibleStores(indices(i), slot);
            [x_size, ~] = size(x{1});
        end
        new_stores(i) = x;
    end
end
end

