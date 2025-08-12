/* (C) 2025 MENTEE POWER */

package ru.mentee.power.service.support;

public class SeatAlreadyBookedException extends RuntimeException {
  public SeatAlreadyBookedException(Long seatId) {
    super("Seat already booked: " + seatId);
  }
}
