/* (C) 2025 MENTEE POWER */

package ru.mentee.power.service.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mentee.power.domain.model.AuditLog;
import ru.mentee.power.domain.repository.AuditLogRepository;
import ru.mentee.power.service.AuditService;
import ru.mentee.power.service.model.BookingRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {
  private final AuditLogRepository auditLogRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void auditBooking(BookingRequest request, String status, String details) {
    AuditLog auditLog = new AuditLog();
    auditLog.setAction("BOOKING_ATTEMPT");
    auditLog.setUserId(request.getUserId().toString());
    auditLog.setEventId(request.getEventId().toString());
    auditLog.setSeatId(request.getSeatId().toString());
    auditLog.setStatus(status);
    auditLog.setDetails(details);
    auditLog.setTimestamp(LocalDateTime.now());

    auditLogRepository.save(auditLog);

    log.info(
        "Audit logged: {} - User: {}, Event: {}, Seat: {}, Status: {}",
        auditLog.getAction(),
        auditLog.getUserId(),
        auditLog.getEventId(),
        auditLog.getSeatId(),
        auditLog.getStatus());
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void auditUserAction(String userId, String action, String details) {
    AuditLog auditLog = new AuditLog();
    auditLog.setAction(action);
    auditLog.setUserId(userId);
    auditLog.setStatus("COMPLETED");
    auditLog.setDetails(details);
    auditLog.setTimestamp(LocalDateTime.now());

    auditLogRepository.save(auditLog);

    log.info("User action audited: {} - User: {}, Details: {}", action, userId, details);
  }
}
