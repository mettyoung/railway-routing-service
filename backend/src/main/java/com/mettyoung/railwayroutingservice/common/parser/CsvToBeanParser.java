package com.mettyoung.railwayroutingservice.common.parser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CsvToBeanParser {

    public static <T> List<T> parseCsvToBean(File file, Class<T> beanType) throws IOException {
        final HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(beanType);

        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file))) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(inputStreamReader)
                    .withMappingStrategy(strategy)
                    .build();
            return csvToBean.parse();
        }
    }
}
