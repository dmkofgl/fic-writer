package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.dto.ArticleDto;
import fic.writer.domain.repository.ArticleRepository;
import fic.writer.domain.service.ArticleService;
import fic.writer.domain.service.BookService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;

    private BookService bookService;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public List<Article> findAllForBook(Long bookId) {
        return articleRepository.findAllByBookId(bookId);
    }

    @Override
    public Page<Article> findPage(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Article update(Long id, ArticleDto articleDto) {
        Article article = articleRepository.findById(id).orElseThrow(EntityExistsException::new);
        flushArticleDtoToArticle(article, articleDto);
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public String parseArticleContentFromFile(MultipartFile multipartFile) {
        String fileContent = "";
        try {
            ByteArrayOutputStream stringWriter = new ByteArrayOutputStream();
            IOUtils.copy(multipartFile.getInputStream(), stringWriter);
            fileContent = stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return fileContent;
    }


    private void flushArticleDtoToArticle(Article article, ArticleDto articleDto) {
        if (articleDto.getTitle() != null) {
            article.setTitle(articleDto.getTitle());
        }
        if (articleDto.getAnnotation() != null) {
            article.setAnnotation(articleDto.getAnnotation());
        }
        if (articleDto.getContent() != null) {
            article.setContent(articleDto.getContent());
        }
    }
}
