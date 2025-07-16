/* @MENTEE_POWER (C)2025 */
package ru.mentee.taskmanager.api.controller;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.api.generated.controller.TasksApi;
import ru.mentee.api.generated.dto.Comment;
import ru.mentee.api.generated.dto.CreateTaskRequest;
import ru.mentee.api.generated.dto.JsonPatchOperation;
import ru.mentee.api.generated.dto.Task;
import ru.mentee.api.generated.dto.Task.StatusEnum;
import ru.mentee.api.generated.dto.TaskListResponse;
import ru.mentee.api.generated.dto.UpdateTaskRequest;
import ru.mentee.taskmanager.api.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class TaskController implements TasksApi {

    private final Map<UUID, Task> tasks = new ConcurrentHashMap<>();
    private final Map<UUID, List<Comment>> comments = new ConcurrentHashMap<>();

    @Override
    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequest request) {
        Task task =
                new Task()
                        .id(UUID.randomUUID())
                        .title(request.getTitle())
                        .description(request.getDescription())
                        .status(StatusEnum.TODO)
                        .createdAt(OffsetDateTime.now());

        tasks.put(task.getId(), task);
        return ResponseEntity.status(201).body(task);
    }

    @Override
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new ResourceNotFoundException("Task", "id", taskId);
        }

        tasks.remove(taskId);
        comments.remove(taskId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/task/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new ResourceNotFoundException("Task", "id", taskId);
        }
        return ResponseEntity.ok(task);
    }

    @Override
    @GetMapping("/tasks")
    public ResponseEntity<TaskListResponse> getTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        List<Task> filteredTasks =
                tasks.values().stream()
                        .filter(
                                t ->
                                        status == null
                                                || t.getStatus()
                                                        .toString()
                                                        .equalsIgnoreCase(status))
                        .filter(
                                t ->
                                        assignee == null
                                                || (t.getAssignee() != null
                                                        && t.getAssignee().equals(assignee)))
                        .filter(
                                t ->
                                        priority == null
                                                || (t.getPriority() != null
                                                        && t.getPriority()
                                                                .toString()
                                                                .equalsIgnoreCase(priority)))
                        .sorted(
                                (t1, t2) -> {
                                    if (sort == null) return 0;
                                    if (sort.equalsIgnoreCase("createdAt:asc")) {
                                        return t1.getCreatedAt().compareTo(t2.getCreatedAt());
                                    } else if (sort.equalsIgnoreCase("createdAt:desc")) {
                                        return t2.getCreatedAt().compareTo(t1.getCreatedAt());
                                    }
                                    return 0;
                                })
                        .toList();

        if (page == null || size == null) {
            return ResponseEntity.ok(
                    new TaskListResponse()
                            .content(filteredTasks)
                            .totalElements(filteredTasks.size()));
        }

        int totalElements = filteredTasks.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        if (page < 0 || (page > 0 && page >= totalPages)) {
            return ResponseEntity.ok(
                    new TaskListResponse()
                            .content(Collections.emptyList())
                            .page(page)
                            .size(size)
                            .totalElements(totalElements)
                            .totalPages(totalPages));
        }

        int from = page * size;
        int to = Math.min(from + size, totalElements);
        List<Task> paginatedTasks = filteredTasks.subList(from, to);

        return ResponseEntity.ok(
                new TaskListResponse()
                        .content(paginatedTasks)
                        .page(page)
                        .size(size)
                        .totalElements(totalElements)
                        .totalPages(totalPages));
    }

    @Override
    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<Void> patchTask(
            @PathVariable UUID taskId, @RequestBody List<JsonPatchOperation> jsonPatchOperation) {

        Task task = tasks.get(taskId);
        if (task == null) {
            throw new ResourceNotFoundException("Task", "id", taskId);
        }

        if (!tasks.containsKey(taskId)) {
            return ResponseEntity.notFound().build();
        }
        for (JsonPatchOperation op : jsonPatchOperation) {
            if (op.getOp().equals("replace") && op.getPath().equals("/status")) {
                task.setStatus(StatusEnum.fromValue(op.getValue().toString()));
            }
        }
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(
            @PathVariable UUID taskId, @RequestBody UpdateTaskRequest updateTaskRequest) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new ResourceNotFoundException("Task", "id", taskId);
        }

        if (!tasks.containsKey(taskId)) {
            return ResponseEntity.notFound().build();
        }
        task =
                tasks.get(taskId)
                        .title(updateTaskRequest.getTitle())
                        .description(updateTaskRequest.getDescription())
                        .status(Task.StatusEnum.valueOf(updateTaskRequest.getStatus().name()))
                        .priority(Task.PriorityEnum.valueOf(updateTaskRequest.getPriority().name()))
                        .assignee(updateTaskRequest.getAssignee());
        return ResponseEntity.ok(task);
    }
}
