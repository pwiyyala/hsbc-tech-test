package com.hsbc.techtest.feign;

import com.hsbc.techtest.config.WeatherMapClientConfiguration;
import com.hsbc.techtest.model.WeatherMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "weatherMapClient", configuration = WeatherMapClientConfiguration.class)
public interface WeatherMapClient {

    @GetMapping(value = "data/2.5/box/city?bbox=12,32,15,37,10&appid=b6907d289e10d714a6e88b30761fae22")
    WeatherMap getWeatherMap();

}
