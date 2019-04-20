package com.mettyoung.railwayroutingservice.railway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Railway {

    private Map<String, List<Station>> graph;

    public Railway() {
        graph = new HashMap<>();
    }

    public Railway addStation(Station station) {
        if (!graph.containsKey(station.getName())) {
            graph.put(station.getName(), new ArrayList<>());
        }
        graph.get(station.getName()).add(station);
        return this;
    }

    public List<Station> findStations(String name) {
        return graph.get(name);
    }

    public Railway addConnection(Station first, Station second) {
        first.addAdjacentStation(second);
        second.addAdjacentStation(first);
        return this;
    }
}
