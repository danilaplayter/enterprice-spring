/* (C) 2025 MENTEE POWER */

package ru.mentee.power.domain.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import ru.mentee.power.domain.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT e FROM Event e WHERE e.id = :id")
  Optional<Event> findByIdForUpdate(Long id);
}
