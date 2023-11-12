package psp.user.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UniqueConstraintViolationException extends RuntimeException {

    private final String fieldName;

    public UniqueConstraintViolationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }
}
