package com.mettyoung.railwayroutingservice.railway;

import lombok.Getter;

@Getter
public class Station {

    private String code;
    private String name;

    public Station(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
