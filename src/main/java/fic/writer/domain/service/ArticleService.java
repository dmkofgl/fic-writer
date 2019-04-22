package fic.writer.domain.service;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.dto.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> findAll();

    List<Article> findAllForBook(Long bookId);

    Page<Article> findPage(Pageable pageable);

    Article update(Long id, ArticleDto articleDto);

    Optional<Article> findById(Long aLong);

    void delete(Article article);

    void deleteById(Long aLong);

    String parseArticleContentFromFile(MultipartFile multipartFile);
}
