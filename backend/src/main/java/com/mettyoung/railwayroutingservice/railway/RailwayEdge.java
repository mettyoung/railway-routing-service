package com.mettyoung.railwayroutingservice.railway;

import lombok.Getter;

@Getter
public class RailwayEdge {

    private Station startStation;
    private Station endStation;

    public RailwayEdge(Station startStation, Station endStation) {
        this.startStation = startStation;
        this.endStation = endStation;
    }
}
