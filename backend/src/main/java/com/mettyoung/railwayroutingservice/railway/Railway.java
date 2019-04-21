package com.mettyoung.railwayroutingservice.railway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Railway {

    private Map<Station, List<Station>> adjacencyList;
    private Map<String, List<Station>> stationsByName;

    public Railway() {
        adjacencyList = new HashMap<>();
        stationsByName = new HashMap<>();
    }

    public Railway addStation(Station station) {
        adjacencyList.put(station, new ArrayList<>());
        if (!stationsByName.containsKey(station.getName())) {
            stationsByName.put(station.getName(), new ArrayList<>());
        }
        stationsByName.get(station.getName()).add(station);
        return this;
    }

    public Railway addConnection(Station first, Station second) {
        if (!adjacencyList.get(first).contains(second)) {
            adjacencyList.get(first).add(second);
            adjacencyList.get(second).add(first);
        }
        return this;
    }

    public List<Station> getAdjacentStations(Station station) {
        return adjacencyList.get(station);
    }

    public Station findStation(String stationName) {
        return stationsByName.get(stationName).get(0);
    }

    public List<Station> findStations(String stationName) {
        return stationsByName.get(stationName);
    }

    public List<Station> getAllStations() {
        return adjacencyList.entrySet().stream().map(Map.Entry::getKey).collect(toList());
    }

    public boolean isStationNameExisting(String stationName) {
        return stationsByName.containsKey(stationName);
    }
}
