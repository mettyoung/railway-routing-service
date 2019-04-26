package com.mettyoung.railwayroutingservice.railway;

import java.util.Comparator;

public class StationTravelledHeuristic implements Comparator<RailwayPath> {

    @Override
    public int compare(RailwayPath self, RailwayPath other) {
        return self.getRailwayEdges().size() - other.getRailwayEdges().size();
    }
}
