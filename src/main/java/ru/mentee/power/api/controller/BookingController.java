/* (C) 2025 MENTEE POWER */

package ru.mentee.power.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.power.service.impl.BookingService;
import ru.mentee.power.service.model.BookingRequest;
import ru.mentee.power.service.model.BookingResponse;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookingResponse book(@Validated @RequestBody BookingRequest request) {
    return bookingService.bookSeat(request);
  }
}
