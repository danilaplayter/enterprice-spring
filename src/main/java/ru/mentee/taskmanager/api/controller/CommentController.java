/* @MENTEE_POWER (C)2025 */
package ru.mentee.taskmanager.api.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mentee.api.generated.controller.CommentsApi;
import ru.mentee.api.generated.dto.Comment;
import ru.mentee.api.generated.dto.CommentListResponse;
import ru.mentee.taskmanager.api.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class CommentController implements CommentsApi {

    private final Map<UUID, List<Comment>> comments = new ConcurrentHashMap<>();

    @Override
    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CommentListResponse> getTaskComments(@PathVariable UUID taskId) {
        if (!comments.containsKey(taskId)) {
            throw new ResourceNotFoundException("Task", "id", taskId);
        }

        List<Comment> allComments = comments.getOrDefault(taskId, Collections.emptyList());

        CommentListResponse response =
                new CommentListResponse().content(allComments).totalElements(allComments.size());

        return ResponseEntity.ok(response);
    }
}
