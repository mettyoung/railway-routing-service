package com.mettyoung.railwayroutingservice.railway;

import java.util.Collections;
import java.util.List;

public class RailwayFactory {

    public static Railway buildRailway(List<Station> stations) {
        Collections.sort(stations);

        Railway railway = new Railway();
        for (int i = 0; i < stations.size(); i++) {
            Station currentStation = stations.get(i);
            railway.addStation(currentStation);
            if (i > 0) {
                Station previousStation = stations.get(i - 1);
                if (previousStation.sameLineWith(currentStation)) {
                    railway.addConnection(stations.get(i - 1), stations.get(i));
                }
            }
        }

        for (Station station: railway.getAllStations()) {
            List<Station> candidateJunctionStations = railway.findStations(station.getName());
            if (candidateJunctionStations.size() > 1) {
                for (int i = 1; i < candidateJunctionStations.size(); i++) {
                    railway.addConnection(candidateJunctionStations.get(i - 1), candidateJunctionStations.get(i));
                }
            }
        }

        return railway;
    }
}
