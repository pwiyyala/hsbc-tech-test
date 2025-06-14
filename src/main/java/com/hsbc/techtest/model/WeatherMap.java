package com.hsbc.techtest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherMap(List<LocationInfo> list){

    public WeatherMap {
        list = List.copyOf(list);
    }
}
