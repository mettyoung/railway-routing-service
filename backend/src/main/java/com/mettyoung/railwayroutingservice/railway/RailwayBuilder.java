package com.mettyoung.railwayroutingservice.railway;

import com.mettyoung.railwayroutingservice.common.parser.CsvToBeanParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class RailwayBuilder {

    @Value("${railway.csvPath:classpath:StationMap.csv}")
    private String csvPath;

    @PostConstruct
    public void buildRailway() throws IOException {
        File file = ResourceUtils.getFile(csvPath);
        CsvToBeanParser.parseCsvToBean(file, Station.class)
                .forEach(System.out::println);
    }
}
