package psp.user.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class GenderValidator implements ConstraintValidator<GenderValidation, String> {

    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        List<String> list = Arrays.asList("male", "female", "other");
        return value != null && list.contains(value.toLowerCase());
    }
}
