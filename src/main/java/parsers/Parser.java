package parsers;

import models.Building;

import java.io.IOException;
import java.util.Map;

public interface Parser {
    Map<Building, Integer> getAddressesWithFrequency(String path) throws IOException;
}
