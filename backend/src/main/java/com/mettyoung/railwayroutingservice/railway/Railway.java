package com.mettyoung.railwayroutingservice.railway;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Railway {

    private Map<String, Set<Station>> graph;

    public Railway() {
        graph = new HashMap<>();
    }

    public Railway addStation(Station station) {
        if (!graph.containsKey(station.getName())) {
            graph.put(station.getName(), new HashSet<>());
        }
        graph.get(station.getName()).add(station);
        return this;
    }

    public Set<Station> findStations(String name) {
        return graph.get(name);
    }

    public Railway addConnection(Station first, Station second) {
        first.addAdjacentStation(second);
        second.addAdjacentStation(first);
        return this;
    }
}
