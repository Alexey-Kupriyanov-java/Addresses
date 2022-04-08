import Utils.Utils;
import models.Building;
import parsers.CSVParserImpl;
import parsers.Parser;
import parsers.XMLStaxParserImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Map<Building, Integer> addressesWithFrequency;
        Map<String, Map<Integer, Long>> buildingsGroupByCityAndFloors;

        while (true) {
            System.out.print("Specify the path to the file with the addresses (type 'exit' for exit): ");
            String path = scanner.nextLine();
            if ("exit".equals(path)) {
                System.out.println("Application execution finished");
                break;
            }

            Parser parser = getParser(path);
            if (parser == null) {
                System.out.println(path + " (Unsupported file format)");
                continue;
            }

            try {
                addressesWithFrequency = parser.getAddressesWithFrequency(path);
            } catch (FileNotFoundException e) {
                System.out.println(path + " (The file not found)");
                continue;
            } catch (IOException e) {
                System.out.println(path + " (IO exception)");
                continue;
            }

            if (addressesWithFrequency == null || addressesWithFrequency.size() == 0) {
                System.out.println(path + " (The file is not valid or empty)");
                continue;
            }

            buildingsGroupByCityAndFloors = Utils.countBuildingsGroupByCityAndFloors(addressesWithFrequency.keySet());

            displayDuplicates(addressesWithFrequency);
            displayBuildingsStatistic(buildingsGroupByCityAndFloors);

        }

        scanner.close();
    }

    private static Parser getParser(String path) {
        Parser parser;

        if (path.endsWith(".xml")) {
            parser = new XMLStaxParserImpl();
        } else if (path.endsWith(".csv")) {
            parser = new CSVParserImpl();
        } else {
            parser = null;
        }

        return parser;
    }

    private static void displayDuplicates(Map<Building, Integer> addressesWithFrequency) {
        System.out.println("Duplicates:");
        addressesWithFrequency.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .forEach(e -> System.out.printf("The address \"%s\" occurs %s times.%n", e.getKey().toString(), e.getValue()));
        System.out.println();
    }

    private static void displayBuildingsStatistic(Map<String, Map<Integer, Long>> buildingsGroupByCityAndFloors) {
        System.out.println("Number of buildings by city and number of floors:");
        buildingsGroupByCityAndFloors.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(System.out::println);
        System.out.println();
    }
}
