package fic.writer.domain.service.helper;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Profile;

import java.util.Set;

public class BookStringConstructor {
    private static String titleHeader = "Title";
    private static String descriptionHeader = "Description";
    private static String authorHeader = "Author";
    private static String coauthorsHeader = "Coauthor";
    private static String sizeHeader = "Size";
    private static String stateHeader = "State";
    private static String contentHeader = "Content";
    private static String annotationHeader = "Annotation";
    private static String coauthorSeparator = ",";
    private BookStringBuilder bookStringBuilder = new BookStringBuilder();

    public String convertBookToSimpleText(Book book) {
        writeTitle(book);
        writeDescription(book);
        writeAuthor(book);
        writeCoauthors(book);
        writeSize(book);
        writeState(book);
        writeArticleHeaders(book);
        writeArticlesContent(book);
        return bookStringBuilder.getContent();
    }

    private void writeTitle(Book book) {
        bookStringBuilder.addDescription(titleHeader, book.getTitle());
    }

    private void writeDescription(Book book) {
        bookStringBuilder.addDescription(descriptionHeader, book.getDescription());
    }

    private void writeAuthor(Book book) {
        bookStringBuilder.addDescription(authorHeader, book.getAuthor().getUsername());
    }

    private void writeCoauthors(Book book) {
        String coauthors = book.getCoauthors()
                .stream()
                .map(Profile::getUsername)
                .reduce((b, a) -> b + coauthorSeparator + a)
                .orElse("");
        bookStringBuilder.addDescription(coauthorsHeader, coauthors);
    }

    private void writeSize(Book book) {
        if (book.getSize() != null) {
            bookStringBuilder.addDescription(sizeHeader, book.getSize().name());
        }
    }

    private void writeState(Book book) {
        if (book.getSize() != null) {
            bookStringBuilder.addDescription(stateHeader, book.getState().name());
        }
    }

    private void writeArticleHeaders(Book book) {
        bookStringBuilder.addParagraph(contentHeader);
        int articleCounter = 0;
        Set<Article> articles = book.getArticles();
        for (Article article : articles) {
            articleCounter++;
            String contentRow = articleCounter + ")" + article.getTitle();
            bookStringBuilder.addParagraph(contentRow);
        }
    }

    private void writeArticlesContent(Book book) {
        Set<Article> articles = book.getArticles();
        for (Article article : articles) {
            bookStringBuilder.addParagraph(article.getTitle());
            bookStringBuilder.addDescription(annotationHeader, article.getAnnotation());
            bookStringBuilder.addParagraph(article.getContent());
        }
    }

    private class BookStringBuilder {
        private final static String DEFAULT_SPLITTER = ":";
        private String content = "";

        public BookStringBuilder addParagraph(String content) {
            this.content += "\n" + content;
            return this;
        }

        public BookStringBuilder addDescription(String header, String value) {
            return this.addDescription(header, value, DEFAULT_SPLITTER);
        }

        public BookStringBuilder addDescription(String header, String value, String splitter) {
            this.content += "\n" + header + splitter + value;
            return this;
        }

        public String getContent() {
            return content;
        }
    }
}
