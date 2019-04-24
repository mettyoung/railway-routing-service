package com.mettyoung.railwayroutingservice.routing;

import com.mettyoung.railwayroutingservice.railway.RailwayPath;

import java.util.Comparator;

public class StationTravelledHeuristic implements Comparator<RailwayPath> {

    @Override
    public int compare(RailwayPath self, RailwayPath other) {
        return self.getRailwayEdges().size() - other.getRailwayEdges().size();
    }
}
