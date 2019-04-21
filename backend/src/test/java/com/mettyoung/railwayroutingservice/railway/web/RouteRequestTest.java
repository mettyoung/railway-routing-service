package com.mettyoung.railwayroutingservice.railway.web;

import com.mettyoung.railwayroutingservice.railway.Railway;
import org.hamcrest.core.Every;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasToString;

public class RouteRequestTest {

    private Validator validator;

    @Bean
    Railway railway() {
        return new Railway();
    }

    @Autowired
    ApplicationContext context;

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_validate_origin_and_target_not_empty() {
        RouteRequest routeRequest = new RouteRequest();

        Set<ConstraintViolation<RouteRequest>> violations = validator.validate(routeRequest);

        assertThat(violations, containsInAnyOrder(
                hasProperty("propertyPath", hasToString(equalTo("origin"))),
                hasProperty("propertyPath", hasToString(equalTo("target")))
        ));

        assertThat(violations, Every.everyItem(
                hasProperty("messageTemplate", hasToString(containsString("NotEmpty")))
        ));
    }
}
