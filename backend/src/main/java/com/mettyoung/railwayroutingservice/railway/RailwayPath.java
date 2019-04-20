package com.mettyoung.railwayroutingservice.railway;

import lombok.Getter;

import java.util.List;

@Getter
public class RailwayPath {
    private List<RailwayEdge> railwayEdges;

    public RailwayPath(List<RailwayEdge> railwayEdges) {
        this.railwayEdges = railwayEdges;
    }
}
