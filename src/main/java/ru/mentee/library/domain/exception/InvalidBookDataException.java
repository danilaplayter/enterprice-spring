/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.exception;

public class InvalidBookDataException extends RuntimeException {
    public InvalidBookDataException(String message) {
        super(message);
    }

    public InvalidBookDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
