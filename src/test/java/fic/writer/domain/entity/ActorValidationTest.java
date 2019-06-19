package fic.writer.domain.entity;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ActorValidationTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void ValidateActor_whenNameIsNull_shouldThrowException() {
        Actor actorDto = Actor.builder().name(null).build();

        Set<ConstraintViolation<Actor>> violations = validator.validate(actorDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateActor_whenNameIsEmpty_shouldThrowException() {
        Actor actor = Actor.builder().name("").build();

        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateActor_whenNameIsValid_shouldReturnEmptyViolations() {
        Actor actor = Actor.builder().name("name").build();

        Set<ConstraintViolation<Actor>> violations = validator.validate(actor);
        assertTrue(violations.isEmpty());
    }
}