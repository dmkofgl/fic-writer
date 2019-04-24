package fic.writer.domain.repository;

import fic.writer.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByBookId(Long bookId);
}
