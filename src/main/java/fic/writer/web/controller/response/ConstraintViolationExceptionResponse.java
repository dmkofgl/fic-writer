package fic.writer.web.controller.response;

import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.ConstraintViolation;

@Getter
public class ConstraintViolationExceptionResponse extends ResourceSupport {
    private String field;
    private Object value;
    private String message;

    public ConstraintViolationExceptionResponse(ConstraintViolation constraintViolation) {
        field = constraintViolation.getPropertyPath().toString();
        message = constraintViolation.getMessage();
        value = constraintViolation.getInvalidValue();
    }
}
