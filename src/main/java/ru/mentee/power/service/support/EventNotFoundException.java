/* (C) 2025 MENTEE POWER */

package ru.mentee.power.service.support;

public class EventNotFoundException extends RuntimeException {
  public EventNotFoundException(Long id) {
    super("Event not found: " + id);
  }
}
