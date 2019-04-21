package com.mettyoung.railwayroutingservice.railway;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RailwayFactoryTest {

    private final Station NS1 = new Station("NS1", "Jurong East");
    private final Station NS2 = new Station("NS2", "Bukit Batok");
    private final Station NS3 = new Station("NS3", "Bukit Gombak");
    private final Station CC21 = new Station("CC21", "Holland Village");
    private final Station CC22 = new Station("CC22", "Buona Vista");
    private final Station CC23 = new Station("CC23", "one-north");
    private final Station EW19 = new Station("EW19", "Queenstown");
    private final Station EW20 = new Station("EW20", "Commonwealth");
    private final Station EW21 = new Station("EW21", "Buona Vista");

    private final List<Station> stations = Arrays.asList(NS1, NS3, EW19, NS2, CC21, EW20, CC22, CC23, EW21);

    @Test
    public void should_create_a_railway_whose_connections_are_derived_sequentially_belonging_to_the_same_line() {
        Railway railway = RailwayFactory.buildRailway(stations);

        assertThat(railway.getAdjacentStations(NS1), contains(sameInstance(NS2)));
        assertThat(railway.getAdjacentStations(NS2), contains(sameInstance(NS1), sameInstance(NS3)));
        assertThat(railway.getAdjacentStations(NS3), contains(sameInstance(NS2)));

        assertThat(railway.getAdjacentStations(CC21), contains(sameInstance(CC22)));
        assertThat(railway.getAdjacentStations(CC22), hasItems(sameInstance(CC21), sameInstance(CC23)));
        assertThat(railway.getAdjacentStations(CC23), contains(sameInstance(CC22)));

        assertThat(railway.getAdjacentStations(EW19), contains(sameInstance(EW20)));
        assertThat(railway.getAdjacentStations(EW20), contains(sameInstance(EW19), sameInstance(EW21)));
        assertThat(railway.getAdjacentStations(EW21), contains(sameInstance(EW20)));
    }
}
