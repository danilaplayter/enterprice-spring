/* (C) 2025 MENTEE POWER */

package ru.mentee.power.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mentee.power.service.support.EventNotFoundException;
import ru.mentee.power.service.support.SeatAlreadyBookedException;
import ru.mentee.power.service.support.SeatNotAvailableException;
import ru.mentee.power.service.support.SeatNotBelongToEventException;
import ru.mentee.power.service.support.SeatNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler({EventNotFoundException.class, SeatNotFoundException.class})
  public ResponseEntity<String> handleNotFound(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler({
    SeatNotAvailableException.class,
    SeatAlreadyBookedException.class,
    SeatNotBelongToEventException.class
  })
  public ResponseEntity<String> handleBadRequest(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
