/* (C) 2025 MENTEE POWER */

package ru.mentee.power.service.support;

public class SeatNotAvailableException extends RuntimeException {
  public SeatNotAvailableException(Long seatId, String status) {
    super("Seat " + seatId + " not available. Status: " + status);
  }
}
