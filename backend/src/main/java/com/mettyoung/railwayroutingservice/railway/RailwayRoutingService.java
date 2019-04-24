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

import static java.util.stream.Collectors.toCollection;

public class RailwayRoutingService {

    private Railway railway;

    public RailwayRoutingService(Railway railway) {
        this.railway = railway;
    }

    public List<RailwayPath> computePaths(String originStationName, String targetStationName) {
        List<Station> origins = railway.findStations(originStationName);
        List<Station> targets = railway.findStations(targetStationName);

        // BFS
        Set<String> visitedSet = new HashSet<>();

        // Path construction
        Map<Station, List<Station>> trail = new HashMap<>();
        Queue<Station> frontier = origins.stream().filter(Station::isOperational).collect(toCollection(LinkedList::new));
        while (!frontier.isEmpty()) {
            Station current = frontier.remove();

            for (Station next : railway.getAdjacentStations(current)) {
                boolean junctionStationAtOrigin = origins.get(0).atJunctionWith(current) && current.atJunctionWith(next);
                String edgeCode = current.getCode().compareTo(next.getCode()) < 0? current.getCode() + next.getCode():
                        next.getCode() + current.getCode();
                if (next.isOperational() && !junctionStationAtOrigin && !visitedSet.contains(edgeCode)) {
                    if (!trail.containsKey(next)) {
                        trail.put(next, new ArrayList<>());
                    }

                    if (!targets.get(0).atJunctionWith(next)) {
                        frontier.add(next);
                    }
                    trail.get(next).add(current);
                }
                visitedSet.add(edgeCode);
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
