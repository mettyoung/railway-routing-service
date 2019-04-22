package com.mettyoung.railwayroutingservice.railway.web;

import com.mettyoung.railwayroutingservice.RailwayRoutingServiceApplication;
import com.mettyoung.railwayroutingservice.railway.Railway;
import com.mettyoung.railwayroutingservice.railway.Station;
import com.mettyoung.railwayroutingservice.railway.validator.ExistingStationName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RailwayRoutingServiceApplication.class, RouteRequestTest.BeanConfig.class})
public class RouteRequestTest {

    // Inject our own Railway to the Application Context for ease of testing.
    @Configuration
    static class BeanConfig {
        @Bean
        public Railway railway() {
            return new Railway()
                    .addStation(new Station("NS1", "one"))
                    .addStation(new Station("NS2", "two"));
        }
    }

    @Autowired
    private Validator validator;

    @Test
    public void should_validate_origin_and_target_not_empty() {
        RouteRequest routeRequest = new RouteRequest();

        Set<ConstraintViolation<RouteRequest>> violations = validator.validate(routeRequest);

        assertThat(violations, hasItems(
                hasProperty("propertyPath", hasToString(equalTo("origin"))),
                hasProperty("propertyPath", hasToString(equalTo("target"))),
                hasProperty("constraintDescriptor",
                        hasProperty("annotationDescriptor",
                                hasProperty("type", equalTo(NotEmpty.class))))
        ));
    }

    @Test
    public void should_validate_origin_and_target_station_existence() {
        RouteRequest validRouteRequest = new RouteRequest("one", "two");

        Set<ConstraintViolation<RouteRequest>> violations = validator.validate(validRouteRequest);
        assertThat(violations, hasSize(0));

        RouteRequest invalidRouteRequest = new RouteRequest("three", "four");

        violations = validator.validate(invalidRouteRequest);
        assertThat(violations, hasItems(
                hasProperty("propertyPath", hasToString(equalTo("origin"))),
                hasProperty("propertyPath", hasToString(equalTo("target"))),
                hasProperty("constraintDescriptor",
                        hasProperty("annotationDescriptor",
                                hasProperty("type", equalTo(ExistingStationName.class))))
        ));
    }
}
