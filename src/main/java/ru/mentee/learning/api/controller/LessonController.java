/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.mentee.learning.service.LessonService;
import ru.mentee.library.api.LessonsApi;

@RestController
@RequiredArgsConstructor
public class LessonController implements LessonsApi {
    private final LessonService lessonService;

    @Override
    public ResponseEntity<Void> apiV1CoursesCourseIdLessonsLessonIdVideoPost(
            String courseId, String lessonId, MultipartFile file, String title, Integer duration) {

        lessonService.uploadLessonVideo(courseId, lessonId, file, title, duration);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
