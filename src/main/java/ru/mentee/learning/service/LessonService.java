/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mentee.learning.domain.model.Lesson;
import ru.mentee.learning.domain.repository.LessonRepository;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final VideoStorageService videoStorageService;

    public void uploadLessonVideo(
            String courseId, String lessonId, MultipartFile file, String title, Integer duration) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Video file is required");
        }

        Lesson lesson = lessonRepository.findByCourseIdAndLessonId(courseId, lessonId);

        String videoUrl = videoStorageService.storeVideo(file);

        lesson.setVideoUrl(videoUrl);
        lesson.setVideoTitle(title);
        lesson.setVideoDuration(duration);

        lessonRepository.save(lesson);
    }
}
