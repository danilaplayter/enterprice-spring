/* (C) 2025 MENTEE POWER */

package ru.mentee.power.service.impl;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.mentee.power.domain.model.Event;
import ru.mentee.power.domain.repository.EventRepository;

@Service
@RequiredArgsConstructor
public class EventReadService {

  private final EventRepository eventRepository;

  @Async("asyncExecutor")
  public CompletableFuture<Event> findEvent(Long id) {
    return CompletableFuture.supplyAsync(() -> eventRepository.findById(id).orElse(null));
  }
}
