/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.mentee.banking.validation.annotation.CardNumber;

public class CardNumberValidator implements ConstraintValidator<CardNumber, String> {

    @Autowired private MessageSource messageSource;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // @NotNull проверяется отдельно
        }

        String clean = value.replaceAll("\\s+", "");

        if (!clean.matches("^[0-9]{13,19}$")) {
            addError(context, "card.number.invalid.format");
            return false;
        }

        if (!passesLuhnCheck(clean)) {
            addError(context, "card.number.invalid.checksum");
            return false;
        }

        return true;
    }

    private boolean passesLuhnCheck(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }

    private void addError(ConstraintValidatorContext context, String messageKey) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                        messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale()))
                .addConstraintViolation();
    }
}
