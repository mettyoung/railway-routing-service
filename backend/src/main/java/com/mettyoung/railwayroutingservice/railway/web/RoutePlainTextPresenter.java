package com.mettyoung.railwayroutingservice.railway.web;

import com.mettyoung.railwayroutingservice.railway.RailwayEdge;
import com.mettyoung.railwayroutingservice.railway.RailwayPath;

import java.util.List;

class RoutePlainTextPresenter {

    static String present(RouteRequest routeRequest, List<RailwayPath> paths) {
        String summary = String.format(
                "Travel from %s to %s\n" +
                        "Paths found: %d\n",
                routeRequest.getOrigin(),
                routeRequest.getTarget(),
                paths.size()
        );

        StringBuilder stringBuilder = new StringBuilder(summary);

        for (RailwayPath path : paths) {
            stringBuilder.append("=================\n");
            stringBuilder.append(String.format("Stations travelled: %d\n", path.getRailwayEdges().size() + 1));
            stringBuilder.append("Route: (");

            StringBuilder detailBuilder = new StringBuilder();

            for (int j = 0; j < path.getRailwayEdges().size(); j++) {
                RailwayEdge railwayEdge = path.getRailwayEdges().get(j);
                stringBuilder.append(String.format("'%s',", railwayEdge.getStartStation().getCode()));
                if (j == path.getRailwayEdges().size() - 1) {
                    stringBuilder.append(String.format("'%s'", railwayEdge.getEndStation().getCode()));
                }

                if (railwayEdge.atJunction()) {
                    detailBuilder.append(String.format("Change from %s line to %s line\n", railwayEdge.getStartStation().getLine(),
                            railwayEdge.getEndStation().getLine()));
                }
                else {
                    detailBuilder.append(String.format("Take %s line from %s to %s\n", railwayEdge.getStartStation().getLine(),
                            railwayEdge.getStartStation().getName(), railwayEdge.getEndStation().getName()));
                }
            }
            stringBuilder.append(")\n\n").append(detailBuilder);
        }
        return stringBuilder.toString();
    }
}