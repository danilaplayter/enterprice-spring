/* (C) 2025 MENTEE POWER */

package ru.mentee.power.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mentee.power.domain.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
  List<AuditLog> findByUserIdOrderByTimestampDesc(String userId);

  List<AuditLog> findByEventIdOrderByTimestampDesc(String eventId);

  List<AuditLog> findByActionAndStatus(String action, String status);

  @Query("SELECT a FROM AuditLog a WHERE a.timestamp BETWEEN :startDate AND :endDate")
  List<AuditLog> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);

  @Query("SELECT COUNT(a) FROM AuditLog a WHERE a.userId = :userId AND a.action = :action")
  long countByUserIdAndAction(String userId, String action);
}
