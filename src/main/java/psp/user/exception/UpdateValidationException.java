package psp.user.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateValidationException extends RuntimeException {

    private String fieldName;

    private String message;

    public UpdateValidationException(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
