package fic.writer.web.controller.response;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Formatting;
import fic.writer.domain.entity.enums.FormattingType;
import fic.writer.domain.service.helper.formatter.FormattingKind;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ArticleResponseFormattingTest {
    @Test
    public void createArticleResponseWithoutFormatting_whenFormattingIsMarkdown_shouldNotChangeContent() {
        String content = "article";
        Book book = Book.builder().id(1L).build();
        Article article = Article.builder().id(1L).content(content).book(book).build();
        FormattingKind formattingKind = FormattingKind.MARKDOWN;
        ArticleResponse articleResponse = new ArticleResponse(article, formattingKind);
        assertEquals(content, articleResponse.getContent());
    }

    @Test
    public void createArticleResponse_whenFormattingIsMarkdown_shouldChangeContent() {
        String content = "article";
        String newContext = "ar_tic_le";
        Book book = Book.builder().id(1L).build();
        Formatting formatting = Formatting.builder()
                .startIndex(2L)
                .endIndex(5L)
                .type(FormattingType.BOLD)
                .build();
        Article article = Article.builder().id(1L)
                .content(content)
                .book(book)
                .formatters(formatting)
                .build();
        FormattingKind formattingKind = FormattingKind.MARKDOWN;
        ArticleResponse articleResponse = new ArticleResponse(article, formattingKind);
        assertEquals(newContext, articleResponse.getContent());
    }

    @Test
    public void createArticleResponse_whenSomeFormattingIsMarkdown_shouldChangeContent() {
        String content = "article";
        String newContext = "ar_**tic_**le";
        Book book = Book.builder().id(1L).build();
        Formatting formattingBold = Formatting.builder()
                .startIndex(2L)
                .endIndex(5L)
                .type(FormattingType.BOLD)
                .build();
        Formatting formattingItalic = Formatting.builder()
                .startIndex(2L)
                .endIndex(5L)
                .type(FormattingType.ITALIC)
                .build();
        List<Formatting> formattings = Lists.list(formattingBold, formattingItalic);
        Article article = Article.builder().id(1L)
                .content(content)
                .book(book)
                .formattings(formattings)
                .build();
        FormattingKind formattingKind = FormattingKind.MARKDOWN;
        ArticleResponse articleResponse = new ArticleResponse(article, formattingKind);
        assertEquals(newContext, articleResponse.getContent());
    }

    @Test
    public void createArticleResponse_whenFormattingIsMarkdownAndIndexNotIntercept_shouldChangeContent() {
        String content = "article";
        String newContext = "ar**ti**c_l_e";
        Book book = Book.builder().id(1L).build();
        Formatting formattingBold = Formatting.builder()
                .startIndex(5L)
                .endIndex(6L)
                .type(FormattingType.BOLD)
                .build();
        Formatting formattingItalic = Formatting.builder()
                .startIndex(2L)
                .endIndex(4L)
                .type(FormattingType.ITALIC)
                .build();
        List<Formatting> formattings = Lists.list(formattingBold, formattingItalic);
        Article article = Article.builder().id(1L)
                .content(content)
                .book(book)
                .formattings(formattings)
                .build();
        FormattingKind formattingKind = FormattingKind.MARKDOWN;
        ArticleResponse articleResponse = new ArticleResponse(article, formattingKind);
        assertEquals(newContext, articleResponse.getContent());
    }

    @Test
    public void createArticleResponseWithoutFormatting_whenFormattingIsHTML_shouldNotChangeContent() {
        String content = "article";
        Book book = Book.builder().id(1L).build();
        Article article = Article.builder().id(1L).content(content).book(book).build();
        FormattingKind formattingKind = FormattingKind.HTML;
        ArticleResponse articleResponse = new ArticleResponse(article, formattingKind);
        assertEquals(content, articleResponse.getContent());
    }

    @Test
    public void createArticleResponseWithoutFormatting_whenFormattingIsPlural_shouldNotChangeContent() {
        String content = "article";
        Book book = Book.builder().id(1L).build();
        Article article = Article.builder().id(1L).content(content).book(book).build();
        FormattingKind formattingKind = FormattingKind.PLURAL;
        ArticleResponse articleResponse = new ArticleResponse(article, formattingKind);
        assertEquals(content, articleResponse.getContent());
    }

    @Test
    public void createArticleResponse_whenFormattingIsPluralAndIndexNotIntercept_shouldNotChangeContent() {
        String content = "article";
        Book book = Book.builder().id(1L).build();
        Formatting formattingBold = Formatting.builder()
                .startIndex(5L)
                .endIndex(6L)
                .type(FormattingType.BOLD)
                .build();
        Formatting formattingItalic = Formatting.builder()
                .startIndex(2L)
                .endIndex(4L)
                .type(FormattingType.ITALIC)
                .build();
        List<Formatting> formattings = Lists.list(formattingBold, formattingItalic);
        Article article = Article.builder().id(1L)
                .content(content)
                .book(book)
                .formattings(formattings)
                .build();
        FormattingKind formattingKind = FormattingKind.PLURAL;
        ArticleResponse articleResponse = new ArticleResponse(article, formattingKind);
        assertEquals(content, articleResponse.getContent());
    }

    @Test
    public void createArticleResponseWithoutFormatting_whenFormattingIsXML_shouldNotChangeContent() {
        String content = "article";
        Book book = Book.builder().id(1L).build();
        Article article = Article.builder().id(1L).content(content).book(book).build();
        FormattingKind formattingKind = FormattingKind.XML;
        ArticleResponse articleResponse = new ArticleResponse(article, formattingKind);
        assertEquals(content, articleResponse.getContent());
    }

}