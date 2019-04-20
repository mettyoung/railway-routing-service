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
    public void should_provide_a_route_from_start_to_end_given_one_straight_path_of_3_stations() {
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

    @Test
    public void should_provide_a_route_from_start_to_end_given_a_graph_with_two_dead_ends_and_one_path() {
        Station one = new Station("one");
        Station two = new Station("two");
        Station three = new Station("three");
        Station dangledOne = new Station("dangledOne");
        Station dangledTwo = new Station("dangledTwo");
        Railway railway = new Railway()
                .addStation(one)
                .addStation(two)
                .addStation(three)
                .addStation(dangledOne)
                .addStation(dangledTwo)
                .addConnection(one, two)
                .addConnection(two, three)
                .addConnection(one, dangledOne)
                .addConnection(two, dangledTwo);

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

    @Test
    public void should_provide_two_routes_from_start_to_end_given_a_graph_with_two_paths() {
        Station one = new Station("one");
        Station two = new Station("two");
        Station three = new Station("three");
        Station four = new Station("four");
        Railway railway = new Railway()
                .addStation(one)
                .addStation(two)
                .addStation(three)
                .addStation(four)
                .addConnection(one, two)
                .addConnection(one, three)
                .addConnection(two, four)
                .addConnection(three, four);

        RailwayRoutingService railwayRoutingService = new RailwayRoutingService(railway);
        List<RailwayPath> paths = railwayRoutingService.computePaths("one", "four");

        assertThat(paths, hasSize(2));
        assertThat(paths.get(0).getRailwayEdges(), hasSize(2));
        assertThat(paths.get(0).getRailwayEdges(), contains(
                allOf(
                        hasProperty("startStation", sameInstance(one)),
                        hasProperty("endStation", sameInstance(two))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(two)),
                        hasProperty("endStation", sameInstance(four))
                )
        ));
        assertThat(paths.get(1).getRailwayEdges(), hasSize(2));
        assertThat(paths.get(1).getRailwayEdges(), contains(
                allOf(
                        hasProperty("startStation", sameInstance(one)),
                        hasProperty("endStation", sameInstance(three))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(three)),
                        hasProperty("endStation", sameInstance(four))
                )
        ));
    }

    @Test
    public void should_provide_two_routes_from_start_to_end_given_a_graph_with_two_unequal_paths() {
        Station one = new Station("one");
        Station two = new Station("two");
        Station three = new Station("three");
        Station four = new Station("four");
        Station five = new Station("five");
        Station six = new Station("six");
        Railway railway = new Railway()
                .addStation(one)
                .addStation(two)
                .addStation(three)
                .addStation(four)
                .addStation(five)
                .addStation(six)
                .addConnection(one, two)
                .addConnection(one, three)
                .addConnection(two, four)
                .addConnection(three, six)
                .addConnection(four, five)
                .addConnection(five, six);

        RailwayRoutingService railwayRoutingService = new RailwayRoutingService(railway);
        List<RailwayPath> paths = railwayRoutingService.computePaths("one", "six");

        assertThat(paths, hasSize(2));
        assertThat(paths.get(0).getRailwayEdges(), hasSize(2));
        assertThat(paths.get(0).getRailwayEdges(), contains(
                allOf(
                        hasProperty("startStation", sameInstance(one)),
                        hasProperty("endStation", sameInstance(three))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(three)),
                        hasProperty("endStation", sameInstance(six))
                )
        ));
        assertThat(paths.get(1).getRailwayEdges(), hasSize(4));
        assertThat(paths.get(1).getRailwayEdges(), contains(
                allOf(
                        hasProperty("startStation", sameInstance(one)),
                        hasProperty("endStation", sameInstance(two))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(two)),
                        hasProperty("endStation", sameInstance(four))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(four)),
                        hasProperty("endStation", sameInstance(five))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(five)),
                        hasProperty("endStation", sameInstance(six))
                )
        ));
    }

    @Test
    public void should_provide_three_routes_from_start_to_end_given_a_graph_with_three_unequal_path_and_two_dead_ends_and_one_cycle() {
        Station one = new Station("one");
        Station two = new Station("two");
        Station three = new Station("three");
        Station four = new Station("four");
        Station dangledFour = new Station("dangledFour");
        Station five = new Station("five");
        Station six = new Station("six");
        Station dangledSix = new Station("dangledSix");
        Station seven = new Station("seven");
        Station eight = new Station("eight");
        Station nine = new Station("nine");
        Station ten = new Station("ten");

        Railway railway = new Railway()
                .addStation(one)
                .addStation(two)
                .addStation(three)
                .addStation(four)
                .addStation(five)
                .addStation(six)
                .addStation(seven)
                .addStation(dangledFour)
                .addStation(dangledSix)
                .addStation(eight)
                .addStation(nine)
                .addStation(ten)
                .addConnection(one, two)
                .addConnection(one, three)
                .addConnection(one, four)
                .addConnection(two, five)
                .addConnection(three, six)
                .addConnection(four, seven)
                .addConnection(four, dangledFour)
                .addConnection(six, five)
                .addConnection(six, dangledSix)
                .addConnection(seven, eight)
                .addConnection(eight, five)
                .addConnection(eight, nine)
                .addConnection(eight, ten);

        RailwayRoutingService railwayRoutingService = new RailwayRoutingService(railway);
        List<RailwayPath> paths = railwayRoutingService.computePaths("one", "five");

        assertThat(paths, hasSize(3));
        assertThat(paths.get(0).getRailwayEdges(), hasSize(2));
        assertThat(paths.get(0).getRailwayEdges(), contains(
                allOf(
                        hasProperty("startStation", sameInstance(one)),
                        hasProperty("endStation", sameInstance(two))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(two)),
                        hasProperty("endStation", sameInstance(five))
                )
        ));
        assertThat(paths.get(1).getRailwayEdges(), hasSize(3));
        assertThat(paths.get(1).getRailwayEdges(), contains(
                allOf(
                        hasProperty("startStation", sameInstance(one)),
                        hasProperty("endStation", sameInstance(three))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(three)),
                        hasProperty("endStation", sameInstance(six))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(six)),
                        hasProperty("endStation", sameInstance(five))
                )
        ));
        assertThat(paths.get(2).getRailwayEdges(), hasSize(4));
        assertThat(paths.get(2).getRailwayEdges(), contains(
                allOf(
                        hasProperty("startStation", sameInstance(one)),
                        hasProperty("endStation", sameInstance(four))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(four)),
                        hasProperty("endStation", sameInstance(seven))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(seven)),
                        hasProperty("endStation", sameInstance(eight))
                ),
                allOf(
                        hasProperty("startStation", sameInstance(eight)),
                        hasProperty("endStation", sameInstance(five))
                )
        ));
    }

    @Test
    public void should_provide_no_path_if_origin_is_same_with_target() {
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
        List<RailwayPath> paths = railwayRoutingService.computePaths("one", "one");

        assertThat(paths, hasSize(0));
    }
}
