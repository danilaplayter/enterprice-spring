/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.exception;

public class CourseNotFoundException extends RuntimeException {
    private final String courseId;

    public CourseNotFoundException(String courseId) {
        super("Course not found with id: " + courseId);
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }
}
