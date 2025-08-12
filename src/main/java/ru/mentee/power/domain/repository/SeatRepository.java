/* (C) 2025 MENTEE POWER */

package ru.mentee.power.domain.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import ru.mentee.power.domain.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT s FROM Seat s WHERE s.id = :id")
  Optional<Seat> findByIdForUpdate(Long id);

  Optional<Seat> findByEventIdAndSeatNumber(Long eventId, String seatNumber);

  @Query("SELECT COUNT(s) FROM Seat s WHERE s.event.id = :eventId AND s.status = 'BOOKED'")
  int countBookedSeatsByEventId(Long eventId);
}
