package Utils;

import models.Building;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static Map<String, Map<Integer, Long>> countBuildingsGroupByCityAndFloors(Collection<Building> addresses) {
        Map<String, Map<Integer, Long>> buildingsGroupByCityAndFloors = new HashMap<>();

        Map<String, List<Building>> buildingsGroupByCity = addresses.stream()
                .distinct()
                .collect(Collectors.groupingBy(Building::getCity));


        buildingsGroupByCity.forEach((city, buildings) -> {
            Map<Integer, Long> collect = buildings.stream()
                    .collect(Collectors.groupingBy(Building::getFloor, Collectors.counting()));
            buildingsGroupByCityAndFloors.put(city, collect);
        });

        return buildingsGroupByCityAndFloors;
    }
}
