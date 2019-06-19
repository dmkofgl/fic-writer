package fic.writer.web.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity handleValidationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(ex.getConstraintViolations().stream().map(ConstraintViolationExceptionResponse::new));
    }
}
