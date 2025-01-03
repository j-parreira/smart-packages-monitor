package logistics.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.stream.Collectors;

public class MyConstraintViolationException extends Exception {
    public MyConstraintViolationException(ConstraintViolationException e) {
        super(getConstraintViolationMessages(e));
    }

    public MyConstraintViolationException(String field, String message) {
        super(field + ":" + message);
    }

    public MyConstraintViolationException(String message) {
        super(message);
    }

    private static String getConstraintViolationMessages(ConstraintViolationException e) {
        return e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("; "));
    }
}
