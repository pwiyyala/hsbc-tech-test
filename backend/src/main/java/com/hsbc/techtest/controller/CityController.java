package com.hsbc.techtest.controller;

import com.hsbc.techtest.service.CitySearchService;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type City controller.
 */
@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
@Slf4j
public class CityController {

  private final CitySearchService citySearchService;

  /**
   * Gets city names.
   *
   * @param cityPrefix the city prefix
   * @return the city names
   */
  @GetMapping(value = "/cities/{cityPrefix}")
  public List<String> getCityNames(@NotBlank @PathVariable String cityPrefix) {
    log.info("Received request to search for cities starting with: {}", cityPrefix);
    List<String> cityNames = citySearchService.getCityName(cityPrefix);
    log.info("Search completed. Found {} cities starting with '{}'", cityNames.size(), cityPrefix);
    return cityNames;
  }
}
