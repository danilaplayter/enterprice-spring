/* (C) 2025 MENTEE POWER */

package ru.mentee.power.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "audit_logs")
public class AuditLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String action;

  @Column(name = "user_id")
  private String userId;

  @Column(name = "event_id")
  private String eventId;

  @Column(name = "seat_id")
  private String seatId;

  @Column(nullable = false, length = 50)
  private String status;

  @Column(columnDefinition = "TEXT")
  private String details;

  @Column(nullable = false)
  private LocalDateTime timestamp;
}
