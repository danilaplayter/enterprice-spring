/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.mentee.banking.validation.annotation.BIC;

public class BICValidator implements ConstraintValidator<BIC, String> {

    @Autowired private MessageSource messageSource;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value.matches("^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            messageSource.getMessage(
                                    "bic.invalid", null, LocaleContextHolder.getLocale()))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
