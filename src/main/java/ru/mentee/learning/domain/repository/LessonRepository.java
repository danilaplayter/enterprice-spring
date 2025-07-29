/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.domain.repository;

import ru.mentee.learning.domain.model.Lesson;

public interface LessonRepository {
    Lesson findByCourseIdAndLessonId(String courseId, String lessonId);

    Lesson save(Lesson lesson);
}
