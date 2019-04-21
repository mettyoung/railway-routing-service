package com.mettyoung.railwayroutingservice.railway;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Design consideration:
 *
 * As far as I can see, OpenCSV does not support bean conversion for the newer Date APIs (e.g. LocalDate). Since we
 * are already in 2019, we should be using the new Date APIs. For this reason, I had to implement a custom bean
 * conversion from a string value to LocalDate.
 *
 * I consider that it is right to couple the LocalDate bean conversion to this domain model because the date format
 * used in the CSV file would always be tightly coupled to this domain model (e.g. the date format is domain-specific).
 *
 */
@Getter
@EqualsAndHashCode(of = "code", callSuper = false)
@ToString(of = "code")
@NoArgsConstructor
public class Station extends AbstractBeanField<LocalDate> implements Comparable<Station> {

    @CsvBindByName(column = "Station Code", required = true)
    private String code;

    @CsvBindByName(column = "Station Name", required = true)
    private String name;

    @CsvCustomBindByName(column = "Opening Date", required = true, converter = Station.class)
    private LocalDate openingDate;

    private String line;
    private Integer number;

    public Station(String code, String name) {
        setCode(code);
        this.name = name;
    }

    public Station(String code) {
        this(code, code);
    }

    public void setCode(String code) {
        if (!code.matches("^[A-Z]{2}\\d+$")) {
            throw new IllegalArgumentException("Station code should consist of two capital letters followed by " +
                    "a numerical value");
        }
        this.code = code;
        this.line = code.substring(0, 2);
        this.number = Integer.valueOf(code.substring(2));
    }

    @Override
    protected LocalDate convert(String value) {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("d MMMM yyyy"));
    }

    @Override
    public int compareTo(Station other) {
        int alphabeticalOrder = line.compareTo(other.line);
        if (alphabeticalOrder == 0) {
            return number.compareTo(other.number);
        }
        return alphabeticalOrder;
    }

    public boolean sameLineWith(Station otherStation) {
        return getLine().equals(otherStation.getLine());
    }
}
