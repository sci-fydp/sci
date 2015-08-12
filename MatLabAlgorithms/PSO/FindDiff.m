function [ velocity ] = FindDiff( items1, items2, stores1, stores2 )
%FindDiff Compares vector position1 and position2 and sets value to 0
%   if position1 at index i exists in position2. Otherwise sets value to 1

% Test case:
%stores1 = [11 12 1 1 0 11 12];
%items1 = [1 2 3 4 5 6 7]; 
%stores2 = [14 1 0 12 11 16 1];
%items2 = [2 3 5 7 6 1 4];
%velocity = FindDiff(items1, items2, stores1, stores2);
% Confirm that velocity = [1 0 0 0 0 1 0]

%Position size should be same?
numItems = length(items1);
velocity = zeros(numItems, 1);

for i = 1:numItems
    storeBest = stores1(i);
    for j = 1:numItems
       if (isequal(items2(j),items1(i)))
           index = j;
       end
    end
    storeCurr = stores2(index);
    if (~isequal(storeBest, storeCurr))
        velocity(index) = 1;
    end
end
velocity = transpose(velocity);
end

