function storePrice = parse_inventories( filename )
%   This function returns a map
%   Keys are product names
%   Values are maps with key-value = {city:price}
    fileId = fopen(filename, 'r');
    input_data = textscan(fileId, '%s', 'Delimiter', '\n');
    store_num = length(input_data{1});
    iteration = 1;
    store_inventories = cell(1);
    for i = 1:store_num
        line = strsplit(input_data{1}{iteration});
        store_inventories{iteration} = line;
        iteration = iteration + 1;
    end
    storePrice = containers.Map;
    for i = 1:length(store_inventories)
       line = store_inventories{i};
       product_name = 'product';
       for j = 2:length(line)
           if (mod(j,2) == 1)
               if isKey(storePrice, product_name)
                   product_map = storePrice(product_name);
                   product_map(line{1}) = line{j};
                   storePrice(product_name) = product_map;
               else
                   storePrice(product_name) = containers.Map(line{1}, line{j});
               end
           else
               product_name = line{j};
           end
       end
    end
    fclose(fileId);
%     This is an example of how to use a resulted map    
%     storePrice_keys = keys(storePrice);
%     for i=1:numel(storePrice_keys)
%         product_name = storePrice_keys(i);
%         disp(storePrice(product_name{1}));
%     end
end