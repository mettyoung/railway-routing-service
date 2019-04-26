package com.mettyoung.railwayroutingservice.routing;

import com.mettyoung.railwayroutingservice.railway.RailwayEdge;
import com.mettyoung.railwayroutingservice.railway.RailwayPath;
import com.mettyoung.railwayroutingservice.railway.Station;
import com.mettyoung.railwayroutingservice.railway.StationTravelledHeuristic;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class StationsTravelledHeuristicTest {

    @Test
    public void should_sort_the_railway_paths_by_shortest_number_of_stations_travelled() {
        Station one = new Station("NS1", "one");
        Station two = new Station("NS2", "two");
        Station three = new Station("NS3", "three");
        Station four = new Station("NS4", "four");

        List<RailwayPath> railwayPaths = Arrays.asList(
                new RailwayPath(Arrays.asList(new RailwayEdge(one, two), new RailwayEdge(two, three), new RailwayEdge(three, four))),
                new RailwayPath(Arrays.asList(new RailwayEdge(one, two), new RailwayEdge(two, three)))
        );

        railwayPaths.sort(new StationTravelledHeuristic());

        assertThat(railwayPaths, contains(
                hasProperty("railwayEdges", hasSize(2)),
                hasProperty("railwayEdges", hasSize(3))
        ));
    }
}
