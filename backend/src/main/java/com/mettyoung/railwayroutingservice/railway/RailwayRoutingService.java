package com.mettyoung.railwayroutingservice.railway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

public class RailwayRoutingService {

    private Railway railway;

    public RailwayRoutingService(Railway railway) {
        this.railway = railway;
    }

    public List<RailwayPath> computePaths(String originStationName, String targetStationName) {
        Station origin = railway.findStation(originStationName);
        Station target = railway.findStation(targetStationName);

        if (originStationName.equals(targetStationName)
                || !origin.isOperational()
                || !target.isOperational()) {
            return new ArrayList<>();
        }

        // BFS
        Queue<Station> frontier = new LinkedList<>();
        Set<Station> visitedSet = new HashSet<>();

        // Path construction
        Map<Station, List<Station>> trail = new HashMap<>();

        frontier.add(origin);
        while (!frontier.isEmpty()) {
            Station current = frontier.remove();
            visitedSet.add(current);

            for (Station next : railway.getAdjacentStations(current)) {
                if (!visitedSet.contains(next)) {
                    if (!trail.containsKey(next)) {
                        trail.put(next, new ArrayList<>());
                    }
                    if (!target.equals(next)) {
                        frontier.add(next);
                    }
                    trail.get(next).add(current);
                }
            }
        }

        return derivePaths(target, trail);
    }

    private static List<RailwayPath> derivePaths(Station head, Map<Station, List<Station>> tree) {
        List<RailwayPath> paths = new ArrayList<>();
        Stack<Station> trail = new Stack<>();

        getAllPathsFromHeadToLeaves(head, tree, trail, path -> {
            List<RailwayEdge> edges = new ArrayList<>();
            for (int i = path.size() - 1; i > 0; i--) {
                Station start = path.get(i);
                Station end = path.get(i - 1);
                edges.add(new RailwayEdge(start, end));
            }
            paths.add(new RailwayPath(edges));
        });

        return paths;
    }

    private static void getAllPathsFromHeadToLeaves(Station head, Map<Station, List<Station>> tree, Stack<Station> trail, Consumer<List<Station>> pathEmitter) {
        trail.push(head);

        // Leaf condition
        if (!tree.containsKey(head)) {
            pathEmitter.accept(trail);
        }
        else {
            List<Station> children = tree.get(head);

            for (Station child : children) {
                getAllPathsFromHeadToLeaves(child, tree, trail, pathEmitter);
                trail.pop();
            }
        }
    }
}
