package com.hsbc.techtest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsbc.techtest.exceptions.ResourceNotFoundException;
import com.hsbc.techtest.feign.WeatherMapClient;
import com.hsbc.techtest.model.LocationInfo;
import com.hsbc.techtest.model.WeatherMap;
import java.util.List;
import java.util.Optional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * The type City search service test.
 */
@ExtendWith(MockitoExtension.class)
class CitySearchServiceTest {

  private static final String LONDON = "London";
  private static final String LOS_ANGELES = "Los Angeles";
  private static final LocationInfo LOCATION_1 = new LocationInfo(LONDON);
  private static final LocationInfo LOCATION_2 = new LocationInfo(LOS_ANGELES);
  private static final String LO_PREFIX = "Lo";
  private static final String NEW_PREFIX = "New";

  /** The Object mapper. */
  ObjectMapper objectMapper = new ObjectMapper();

  @Mock private WeatherMapClient weatherMapClient;
  @InjectMocks private CitySearchService citySearchService;

  /**
   * Sets up.
   */
@BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(citySearchService, "objectMapper", objectMapper);
    ReflectionTestUtils.setField(citySearchService, "bbox", "12,32,15,37,10");
    ReflectionTestUtils.setField(citySearchService, "appid", "b6907d289e10d714a6e88b30761fae22");
  }

  /**
   * Should return cities matching search prefix.
   */
@SneakyThrows
  @Test
  void shouldReturnCitiesMatchingSearchPrefix() {
    // Arrange
    WeatherMap weatherMap = new WeatherMap(List.of(LOCATION_1, LOCATION_2));
    given(
            weatherMapClient.getWeatherMap(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
        .willReturn(Optional.of(objectMapper.writeValueAsString(weatherMap)));

    // Act
    List<String> result = citySearchService.getCityName(LO_PREFIX);

    // Assert
    assertThat(result).containsExactly(LONDON, LOS_ANGELES);
  }

  /**
   * Should return empty list when no cities match search prefix.
   */
@SneakyThrows
  @Test
  void shouldReturnEmptyListWhenNoCitiesMatchSearchPrefix() {
    // Arrange
    WeatherMap weatherMap = new WeatherMap(List.of(LOCATION_1, LOCATION_2));
    given(
            weatherMapClient.getWeatherMap(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
        .willReturn(Optional.of(objectMapper.writeValueAsString(weatherMap)));

    // Act
    ResourceNotFoundException exception =
            Assertions.assertThrows(
                    ResourceNotFoundException.class, () -> citySearchService.getCityName(NEW_PREFIX));

    // Assert
    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("No cities found starting with New");
  }

  /**
   * Should return empty list when weather map is null.
   */
@Test
  void shouldReturnEmptyListWhenWeatherMapIsNull() {
    // Arrange
    given(
            weatherMapClient.getWeatherMap(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
        .willReturn(Optional.empty());

    // Act
    ResourceNotFoundException exception =
        Assertions.assertThrows(
            ResourceNotFoundException.class, () -> citySearchService.getCityName(LO_PREFIX));

    // Assert
    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Weather map data not found");
  }

  /**
   * Should return empty list when weather map has empty list.
   */
@SneakyThrows
  @Test
  void shouldReturnEmptyListWhenWeatherMapHasEmptyList() {
    // Arrange
    WeatherMap weatherMap = new WeatherMap(List.of());
    given(
            weatherMapClient.getWeatherMap(
                ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
        .willReturn(Optional.of(objectMapper.writeValueAsString(weatherMap)));

    // Act
    ResourceNotFoundException exception =
            Assertions.assertThrows(
                    ResourceNotFoundException.class, () -> citySearchService.getCityName(LO_PREFIX));

    // Assert
    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("No cities found starting with Lo");
  }
}
