/* (C)2024 */
package com.hsbc.techtest.exceptions.errorhandlers;

import com.hsbc.techtest.exceptions.InvalidRequestException;
import com.hsbc.techtest.exceptions.ResourceNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** The type Api exception handler. */
@ControllerAdvice
@Order(1)
@Slf4j
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle invalid request response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = InvalidRequestException.class)
  public ResponseEntity<ErrorResponse> handleInvalidRequest(InvalidRequestException e) {
    log.error("Invalid request with validation errors: {}", e.getMessage(), e);

    return ResponseEntity.badRequest()
        .body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), List.of()));
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    return ResponseEntity.badRequest()
        .body(
            new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), List.of(errors.toString())));
  }

  /**
   * Handle record not found exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleRecordNotFoundException(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), List.of()));
  }

  /**
   * Handle global errors response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<ErrorResponse> handleGlobalErrors(Exception ex) {
    log.error(
        "Request failed with errorType {} and errorMessage {}",
        ex.getClass().getTypeName(),
        ex.getMessage());
    ErrorResponse error =
        new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Something went wrong!!",
            List.of(ex.getClass().getTypeName()));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
