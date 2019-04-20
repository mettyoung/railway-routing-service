package com.mettyoung.railwayroutingservice.railway;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RailwayTest {

    @Test
    public void should_be_able_to_get_one_station_from_railway_given_station_name() {
        Station one = new Station("code", "one");
        Station two = new Station("two");

        Railway railway = new Railway()
                .addStation(one)
                .addStation(two);

        assertThat(railway.findStation("one"), sameInstance(one));
        assertThat(railway.findStation("two"), sameInstance(two));
    }

    @Test
    public void should_be_able_to_create_a_railway_given_two_adjacent_stations() {
        Station one = new Station("one");
        Station two = new Station("two");

        Railway railway = new Railway()
                .addStation(one)
                .addStation(two)
                .addConnection(one, two);

        assertThat(railway.getAdjacentStations(one), hasSize(1));
        assertThat(railway.getAdjacentStations(one), hasItem(sameInstance(two)));
        assertThat(railway.getAdjacentStations(two), hasSize(1));
        assertThat(railway.getAdjacentStations(two), hasItem(sameInstance(one)));
    }
}
