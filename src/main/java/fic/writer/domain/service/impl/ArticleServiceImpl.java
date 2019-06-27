package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.dto.ArticleDto;
import fic.writer.domain.repository.ArticleRepository;
import fic.writer.domain.service.ArticleService;
import fic.writer.domain.service.helper.flusher.ArticleFlusher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article update(Long id, ArticleDto articleDto) {
        Article article = articleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ArticleFlusher.flushArticleDtoToArticle(article, articleDto);
        return articleRepository.save(article);
    }

    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

}
