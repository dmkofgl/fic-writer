package fic.writer.domain.entity;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.*;

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
        int expectedSize = 2;
        Article article = Article.builder().title(null).content(null).build();

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertEquals(expectedSize, violations.size());
    }

    @Test
    public void validateArticle_whenTitleAndContentAreEmpty_shouldReturnTwoViolations() {
        int expectedSize = 2;
        Article article = Article.builder().title("").content("").build();

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertEquals(expectedSize, violations.size());
    }

    @Test
    public void validateArticle_whenValid_shouldReturnEmptyViolations() {
        Article article = Article.builder().title("title").content("content").build();

        Set<ConstraintViolation<Article>> violations = validator.validate(article);
        assertTrue(violations.isEmpty());
    }
}