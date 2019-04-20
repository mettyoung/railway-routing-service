package com.mettyoung.railwayroutingservice.routing;

import com.mettyoung.railwayroutingservice.railway.Railway;
import com.mettyoung.railwayroutingservice.railway.RailwayPath;
import com.mettyoung.railwayroutingservice.railway.RailwayRoutingService;
import com.mettyoung.railwayroutingservice.railway.Station;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class RailwayRoutingServiceTest {

    @Test
    public void should_provide_a_route_from_start_to_end_given_one_path_of_3_stations() {
        Station one = new Station("one");
        Station two = new Station("two");
        Station three = new Station("three");
        Railway railway = new Railway()
                .addStation(one)
                .addStation(two)
                .addStation(three)
                .addConnection(one, two)
                .addConnection(two, three);

        RailwayRoutingService railwayRoutingService = new RailwayRoutingService(railway);
        List<RailwayPath> paths = railwayRoutingService.computePaths("one", "three");

        assertThat(paths, hasSize(1));
        assertThat(paths.get(0).getRailwayEdges(), hasSize(2));
        assertThat(paths.get(0).getRailwayEdges(), contains(
                allOf(
                        hasProperty("startStation", sameInstance(one)),
                        hasProperty("endStation", sameInstance(two))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(two)),
                        hasProperty("endStation", sameInstance(three))
                )
        ));
    }
}
