package fic.writer.web.controller;

import fic.writer.domain.entity.dto.ArticleDto;
import fic.writer.domain.service.ArticleService;
import fic.writer.domain.service.BookService;
import fic.writer.web.response.ArticleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/books/{bookId}/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {
    private static final String ID_TEMPLATE_PATH = "/{articleId}";
    private static final String ID_TEMPLATE = "articleId";
    private static final String BOOK_ID_TEMPLATE_PATH = "/{bookId}";
    private static final String BOOK_ID_TEMPLATE = "bookId";
    @Autowired
    private ArticleService articleService;
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<ArticleResponse> getAllArticles(@PathVariable(BOOK_ID_TEMPLATE) Long bookId) {
        List<ArticleResponse> list = articleService.findAllForBook(bookId).stream().map(ArticleResponse::new).collect(Collectors.toList());
        return list;
    }

    @GetMapping(ID_TEMPLATE_PATH)
    public ArticleResponse getOneArticle(@PathVariable(BOOK_ID_TEMPLATE) Long bookId, @PathVariable(ID_TEMPLATE) Long articleId) {
        return articleService.findAllForBook(bookId).stream().
                filter(article -> article.getId().equals(articleId))
                .map(ArticleResponse::new)
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createArticle(@PathVariable(BOOK_ID_TEMPLATE) Long bookId, @RequestBody ArticleDto articleDto) {
        bookService.addArticle(bookId, articleDto);
    }

    @DeleteMapping(ID_TEMPLATE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable(BOOK_ID_TEMPLATE) Long bookId, @PathVariable(ID_TEMPLATE) Long articleId) {
        bookService.removeArticle(bookId, articleId);
    }

    @PutMapping(ID_TEMPLATE_PATH)
    @ResponseStatus(HttpStatus.OK)
    public void updateArticle(@PathVariable(ID_TEMPLATE) Long articleId, @RequestBody ArticleDto articleDto) {
        articleService.update(articleId, articleDto);
    }


}