package fic.writer.domain.entity.dto;

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

public class ProfileDtoValidationTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateProfileDto_whenUsernameIsEmpty_shouldThrowException() {
        String propertyPath = "username";
        ProfileDto profileDto = ProfileDto.builder().username("").build();

        Set<ConstraintViolation<ProfileDto>> violations = validator.validate(profileDto);
        Optional<ConstraintViolation<ProfileDto>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenUsernameisNull_shouldThrowException() {
        String propertyPath = "username";
        ProfileDto profileDto = ProfileDto.builder().username(null).build();

        Set<ConstraintViolation<ProfileDto>> violations = validator.validate(profileDto);
        Optional<ConstraintViolation<ProfileDto>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenUsernameLengthIsLessThanTwo_shouldThrowException() {
        String propertyPath = "username";
        ProfileDto profileDto = ProfileDto.builder().username("a").build();

        Set<ConstraintViolation<ProfileDto>> violations = validator.validate(profileDto);
        Optional<ConstraintViolation<ProfileDto>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenUsernameIsValid_shouldNotFoundViolation() {
        String propertyPath = "username";
        ProfileDto profileDto = ProfileDto.builder().username("username").build();

        Set<ConstraintViolation<ProfileDto>> violations = validator.validate(profileDto);
        Optional<ConstraintViolation<ProfileDto>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertFalse(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenEmailNotBlank_shouldThrowException() {
        String propertyPath = "email";
        ProfileDto profileDto = ProfileDto.builder().email("email").build();

        Set<ConstraintViolation<ProfileDto>> violations = validator.validate(profileDto);
        Optional<ConstraintViolation<ProfileDto>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenEmailWithoutDot_shouldThrowException() {
        String propertyPath = "email";
        ProfileDto profileDto = ProfileDto.builder().email("email@cc").build();

        Set<ConstraintViolation<ProfileDto>> violations = validator.validate(profileDto);
        Optional<ConstraintViolation<ProfileDto>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenEmailContainLessThanTwoAfterDot_shouldNotFoundViolation() {
        String propertyPath = "email";
        ProfileDto profileDto = ProfileDto.builder().email("email@cc.c").build();

        Set<ConstraintViolation<ProfileDto>> violations = validator.validate(profileDto);
        Optional<ConstraintViolation<ProfileDto>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateProfileDto_whenEmailValid_shouldNotFoundViolation() {
        String propertyPath = "email";
        ProfileDto profileDto = ProfileDto.builder().email("email@cc.com").build();

        Set<ConstraintViolation<ProfileDto>> violations = validator.validate(profileDto);
        Optional<ConstraintViolation<ProfileDto>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertFalse(optional.isPresent());
    }
}