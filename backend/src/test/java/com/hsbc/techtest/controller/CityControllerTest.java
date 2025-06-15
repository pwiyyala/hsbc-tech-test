package com.hsbc.techtest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.hsbc.techtest.service.CitySearchService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The type City controller test.
 */
@ExtendWith(MockitoExtension.class)
class CityControllerTest {

  private static final String LO = "Lo";
  private static final String LONDON = "London";
  private static final String LOS_ANGELES = "Los Angeles";
  private static final String NEW = "New";

  @Mock private CitySearchService citySearchService;

  @InjectMocks private CityController cityController;

  /**
   * Should return cities matching search prefix.
   */
@Test
  void shouldReturnCitiesMatchingSearchPrefix() {
    // Arrange
    String prefix = LO;
    List<String> cities = List.of(LONDON, LOS_ANGELES);
    given(citySearchService.getCityName(prefix)).willReturn(cities);

    // Act
    List<String> response = cityController.getCityNames(prefix);

    // Assert
    assertThat(response).containsExactly(LONDON, LOS_ANGELES);
  }

  /**
   * Should return empty list when no cities match search prefix.
   */
@Test
  void shouldReturnEmptyListWhenNoCitiesMatchSearchPrefix() {
    // Arrange
    String prefix = NEW;
    given(citySearchService.getCityName(prefix)).willReturn(List.of());

    // Act
    List<String> response = cityController.getCityNames(prefix);

    // Assert
    assertThat(response).isEmpty();
  }

  /**
   * Should handle null prefix gracefully.
   */
@Test
  void shouldHandleNullPrefixGracefully() {
    // Arrange
    String prefix = null;
    given(citySearchService.getCityName(prefix)).willReturn(List.of());

    // Act
    List<String> response = cityController.getCityNames(prefix);

    // Assert
    assertThat(response).isEmpty();
  }
}
