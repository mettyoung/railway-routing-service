package com.mettyoung.railwayroutingservice.railway;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RailwayEdgeTest {

    @Test
    public void should_assert_at_junction_if_start_and_end_station_belong_to_the_same_station_name() {
        RailwayEdge atJunctionStation = new RailwayEdge(
                new Station("EW1", "one"),
                new Station("EW2", "one")
        );

        RailwayEdge separateStations = new RailwayEdge(
                new Station("EW1", "one"),
                new Station("EW2", "two")
        );

        assertEquals(atJunctionStation.atJunction(), true);
        assertEquals(separateStations.atJunction(), false);
    }
}
