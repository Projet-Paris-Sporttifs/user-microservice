package psp.user.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GenderValidator.class)
public @interface GenderValidation {
    String message() default "Invalid gender: must be male, female or other";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}