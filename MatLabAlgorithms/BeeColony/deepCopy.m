 function new = deepCopy(this)
    % Instantiate new object of the same class.
    new = feval(class(this));

    new('route') = this('route');
    new('currSolnCost') = this('currSolnCost');
    new('currPurchaseArray') = this('currPurchaseArray') ;
    new('age') = this('age');
end

