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

public class ArticleValidationTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateArticle_whenTitleIsEmpty_shouldThrowException() {
        Article article = Article.builder().title("").content("content").build();

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateArticle_whenContentIsNull_shouldThrowException() {
        Article article = Article.builder().title("title").content(null).build();

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateArticle_whenContentIsEmpty_shouldThrowException() {
        Article article = Article.builder().title("title").content("").build();

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateArticle_whenTitleIsNull_shouldThrowException() {
        Article article = Article.builder().title(null).content("content").build();

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertFalse(violations.isEmpty());
    }


    @Test
    public void validateArticle_whenTitleAndContentAreNull_shouldReturnTwoViolations() {
        String propertyPathTitle = "title";
        String propertyPathContent = "content";
        Article article = Article.builder().title(null).content(null).build();

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        Optional<ConstraintViolation<Article>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPathTitle))
                .findFirst();
        assertTrue(optional.isPresent());
        optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPathContent))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateArticle_whenTitleAndContentAreEmpty_shouldReturnTwoViolations() {
        String propertyPathTitle = "title";
        String propertyPathContent = "content";
        Article article = Article.builder().title("").content("").build();

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        Optional<ConstraintViolation<Article>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPathTitle))
                .findFirst();
        assertTrue(optional.isPresent());
        optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPathContent))
                .findFirst();
        assertTrue(optional.isPresent());
    }

    @Test
    public void validateArticle_whenValid_shouldReturnEmptyViolations() {
        String propertyPath = "title";
        Article article = Article.builder().title("title").content("content").build();


        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        Optional<ConstraintViolation<Article>> optional = violations.stream()
                .filter(c -> c.getPropertyPath().toString().equalsIgnoreCase(propertyPath))
                .findFirst();
        assertFalse(optional.isPresent());
    }
}