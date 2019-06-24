package fic.writer.domain.service.helper;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Profile;

import java.util.Set;

public class BookTXTStringConstructor extends BookStringConstructor {
    protected BookStringBuilder bookStringBuilder = new BookStringBuilder();

    @Override
    protected void writeTitle(Book book) {
        String titleHeader = "Title";
        bookStringBuilder.addDescription(titleHeader, book.getTitle());
        content = bookStringBuilder.getContent();
    }

    @Override
    protected void writeDescription(Book book) {
        String descriptionHeader = "Description";
        bookStringBuilder.addDescription(descriptionHeader, book.getDescription());
        content = bookStringBuilder.getContent();
    }

    @Override
    protected void writeAuthor(Book book) {
        if (book.getAuthor() != null) {
            String authorHeader = "Author";
            bookStringBuilder.addDescription(authorHeader, book.getAuthor().getUsername());
            content = bookStringBuilder.getContent();
        }
    }

    @Override
    protected void writeCoauthors(Book book) {
        final String coauthorSeparator = ",";
        String coauthors = book.getCoauthors()
                .stream()
                .map(Profile::getUsername)
                .reduce((b, a) -> b + coauthorSeparator + a)
                .orElse("");
        String coauthorsHeader = "Coauthor";
        bookStringBuilder.addDescription(coauthorsHeader, coauthors);
        content = bookStringBuilder.getContent();
    }

    @Override
    protected void writeSize(Book book) {
        if (book.getSize() != null) {
            String sizeHeader = "Size";
            bookStringBuilder.addDescription(sizeHeader, book.getSize().name());
        }
        content = bookStringBuilder.getContent();
    }

    @Override
    protected void writeState(Book book) {
        if (book.getState() != null) {
            String stateHeader = "State";
            bookStringBuilder.addDescription(stateHeader, book.getState().name());
        }
        content = bookStringBuilder.getContent();
    }

    @Override
    protected void writeArticleHeaders(Book book) {
        String contentHeader = "Content";
        bookStringBuilder.addParagraph(contentHeader);
        int articleCounter = 0;
        Set<Article> articles = book.getArticles();
        for (Article article : articles) {
            articleCounter++;
            String contentRow = articleCounter + ")" + article.getTitle();
            bookStringBuilder.addParagraph(contentRow);
        }
        content = bookStringBuilder.getContent();
    }

    @Override
    protected void writeArticlesContent(Book book) {
        Set<Article> articles = book.getArticles();
        for (Article article : articles) {
            bookStringBuilder.addParagraph(article.getTitle());
            String annotationHeader = "Annotation";
            bookStringBuilder.addDescription(annotationHeader, article.getAnnotation());
            bookStringBuilder.addParagraph(article.getContent());
        }
        content = bookStringBuilder.getContent();
    }


}
