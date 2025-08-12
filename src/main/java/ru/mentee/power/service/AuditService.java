/* (C) 2025 MENTEE POWER */

package ru.mentee.power.service;

import ru.mentee.power.service.model.BookingRequest;

public interface AuditService {
  void auditBooking(BookingRequest request, String status, String details);

  void auditUserAction(String userId, String action, String details);
}
