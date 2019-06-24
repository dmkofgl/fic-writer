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

public class ActorDtoValidationTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateActorDto_whenNameIsEmpty_shouldThrowException() {
        ActorDto actorDto = ActorDto.builder().name("").build();

        Set<ConstraintViolation<ActorDto>> violations = validator.validate(actorDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateActorDto_whenNameIsNull_shouldThrowException() {
        ActorDto actorDto = ActorDto.builder().name(null).build();

        Set<ConstraintViolation<ActorDto>> violations = validator.validate(actorDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateActorDto_whenNameIsValid_shouldReturnEmptyViolations() {
        ActorDto actorDto = ActorDto.builder().name("name").build();

        Set<ConstraintViolation<ActorDto>> violations = validator.validate(actorDto);
        assertTrue(violations.isEmpty());
    }
}