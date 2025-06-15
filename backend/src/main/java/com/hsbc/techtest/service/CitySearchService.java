package com.hsbc.techtest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsbc.techtest.exceptions.ResourceNotFoundException;
import com.hsbc.techtest.feign.WeatherMapClient;
import com.hsbc.techtest.model.LocationInfo;
import com.hsbc.techtest.model.WeatherMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The type City search service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CitySearchService {

  private final WeatherMapClient weatherMapClient;
  private final ObjectMapper objectMapper;

  @Value("${weather.map.bbox:12,32,15,37,10}")
  private String bbox;

  @Value("${weather.map.appid:b6907d289e10d714a6e88b30761fae22}")
  private String appid;

  /**
   * Gets city name.
   *
   * @param searchPrefix the search prefix
   * @return the city name
   */
  public List<String> getCityName(String searchPrefix) {

    log.info("Searching for city name starting with {}", searchPrefix);

    // Call the WeatherMapClient to get the city name based on the search prefix
    Optional<String> weatherMapOptional = weatherMapClient.getWeatherMap(bbox, appid);

    WeatherMap weatherMap =
        weatherMapOptional
            .map(
                s -> {
                  try {
                    return objectMapper.readValue(s, WeatherMap.class);
                  } catch (JsonProcessingException e) {
                    log.error("Error parsing weather map data: {}", e.getMessage(), e);
                    throw new ResourceNotFoundException("Weather map data not found");
                  }
                })
            .orElseThrow(() -> new ResourceNotFoundException("Weather map data not found"));

    List<String> cities =
        Optional.of(weatherMap).stream()
            .map(WeatherMap::list)
            .flatMap(List::stream)
            .map(LocationInfo::name)
            .filter(name -> name.toLowerCase().startsWith(searchPrefix.trim().toLowerCase()))
            .toList();

    log.info("Returning {} cities starting with {}", cities.size(), searchPrefix);
    log.debug("For search prefix {}. Cities found: {}", searchPrefix, cities);

    if(cities.isEmpty()) {
      throw new ResourceNotFoundException("No cities found starting with " + searchPrefix);
    }
    return cities;
  }
}
