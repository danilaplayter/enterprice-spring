/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.mentee.banking.validation.annotation.IBAN;

public class IBANValidator implements ConstraintValidator<IBAN, String> {
    private String countryCode;
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void initialize(IBAN constraintAnnotation) {
        this.countryCode = constraintAnnotation.countryCode();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        value = value.replace(" ", "").toUpperCase();

        if (!value.startsWith(countryCode)) {
            addError(context, "iban.country-mismatch", countryCode);
            return false;
        }

        if ("RU".equals(countryCode) && value.length() != 28) {
            addError(context, "validation.ru.iban");
            return false;
        }

        if ("GB".equals(countryCode) && value.length() != 22) {
            addError(context, "validation.en.iban");
            return false;
        }
        return validateIbanChecksum(value);
    }

    private boolean validateIbanChecksum(String iban) {
        try {
            String rearranged = iban.substring(4) + iban.substring(0, 4);
            StringBuilder numeric = new StringBuilder();

            for (char c : rearranged.toCharArray()) {
                if (Character.isDigit(c)) {
                    numeric.append(c);
                } else {
                    numeric.append(Character.toUpperCase(c) - 'A' + 10);
                }
            }

            BigInteger bigInt = new BigInteger(numeric.toString());
            return bigInt.mod(BigInteger.valueOf(97)).intValue() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    private void addError(ConstraintValidatorContext context, String messageKey, Object... args) {
        context.disableDefaultConstraintViolation();
        String message =
                messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
