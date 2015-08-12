function [ velocity ] = Subtracting( position1, position2 )
%Subtracting - Subtracting position vector 2 from position vector 1 to
%   a velocity matrix. This produces the sequence of swaps that could
%   one position to the other.

% Test case:
%position1 = [3 2 7 1 6 4 5];
%position2 = [6 2 5 1 3 4 7];
%velocity = Subtracting(position1, position2);
% Confirm that velocity = [1 5; 3 7] % and NOT [3 6; 7 5]?

%Position size should be same?
numPositions = max (length(position1), length(position2));
velocity = zeros(numPositions, 2);

count = 1;
for i = 1:numPositions
    if( not( isequal(position1(i), position2(i)) ) )
        velocity(count, 1) = i;
        numDiff = position1(i);
        
        for j = 1:numPositions
            if(isequal(position2(j), numDiff))
                velocity(count, 2) = j;
            end
        end
        count = count + 1;
    end
end

%Remove duplicate entries (swaps in other order)
[numVelocity, n] = size (velocity);
removeVector = zeros(numVelocity, 1);
count = 1;
for i = 1:numVelocity
    for j = i:numVelocity
        if velocity(i,1) ~= 0 && velocity(i,1) == velocity (j,2) && velocity(i,2) == velocity(j,1)
           removeVector(count) = j;
           count = count + 1;
        end
    end
end

removeVector(removeVector==0) = [];

for i = 1:length(removeVector)
    velocity(removeVector(i), :) = 0;
end

%Remove extra zeros
velocity(any(velocity==0,2),:) = [];

end