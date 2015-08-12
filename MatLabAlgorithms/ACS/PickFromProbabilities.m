function [ index ] = PickFromProbabilities( probabilities, random_number, r )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
    maxProbability = 0.0;
    sum = 0;
    index = 1;
    if r == 0
        for index = 1:length(probabilities)
            sum = sum + probabilities(index);
            
        end
        factor = 1.0/sum;
        s2 = 0;
        for index = 1:length(probabilities)
            s2 = s2 + probabilities(index) * factor;
            if s2 > random_number
                break;
            end
        end
        
    else
        for i = 1:length(probabilities)
            if probabilities(i) > maxProbability
                index = i;
                maxProbability = probabilities(i);
            end
        end
    end
end

