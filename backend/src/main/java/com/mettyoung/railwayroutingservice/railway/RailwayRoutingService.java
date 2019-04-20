package com.mettyoung.railwayroutingservice.railway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class RailwayRoutingService {

    public static List<RailwayPath> computePaths(List<Station> origins, List<Station> goals) {
        Station origin = origins.get(0);
        Station goal = goals.get(0);

        Queue<Station> frontier = new LinkedList<>();
        Map<Station, Station> cameFrom = new HashMap<>();

        frontier.add(origin);
        cameFrom.put(origin, null);

        while (!frontier.isEmpty()) {
            Station current = frontier.remove();

            for (Station next : current.getAdjacentStations()) {
                if (!cameFrom.containsKey(next)) {
                    frontier.add(next);
                    cameFrom.put(next, current);
                }
            }
        }

        Station current = goal;
        List<RailwayEdge> edges = new ArrayList<>();
        while (cameFrom.size() > 1 && !current.equals(origin)) {
            Station trailingStation = cameFrom.get(current);
            edges.add(new RailwayEdge(trailingStation, current));
            current = trailingStation;
        }
        Collections.reverse(edges);

        return Arrays.asList(new RailwayPath(edges));
    }
}
