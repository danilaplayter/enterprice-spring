/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.api.exception;

public class BusinessRuleException extends BankingException {

    public BusinessRuleException(String message, String errorCode) {
        super(message, errorCode);
    }
}
