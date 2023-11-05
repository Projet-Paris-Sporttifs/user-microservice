package psp.user.exception;

import java.util.HashMap;
import java.util.Map;

public class UniqueConstraintViolationException extends  RuntimeException {

    Map<String, String> errors = new HashMap<>();

    public void addError(String k, String v) {
        errors.put(k, v);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }
}
