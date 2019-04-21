package com.mettyoung.railwayroutingservice.railway.validator;

import com.mettyoung.railwayroutingservice.railway.Railway;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistingStationNameValidator implements ConstraintValidator<ExistingStationName, String> {

    @Autowired
    private Railway railway;

    @Override
    public boolean isValid(String stationName, ConstraintValidatorContext constraintValidatorContext) {
        return railway.isStationNameExisting(stationName);
    }
}
