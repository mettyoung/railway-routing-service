package com.mettyoung.railwayroutingservice.railway.web;

import com.mettyoung.railwayroutingservice.railway.RailwayPath;
import com.mettyoung.railwayroutingservice.railway.RailwayRoutingService;
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

    @GetMapping("/compute-path")
    public String computePath(@Valid RouteRequest routeRequest) {
        List<RailwayPath> railwayPaths = railwayRoutingService.computePaths(routeRequest.getOrigin(), routeRequest.getTarget());
        return RoutePlainTextPresenter.present(routeRequest, railwayPaths);
    }
}