/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mentee.library.model.Course;

public interface CourseRepository extends JpaRepository<Course, String> {
    List<Course> findAll();

    Optional<Course> findById(String courseId);
}
