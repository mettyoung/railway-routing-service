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
        List<Station> origins = railway.findStations(originStationName);
        List<Station> targets = railway.findStations(targetStationName);

        // BFS
        Queue<Station> frontier = new LinkedList<>();
        Set<Station> visitedSet = new HashSet<>();

        // Path construction
        Map<Station, List<Station>> trail = new HashMap<>();

        for (Station origin : origins) {
            if (origin.isOperational()) {
                frontier.add(origin);
                while (!frontier.isEmpty()) {
                    Station current = frontier.remove();
                    visitedSet.add(current);

                    for (Station next : railway.getAdjacentStations(current)) {
                        boolean junctionStationAtOrigin = origin.atJunctionWith(current) && current.atJunctionWith(next);
                        if (next.isOperational() && !junctionStationAtOrigin && !visitedSet.contains(next)) {
                            if (!trail.containsKey(next)) {
                                trail.put(next, new ArrayList<>());
                            }

                            if (!targets.get(0).atJunctionWith(next)) {
                                frontier.add(next);
                            }
                            trail.get(next).add(current);
                        }
                    }
                }
            }
        }

        return derivePaths(targets, trail);
    }

    private static List<RailwayPath> derivePaths(List<Station> heads, Map<Station, List<Station>> tree) {
        List<RailwayPath> paths = new ArrayList<>();

        for (Station head : heads) {
            Stack<Station> trail = new Stack<>();
            getAllPathsFromHeadToLeaves(head, tree, trail, path -> {
                List<RailwayEdge> edges = new ArrayList<>();
                for (int i = path.size() - 1; i > 0; i--) {
                    Station start = path.get(i);
                    Station end = path.get(i - 1);
                    edges.add(new RailwayEdge(start, end));
                }
                if (!edges.isEmpty()) {
                    paths.add(new RailwayPath(edges));
                }
            });
        }

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
