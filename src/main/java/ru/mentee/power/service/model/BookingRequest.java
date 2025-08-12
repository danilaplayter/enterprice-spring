/* (C) 2025 MENTEE POWER */

package ru.mentee.power.service.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookingRequest {
  @NotNull Long eventId;
  @NotNull Long seatId;
  @NotNull Long userId;
}
