package fic.writer.domain.service;

import fic.writer.domain.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleService {
    List<Article> findAll();

    Page<Article> findPage(Pageable pageable);

    Optional<Article> findById(UUID uuid);
}
