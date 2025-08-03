/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.api.exception;

public class BankingException extends RuntimeException {
    private final String errorCode;

    public BankingException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
