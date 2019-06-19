package fic.writer.domain.entity;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookValidationTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateBook_whenTitleIsEmpty_shouldThrowException() {
        String propertyPath = "title";
        Book book = Book.builder().title("").build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Optional<ConstraintViolation<Book>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateBook_whenTitleIsNull_shouldThrowException() {
        String propertyPath = "title";
        Book book = Book.builder().title(null).build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Optional<ConstraintViolation<Book>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateBook_whenTitleIsValid_shouldReturnEmptyViolation() {
        String propertyPath = "title";
        Book book = Book.builder().title("title").build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Optional<ConstraintViolation<Book>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertFalse(optional.isPresent());
    }

    @Test
    public void validateBook_whenAuthorIsNull_shouldReturnException() {
        String propertyPath = "author";
        Book book = Book.builder().author(null).build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Optional<ConstraintViolation<Book>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateBook_whenAuthorExists_shouldReturnEmptyViolation() {
        String propertyPath = "author";
        Profile profile = Profile.builder().build();
        Book book = Book.builder().author(profile).build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        Optional<ConstraintViolation<Book>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertFalse(optional.isPresent());
    }

    @Test
    public void validateBook_whenAuthorAndTitleExist_shouldReturnEmptyViolation() {
        String propertyPath = "author";
        Profile profile = Profile.builder().build();
        Book book = Book.builder().author(profile).title("title").build();

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertTrue(violations.isEmpty());
    }
}