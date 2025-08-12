/* (C) 2025 MENTEE POWER */

package ru.mentee.power.api.controller;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.power.service.impl.EventReadService;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventQueryController {

  private final EventReadService eventReadService;

  @GetMapping("/{id}/summary")
  public CompletableFuture<Map<String, Object>> getSummary(@PathVariable Long id) {
    return eventReadService
        .findEvent(id)
        .thenApply(
            event -> {
              if (event == null) return Map.of("found", false);
              return Map.of(
                  "found", true,
                  "eventId", event.getId(),
                  "name", event.getName(),
                  "bookedSeats", event.getBookedSeats(),
                  "totalSeats", event.getTotalSeats());
            });
  }
}
