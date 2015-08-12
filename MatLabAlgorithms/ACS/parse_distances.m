function distance_map = parse_distances( filename )
%PARSE_DISTANCES Compose a map of a city and distances
%   Keys - city names
%   Values - vector of disances
    fileId = fopen(filename, 'r');
    rawText = textscan(fileId, '%s', 'delimiter', '\n');
    numberOfLines = length(rawText{1});


    fileId = fopen(filename, 'r');
    input_data = textscan(fileId, '%s', 'Delimiter', '\n');

    distance_map = containers.Map;
    iteration = 1;

    for i = 1:numberOfLines
        line = strsplit(input_data{1}{iteration});
        distances = line(2:numberOfLines+1);
        city_name = line(1);
        distance_map(city_name{1}) = distances;
        iteration = iteration+1;
    end
end

