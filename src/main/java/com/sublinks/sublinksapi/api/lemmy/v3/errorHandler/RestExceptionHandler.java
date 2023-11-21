package com.sublinks.sublinksapi.api.lemmy.v3.errorHandler;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    final List<String> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }

    final ApiError apiError =
        new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return handleExceptionInternal(
        ex, apiError, headers, apiError.getStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status,
      WebRequest request
  ) {

    String error = ex.getParameterName() + " parameter is missing";

    final ApiError apiError =
        new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<Object> handleConstraintViolation(
      ConstraintViolationException ex, WebRequest request) {

    final List<String> errors = new ArrayList<String>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      errors.add(violation.getRootBeanClass().getName() + " " +
          violation.getPropertyPath() + ": " + violation.getMessage());
    }

    final ApiError apiError =
        new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {

    final String error =
        ex.getName() + " should be of type " + ex.getRequiredType().getName();

    final ApiError apiError =
        new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({ResponseStatusException.class})
  public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {

    final ApiError apiError = new ApiError(
        ex.getStatusCode(), ex.getLocalizedMessage(), "error occurred");
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({ExpiredJwtException.class})
  public ResponseEntity<Object> handleAll(ExpiredJwtException ex, WebRequest request) {

    final ApiError apiError = new ApiError(
        HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), "expired login");
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

    final ApiError apiError = new ApiError(
        HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
    return new ResponseEntity<Object>(
        apiError, new HttpHeaders(), apiError.getStatus());
  }
}
