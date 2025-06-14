package com.hsbc.techtest.service;

import com.hsbc.techtest.feign.WeatherMapClient;
import com.hsbc.techtest.model.WeatherMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class CitySearchService {

    private final WeatherMapClient weatherMapClient;

    public String getCityName(String searchPrefix) {

        // Call the WeatherMapClient to get the city name based on the search prefix
        WeatherMap weatherMap = weatherMapClient.getWeatherMap();

        Optional.ofNullable(weatherMap)
                .stream()
                .map(WeatherMap::list)
                .filter()

        return cityName;
    }

}
