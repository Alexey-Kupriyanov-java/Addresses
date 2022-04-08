package parsers;

import models.Building;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVParserImpl implements Parser {
    private static final String DELIMITER = ";";

    @Override
    public Map<Building, Integer> getAddressesWithFrequency(String path) throws IOException {
        Map<Building, Integer> addresses = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine();

        String line;
        while((line = reader.readLine()) != null) {
            String[] splited = line.split(DELIMITER);

            if (splited.length != 4 || splited[0] == null || splited[3] == null) {
                return null;
            }

            String city = splited[0];
            String street = splited[1];
            String house = splited[2];
            String floor = splited[3];

            Building building = new Building(city, street, house, Integer.parseInt(floor));
            addresses.merge(building, 1, Integer::sum);
        }

        return addresses;
    }
}
