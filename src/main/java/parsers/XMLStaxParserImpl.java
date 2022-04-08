package parsers;

import models.Building;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XMLStaxParserImpl implements Parser {

    @Override
    public Map<Building, Integer> getAddressesWithFrequency(String path) throws IOException {
        Map<Building, Integer> addresses = new HashMap<>();

        try (StaxStreamProcessor staxStreamProcessor = new StaxStreamProcessor(new FileInputStream(path))) {
            XMLStreamReader reader = staxStreamProcessor.getReader();

            while (reader.hasNext()) {
                reader.next();

                if (reader.isStartElement() && "item".equals(reader.getLocalName())) {
                    String city = reader.getAttributeValue(null, "city");
                    String street = reader.getAttributeValue(null, "street");
                    String house = reader.getAttributeValue(null, "house");
                    String floor = reader.getAttributeValue(null, "floor");

                    if (city == null || floor == null) {
                        return null;
                    }

                    Building building = new Building(city, street, house, Integer.parseInt(floor));
                    addresses.merge(building, 1, Integer::sum);
                }

            }
        } catch (XMLStreamException e) {
            return null;
        }
        return addresses;
    }
}
