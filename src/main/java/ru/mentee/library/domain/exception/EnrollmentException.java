/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.exception;

public class EnrollmentException extends RuntimeException {
    public EnrollmentException(String message) {
        super(message);
    }

    public EnrollmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
