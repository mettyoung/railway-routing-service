package com.mettyoung.railwayroutingservice.railway;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class RailwayTest {

    @Test
    public void should_be_able_to_get_one_station_from_railway_given_one_station_in_a_station_name() {
        Station one = new Station("one", "group");
        Railway railway = new Railway();
        railway.addStation(one);

        Assert.assertThat(railway.findStations("group"), hasSize(1));
        Assert.assertThat(railway.findStations("group"), hasItem(sameInstance(one)));
    }
}
