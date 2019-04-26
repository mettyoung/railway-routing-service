package com.mettyoung.railwayroutingservice.common.parser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CsvToBeanParser {

    public static <T> List<T> parseCsvToBean(InputStream inputStream, Class<T> beanType) throws IOException {
        final HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(beanType);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(new InputStreamReader(inputStream))
                .withMappingStrategy(strategy)
                .build();
        return csvToBean.parse();
    }
}
