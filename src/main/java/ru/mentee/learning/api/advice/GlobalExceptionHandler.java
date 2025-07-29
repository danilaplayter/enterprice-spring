/* @MENTEE_POWER (C)2025 */
package ru.mentee.learning.api.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.mentee.learning.domain.model.ApiError;
import ru.mentee.library.domain.exception.CourseNotFoundException;
import ru.mentee.library.domain.exception.EnrollmentException;
import ru.mentee.library.domain.exception.RateLimitExceededException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Object> handleCourseNotFound(
            CourseNotFoundException ex, WebRequest request) {
        ApiError apiError =
                new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND,
                        "Course not found",
                        ex.getMessage(),
                        request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EnrollmentException.class)
    public ResponseEntity<Object> handleEnrollmentError(
            EnrollmentException ex, WebRequest request) {
        ApiError apiError =
                new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST,
                        "Enrollment error",
                        ex.getMessage(),
                        request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<Object> handleRateLimitExceeded(
            RateLimitExceededException ex, WebRequest request) {
        ApiError apiError =
                new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.TOO_MANY_REQUESTS,
                        "Rate limit exceeded",
                        ex.getMessage(),
                        request.getDescription(false));

        Map<String, String> headers = new HashMap<>();
        headers.put("X-RateLimit-Remaining", String.valueOf(ex.getRemaining()));
        headers.put("X-RateLimit-Reset", String.valueOf(ex.getResetTime()));

        return buildResponseEntity(apiError, headers);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceeded(
            MaxUploadSizeExceededException ex, WebRequest request) {
        ApiError apiError =
                new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.PAYLOAD_TOO_LARGE,
                        "File too large",
                        "Maximum upload size exceeded",
                        request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError apiError =
                new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal server error",
                        ex.getMessage(),
                        request.getDescription(false));
        return buildResponseEntity(apiError);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> errors =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(
                                fieldError ->
                                        fieldError.getField()
                                                + ": "
                                                + fieldError.getDefaultMessage())
                        .collect(Collectors.toList());

        ApiError apiError =
                new ApiError(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST,
                        "Validation error",
                        "Invalid request parameters",
                        request.getDescription(false));
        apiError.setSubErrors(errors);

        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private ResponseEntity<Object> buildResponseEntity(
            ApiError apiError, Map<String, String> headers) {
        HttpHeaders responseHeaders = new HttpHeaders();
        headers.forEach(responseHeaders::add);
        return new ResponseEntity<>(apiError, responseHeaders, apiError.getStatus());
    }
}
