package com.mettyoung.railwayroutingservice.railway;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "code")
@ToString(of = "code")
public class Station {

    private String code;
    private String name;

    public Station(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Station(String code) {
        this.code = code;
        this.name = code;
    }
}
