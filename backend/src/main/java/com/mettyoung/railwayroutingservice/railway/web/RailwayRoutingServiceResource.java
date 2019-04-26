package com.mettyoung.railwayroutingservice.railway.web;

import com.mettyoung.railwayroutingservice.railway.Railway;
import com.mettyoung.railwayroutingservice.railway.RailwayPath;
import com.mettyoung.railwayroutingservice.railway.RailwayRoutingService;
import com.mettyoung.railwayroutingservice.railway.Station;
import com.mettyoung.railwayroutingservice.railway.StationTravelledHeuristic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/railway-routing-service")
public class RailwayRoutingServiceResource {

    @Autowired
    private RailwayRoutingService railwayRoutingService;

    @Autowired
    private Railway railway;

    @GetMapping(value = "/compute-path", produces = "application/json")
    public List<RailwayPath> computePathAsJson(@Valid RouteRequest routeRequest) {
        List<RailwayPath> railwayPaths = railwayRoutingService.computePaths(routeRequest.getOrigin(), routeRequest.getTarget());
        railwayPaths.sort(new StationTravelledHeuristic());
        return railwayPaths;
    }

    @GetMapping(value = "/compute-path", produces = "text/plain")
    public String computePathAsPlainText(@Valid RouteRequest routeRequest) {
        List<RailwayPath> railwayPaths = railwayRoutingService.computePaths(routeRequest.getOrigin(), routeRequest.getTarget());
        railwayPaths.sort(new StationTravelledHeuristic());
        return RoutePlainTextPresenter.present(routeRequest, railwayPaths);
    }

    @GetMapping("/stations")
    public List<Station> getStations() {
        return railway.getAllStations();
    }
}
