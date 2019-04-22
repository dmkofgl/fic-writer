package fic.writer.web.controller;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileControllerAdvice {
    @ExceptionHandler(EnumConstantNotPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Resource<String> unexpectedExtension(EnumConstantNotPresentException e) {
        return new Resource<>("unsupported extension: " + e.constantName());
    }

}
