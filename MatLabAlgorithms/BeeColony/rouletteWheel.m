function [ index ] = rouletteWheel(valueArray)
	%runs the roulette wheel on the array given, returns index
    sum = 0;
	randomNumber = rand();
	for index = 1:length(valueArray)
		sum = sum + valueArray(index);
	end
	
	factor = 1.0/sum; %Normalize to 0,1 range
	
	s2 = 0;
	for index = 1:length(valueArray)
		s2 = s2 + valueArray(index) * factor;
		if s2 > randomNumber
			break;
		end
	end
end

