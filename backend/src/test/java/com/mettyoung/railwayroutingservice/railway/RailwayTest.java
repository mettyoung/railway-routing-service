package com.mettyoung.railwayroutingservice.railway;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RailwayTest {

    @Test
    public void should_be_able_to_get_one_station_from_railway_given_one_station_in_a_station_name() {
        Station one = new Station("one", "group");

        Railway railway = new Railway();
        railway.addStation(one);

        assertThat(railway.findStations("group"), hasSize(1));
        assertThat(railway.findStations("group"), hasItem(sameInstance(one)));
    }

    @Test
    public void should_be_able_to_get_two_stations_from_railway_given_two_stations_having_the_same_station_name() {
        Station one = new Station("one", "group");
        Station two = new Station("two", "group");
        Station three = new Station("three", "another-group");

        Railway railway = new Railway();
        railway.addStation(one)
                .addStation(two)
                .addStation(three);

        assertThat(railway.findStations("group"), hasSize(2));
        assertThat(railway.findStations("group"), hasItems(sameInstance(one), sameInstance(two)));
    }
}
