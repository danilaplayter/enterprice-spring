/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.validation.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import ru.mentee.banking.validation.validator.TransferValidator;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = TransferValidator.class)
public @interface ValidTransfer {
    String message() default "{transfer.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String countryCode() default "RU";
}
