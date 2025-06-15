package com.hsbc.techtest;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsbc.techtest.config.AppTestConfig;
import com.hsbc.techtest.config.TestcontainersConfiguration;
import com.hsbc.techtest.exceptions.errorhandlers.ErrorResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

/** The type Techtest application tests. */
@Import({TestcontainersConfiguration.class, AppTestConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableWireMock({@ConfigureWireMock(name = "weathermap-mock", port = 8888)})
@ActiveProfiles("it")
class TechtestApplicationTests {

  private static final String URL = "http://localhost:8080/api/v1/cities/";

  /** The Rest template. */
  @Autowired TestRestTemplate restTemplate;

  /** The Mapper. */
  ObjectMapper mapper = new ObjectMapper();

  /** The Json. */
  String json;

  /** Sets up. */
  @BeforeEach
  @SneakyThrows
  void setUp() {
    json = loadResourceFile("weathermap_response_1.json");
    stubFor(
        get("/data/2.5/box/city?bbox=12%2C32%2C15%2C37%2C10&appid=b6907d289e10d714a6e88b30761fae22")
            .willReturn(ok(json)));
  }

  /** Should fetch cities with one match. */
  @Test
  void shouldFetchCitiesWithOneMatch() {

    // given

    // when
    ResponseEntity<List> responseEntity = restTemplate.getForEntity(URL + "Y", List.class);

    // then
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().size()).isEqualTo(1);
    assertThat(responseEntity.getBody()).contains("Yafran");
  }

  /** Should fetch cities with one match and spaces. */
  @Test
  void shouldFetchCitiesWithOneMatchAndSpaces() {

    // given

    // when
    ResponseEntity<List> responseEntity = restTemplate.getForEntity(URL + "Y  ", List.class);

    // then
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().size()).isEqualTo(1);
    assertThat(responseEntity.getBody()).contains("Yafran");
  }

  /** Should fetch cities with three matches and spaces. */
  @Test
  void shouldFetchCitiesWithThreeMatchesAndSpaces() {

    // given

    // when
    ResponseEntity<List> responseEntity = restTemplate.getForEntity(URL + "Z  ", List.class);

    // then
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().size()).isEqualTo(3);
    assertThat(responseEntity.getBody()).contains("Zuwarah", "Zawiya", "Zlitan");
  }

  /** Should not fetch cities with no match. */
  @Test
  @SneakyThrows
  void shouldNotFetchCitiesWithNoMatch() {

    // given

    // when
    ResponseEntity<ErrorResponse> responseEntity =
        restTemplate.getForEntity(URL + "X", ErrorResponse.class);

    // then
    assertThat(responseEntity).isNotNull();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isNotNull();
    ErrorResponse errorResponse = responseEntity.getBody();
    assertThat(errorResponse).isNotNull();
    assertThat(errorResponse.status()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(errorResponse.message()).isEqualTo("No cities found starting with X");
  }

  /**
   * Load resource file string.
   *
   * @param filePath the file path
   * @return the string
   * @throws IOException the io exception
   */
  @SneakyThrows
  public String loadResourceFile(String filePath) throws IOException {
    Resource resource = new ClassPathResource(filePath);
    return String.join("\n", Files.readAllLines(Paths.get(resource.getURI())));
  }
}
