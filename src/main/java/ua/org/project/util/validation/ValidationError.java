package ua.org.project.util.validation;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Petrov on 21.06.14.
 */
public class ValidationError {
    private List<FieldError> fieldErrors = new ArrayList<FieldError>();

    public ValidationError() {}

    public void addFieldError(String path, String message) {
        FieldError error = new FieldError(path, message, "");
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
