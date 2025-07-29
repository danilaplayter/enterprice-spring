/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mentee.learning.service.CourseService;
import ru.mentee.library.api.CoursesApi;
import ru.mentee.library.model.CourseList;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController implements CoursesApi {
    private final CourseService courseService;

    @Override
    public ResponseEntity<CourseList> apiV1CoursesGet(
            String accept, String category, String level) {
        CourseList courseList = courseService.getCourses(category, level);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(accept != null ? accept : "application/json"))
                .body(courseList);
    }

    @Override
    public ResponseEntity<Void> apiV1CoursesCourseIdEnrollPost(String courseId) {
        courseService.enrollToCourse(courseId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .header("Location", "/api/v1/courses/" + courseId + "/enroll/status")
                .build();
    }

    @Override
    public ResponseEntity<Void> apiV1UsersMeProgressGet() {
        return ResponseEntity.ok().build();
    }
}
