package com.mettyoung.railwayroutingservice.railway;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Station {

    private String code;
    private String name;
    private List<Station> adjacentStations;

    public Station(String code, String name) {
        this.code = code;
        this.name = name;
        this.adjacentStations = new LinkedList<>();
    }

    public void addAdjacentStation(Station station) {
        adjacentStations.add(station);
    }
}