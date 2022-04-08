package parsers;

import models.Building;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CSVParserImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Payload with 10 different rows")
    void getAddressesWithFrequency1() throws IOException {
        String path = "src\\test\\resources\\address1.csv";
        Parser parser = new CSVParserImpl();
        Map<Building, Integer> map = parser.getAddressesWithFrequency(path);
        assertEquals(10, map.size());
    }

    @Test
    @DisplayName("Payload with 5 different rows, 5 duplicate rows (2 and 3 times)")
    void getAddressesWithFrequency2() throws IOException {
        String path = "src\\test\\resources\\address2.csv";
        Parser parser = new CSVParserImpl();
        Map<Building, Integer> map = parser.getAddressesWithFrequency(path);
        assertEquals(7, map.size());
        assertEquals(5, map.values().stream().filter(e -> e == 1).count());
        assertEquals(1, map.values().stream().filter(e -> e == 2).count());
        assertEquals(1, map.values().stream().filter(e -> e == 3).count());
    }

    @Test
    @DisplayName("Payload with wrong path")
    void getAddressesWithFrequency3() throws IOException {
        assertThrows(FileNotFoundException.class,
                () -> {
                    String path = "src\\test\\resources\\nothing.csv";
                    Parser parser = new CSVParserImpl();
                    Map<Building, Integer> map = parser.getAddressesWithFrequency(path);
                });
    }


}