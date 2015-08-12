function [best] = adaptiveGA()

for i=1:50
 results = GA();
 best(i) = results(1);
 numIt(i) = results(2);
end

figure(2);
histfit(best);
disp(mean(best));
disp(min(best));

figure(3);
histfit(numIt);
disp(mean(numIt));
disp(max(numIt));