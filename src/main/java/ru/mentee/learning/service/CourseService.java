/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mentee.learning.domain.repository.CourseRepository;
import ru.mentee.library.model.Course;
import ru.mentee.library.model.CourseList;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public void enrollToCourse(String courseId) {
        Course course =
                courseRepository
                        .findById(courseId)
                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Course not found with id: " + courseId));

        System.out.println("Processing enrollment for course: " + course.getTitle());
    }

    public CourseList getCourses(String category, String level) {
        List<Course> filteredCourses =
                courseRepository.findAll().stream()
                        .filter(c -> category == null || c.getCategory().equals(category))
                        .filter(c -> level == null || c.getLevel().name().equals(level))
                        .toList();
        return new CourseList().courses(filteredCourses).totalCount(filteredCourses.size());
    }
}
