/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.learning.service.NotificationService;
import ru.mentee.library.api.NotificationsApi;

@RestController
@AllArgsConstructor
public class NotificationController implements NotificationsApi {

    private final NotificationService notificationService;

    @Override
    public ResponseEntity<String> apiV1NotificationsStreamGet() {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body("Event stream connected");
    }
}
