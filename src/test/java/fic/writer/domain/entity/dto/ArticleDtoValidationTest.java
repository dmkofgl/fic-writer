package fic.writer.domain.entity.dto;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.*;

public class ArticleDtoValidationTest {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateArticleDto_whenTitleIsEmpty_shouldThrowException() {
        ArticleDto articleDto = ArticleDto.builder().title("").content("content").build();

        Set<ConstraintViolation<ArticleDto>> violations = validator.validate(articleDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateArticleDto_whenContentIsNull_shouldThrowException() {
        ArticleDto articleDto = ArticleDto.builder().title("title").content(null).build();

        Set<ConstraintViolation<ArticleDto>> violations = validator.validate(articleDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateArticleDto_whenContentIsEmpty_shouldThrowException() {
        ArticleDto articleDto = ArticleDto.builder().title("title").content("").build();

        Set<ConstraintViolation<ArticleDto>> violations = validator.validate(articleDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateArticleDto_whenTitleIsNull_shouldThrowException() {
        ArticleDto articleDto = ArticleDto.builder().title(null).content("content").build();

        Set<ConstraintViolation<ArticleDto>> violations = validator.validate(articleDto);
        assertFalse(violations.isEmpty());
    }


    @Test
    public void validateArticleDto_whenTitleAndContentAreNull_shouldReturnTwoViolations() {
        int expectedSize = 2;
        ArticleDto articleDto = ArticleDto.builder().title(null).content(null).build();

        Set<ConstraintViolation<ArticleDto>> violations = validator.validate(articleDto);
        assertEquals(expectedSize, violations.size());
    }

    @Test
    public void validateArticleDto_whenTitleAndContentAreEmpty_shouldReturnTwoViolations() {
        int expectedSize = 2;
        ArticleDto articleDto = ArticleDto.builder().title("").content("").build();

        Set<ConstraintViolation<ArticleDto>> violations = validator.validate(articleDto);
        assertEquals(expectedSize, violations.size());
    }

    @Test
    public void validateArticleDto_whenValid_shouldReturnEmptyViolations() {
        ArticleDto articleDto = ArticleDto.builder().title("title").content("content").build();

        Set<ConstraintViolation<ArticleDto>> violations = validator.validate(articleDto);
        assertTrue(violations.isEmpty());
    }
}