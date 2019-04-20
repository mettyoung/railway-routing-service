package com.mettyoung.railwayroutingservice.railway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Railway {

    private Map<Station, List<Station>> adjacencyList;
    private Map<String, Station> stationByName;

    public Railway() {
        adjacencyList = new HashMap<>();
        stationByName = new HashMap<>();
    }

    public Railway addStation(Station station) {
        adjacencyList.put(station, new ArrayList<>());
        stationByName.put(station.getName(), station);
        return this;
    }

    public Railway addConnection(Station first, Station second) {
        adjacencyList.get(first).add(second);
        adjacencyList.get(second).add(first);
        return this;
    }

    public List<Station> getAdjacentStations(Station station) {
        return adjacencyList.get(station);
    }

    public Station findStation(String stationName) {
        return stationByName.get(stationName);
    }
}
