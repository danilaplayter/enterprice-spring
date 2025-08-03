/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
import ru.mentee.banking.validation.validator.IBANValidator;

@Documented
@Constraint(validatedBy = IBANValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IBAN {
    String message() default "{iban.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String countryCode() default "RU";
}
