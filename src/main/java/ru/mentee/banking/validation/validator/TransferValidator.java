/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import ru.mentee.api.generated.dto.TransferRequest;
import ru.mentee.banking.validation.annotation.ValidTransfer;

public class TransferValidator implements ConstraintValidator<ValidTransfer, TransferRequest> {

    @Autowired private MessageSource messageSource;

    @Override
    public boolean isValid(TransferRequest request, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (request.getFromAccount().equals(request.getToAccount())) {
            addError(context, "toAccount", "transfer.same-account");
            isValid = false;
        }

        if ("URGENT".equals(request.getUrgency()) && request.getAmount().doubleValue() > 500000) {
            addError(context, "amount", "transfer.urgent-limit");
            isValid = false;
        }

        return isValid;
    }

    private void addError(ConstraintValidatorContext context, String field, String messageKey) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                        messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale()))
                .addPropertyNode(field)
                .addConstraintViolation();
    }
}
