/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.api.exception;

public class ValidationException extends BankingException {
    public ValidationException(String message, String errorCode) {
        super(message, errorCode);
    }
}
