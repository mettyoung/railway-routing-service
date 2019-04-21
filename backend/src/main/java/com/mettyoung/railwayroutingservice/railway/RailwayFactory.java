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

        return railway;
    }
}
