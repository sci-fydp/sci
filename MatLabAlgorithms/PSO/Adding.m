function [ new_pos ] = Adding( position, velocity )
%Adding - Apply the sequence of swaps defined by the velocity to the
%position vector to get the new position vector

% Test case:
%velocity = [1 5; 3 7];
%position = [3 2 7 1 6 4 5];
%new_pos = Adding(position, velocity);
% Confirm that new_pos = [6 2 5 1 3 4 7]

%Remove extra zeros
velocity(any(velocity==0,2),:) = [];

[numVelocity, n] = size (velocity);
new_pos = position;

for i = 1:numVelocity
    swapIndex1 = velocity(i,1);
    swapIndex2 = velocity(i,2);
    temp = new_pos(swapIndex1);
    new_pos(swapIndex1) = new_pos(swapIndex2);
    new_pos(swapIndex2) = temp;
end

end

