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

public class ProfileValidationTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateProfile_whenUsernameIsEmpty_shouldThrowException() {
        String propertyPath = "username";
        Profile profile = Profile.builder().username("").build();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);
        Optional<ConstraintViolation<Profile>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenUsernameisNull_shouldThrowException() {
        String propertyPath = "username";
        Profile profile = Profile.builder().username(null).build();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);
        Optional<ConstraintViolation<Profile>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenUsernameLengthIsLessThanTwo_shouldThrowException() {
        String propertyPath = "username";
        Profile profile = Profile.builder().username("a").build();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);
        Optional<ConstraintViolation<Profile>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenUsernameIsValid_shouldNotFoundViolation() {
        String propertyPath = "username";
        Profile profile = Profile.builder().username("username").build();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);
        Optional<ConstraintViolation<Profile>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertFalse(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenEmailNotBlank_shouldThrowException() {
        String propertyPath = "email";
        Profile profile = Profile.builder().email("email").build();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);
        Optional<ConstraintViolation<Profile>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenEmailWithoutDot_shouldThrowException() {
        String propertyPath = "email";
        Profile profile = Profile.builder().email("email@cc").build();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);
        Optional<ConstraintViolation<Profile>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenEmailContainLessThanTwoAfterDot_shouldNotFoundViolation() {
        String propertyPath = "email";
        Profile profile = Profile.builder().email("email@cc.c").build();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);
        Optional<ConstraintViolation<Profile>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenEmailValid_shouldNotFoundViolation() {
        String propertyPath = "email";
        Profile profile = Profile.builder().email("email@cc.com").build();

        Set<ConstraintViolation<Profile>> violations = validator.validate(profile);
        Optional<ConstraintViolation<Profile>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertFalse(optional.isPresent());
    }
}