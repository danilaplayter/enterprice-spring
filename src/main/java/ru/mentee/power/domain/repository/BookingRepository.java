/* (C) 2025 MENTEE POWER */

package ru.mentee.power.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mentee.power.domain.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  boolean existsBySeatIdAndStatusNot(Long seatId, String status);
}
