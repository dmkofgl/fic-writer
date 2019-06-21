package fic.writer.domain.service;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.dto.ArticleDto;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> findAll();

    Article update(Long id, ArticleDto articleDto);

    Optional<Article> findById(Long articleId);

    void delete(Article article);

    void deleteById(Long articleId);
}
