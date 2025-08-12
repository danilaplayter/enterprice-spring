/* (C) 2025 MENTEE POWER */

package ru.mentee.power.service.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookingResponse {
  Long bookingId;
  String status;
  String seatNumber;
  String eventName;
}
