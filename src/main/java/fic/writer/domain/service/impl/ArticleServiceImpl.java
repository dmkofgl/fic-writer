package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Article;
import fic.writer.domain.repository.ArticleRepository;
import fic.writer.domain.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Page<Article> findPage(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Optional<Article> findById(UUID uuid) {
        return articleRepository.findById(uuid);
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public void deleteById(UUID uuid) {
        articleRepository.deleteById(uuid);
    }
}
