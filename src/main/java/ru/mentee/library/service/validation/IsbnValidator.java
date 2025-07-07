/* @MENTEE_POWER (C)2025 */
package ru.mentee.library.service.validation;

import org.springframework.stereotype.Component;

@Component
public class IsbnValidator {

    public void validate(String isbn) {
        if (isbn == null || !isValidISBN(isbn)) {
            throw new IllegalArgumentException("Invalid ISBN");
        }
    }

    private boolean isValidISBN(String isbn) {
        return isbn.matches(
                "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$0)");
    }
}
