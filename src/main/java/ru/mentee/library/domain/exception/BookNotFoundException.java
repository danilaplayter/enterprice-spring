/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.domain.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(long id) {
        super("Book with ID " + id + " not found");
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookNotFoundException(long id, Throwable cause) {
        super("Book with ID " + id + " not found", cause);
    }
}
