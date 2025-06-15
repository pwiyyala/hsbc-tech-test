package com.hsbc.techtest.feign;

import com.hsbc.techtest.config.WeatherMapClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/** The interface Weather map client. */
@FeignClient(value = "weatherMapClient", configuration = WeatherMapClientConfiguration.class)
public interface WeatherMapClient {

  /**
   * Gets weather map.
   *
   * @param bbox the bbox
   * @param appid the appid
   * @return the weather map
   */
  @GetMapping(value = "data/2.5/box/city")
  Optional<String> getWeatherMap(@RequestParam String bbox, @RequestParam String appid);
}
