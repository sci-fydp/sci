%%% sample code of how to use helper functions
distances = parse_distances('outputDistance.txt');
product_prices = parse_inventories('outputInventory.txt');
storeNames = store_names('outputDistance.txt');

storeprice_keys = keys(product_prices);
for i = 1:numel(storeprice_keys)
    product_name = storeprice_keys(i);
    % disp product name
    fprintf('Product name is %s\n', product_name{1});
%     disp(product_name{1});
    % get store-price map of a product
    store_price = product_prices(product_name{1});
    
    % get store names that sell a product
    store_keys = keys(store_price);
    
    % for each product display a store name and a price of a product
    for j = 1:numel(store_keys)
        store_name = store_keys(j);
        disp(store_name{1});
        product_price = store_price(store_name{1});
        disp(product_price);
    end
end

for i = 1:numel(storeNames)
    storeName = storeNames(i);
    % display distances vector for a store
    fprintf('Store name is %s\n', storeName{1});
    dist = distances(storeName{1});
    disp(dist);
end