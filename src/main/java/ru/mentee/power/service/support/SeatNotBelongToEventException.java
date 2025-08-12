/* (C) 2025 MENTEE POWER */

package ru.mentee.power.service.support;

public class SeatNotBelongToEventException extends RuntimeException {
  public SeatNotBelongToEventException(Long seatId, Long eventId) {
    super("Seat " + seatId + " does not belong to event " + eventId);
  }
}
