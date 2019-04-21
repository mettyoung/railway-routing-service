package com.mettyoung.railwayroutingservice.railway.web;

import com.mettyoung.railwayroutingservice.railway.validator.ExistingStationName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RouteRequest {

    @NotEmpty
    @ExistingStationName(message = "origin station must be existing")
    private String origin;

    @NotEmpty
    @ExistingStationName(message = "target station must be existing")
    private String target;
}
