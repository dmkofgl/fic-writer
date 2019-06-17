package fic.writer.domain.entity.dto;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookDtoValidationTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateBookDto_whenTitleIsEmpty_shouldThrowException() {
        BookDto bookDto = BookDto.builder().title("").build();

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateBookDto_whenTitleIsNull_shouldThrowException() {
        BookDto bookDto = BookDto.builder().title(null).build();

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateBookDto_whenTitleIsValid_shouldReturnEmptyViolation() {
        BookDto bookDto = BookDto.builder().title("title").build();

        Set<ConstraintViolation<BookDto>> violations = validator.validate(bookDto);
        assertTrue(violations.isEmpty());
    }

}