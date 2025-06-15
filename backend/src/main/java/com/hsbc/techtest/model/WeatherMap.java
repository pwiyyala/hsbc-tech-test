package com.hsbc.techtest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.List;

/**
 * The type Weather map.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherMap(List<LocationInfo> list){

    /**
     * Instantiates a new Weather map.
     *
     * @param list the list
     */
public WeatherMap {
    if(list == null) {
        list = Collections.emptyList();
    } else {
        list = List.copyOf(list);
    }
    }
}
