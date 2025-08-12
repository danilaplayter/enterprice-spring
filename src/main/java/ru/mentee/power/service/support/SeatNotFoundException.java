/* (C) 2025 MENTEE POWER */

package ru.mentee.power.service.support;

public class SeatNotFoundException extends RuntimeException {
  public SeatNotFoundException(Long id) {
    super("Seat not found: " + id);
  }
}
