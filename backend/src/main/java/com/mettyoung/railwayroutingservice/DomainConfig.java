package com.mettyoung.railwayroutingservice;

import com.mettyoung.railwayroutingservice.common.parser.CsvToBeanParser;
import com.mettyoung.railwayroutingservice.railway.Railway;
import com.mettyoung.railwayroutingservice.railway.RailwayFactory;
import com.mettyoung.railwayroutingservice.railway.RailwayRoutingService;
import com.mettyoung.railwayroutingservice.railway.Station;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Configuration
public class DomainConfig {

    @Value("${railway.csvPath:classpath:StationMap.csv}")
    private String csvPath;

    @Bean
    public Railway buildRailway() throws IOException {
        File file = ResourceUtils.getFile(csvPath);
        List<Station> stations = CsvToBeanParser.parseCsvToBean(file, Station.class);

        return RailwayFactory.buildRailway(stations);
    }

    @Bean
    public RailwayRoutingService buildRailwayRoutingService() throws IOException {
        return new RailwayRoutingService(buildRailway());
    }
}
