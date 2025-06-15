package com.hsbc.techtest.exceptions.errorhandlers;

import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;

/** The type Error response. */
public record ErrorResponse(HttpStatus status, String message, List<String> errors) {

  /**
   * Instantiates a new Error response.
   *
   * @param status the status
   * @param message the message
   * @param errors the errors
   */
  public ErrorResponse {
    if (errors == null) {
      errors = Collections.emptyList();
    } else {
      errors = List.copyOf(errors);
    }
  }
}
