/* @MENTEE_POWER (C)2025 */
package ru.mentee.banking.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import ru.mentee.banking.validation.validator.BICValidator;

@Documented
@Constraint(validatedBy = BICValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BIC {
    String message() default "{bic.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String countryCode() default "RU";
}
