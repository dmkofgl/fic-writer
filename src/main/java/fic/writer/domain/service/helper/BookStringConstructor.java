package fic.writer.domain.service.helper;

import fic.writer.domain.entity.Book;

public abstract class BookStringConstructor {
    protected String content;

    public final String convertBookToText(Book book) {
        writeTitle(book);
        writeDescription(book);
        writeAuthor(book);
        writeCoauthors(book);
        writeSize(book);
        writeState(book);
        writeArticleHeaders(book);
        writeArticlesContent(book);
        return content;
    }

    protected abstract void writeTitle(Book book);

    protected abstract void writeDescription(Book book);

    protected abstract void writeAuthor(Book book);

    protected abstract void writeCoauthors(Book book);

    protected abstract void writeSize(Book book);

    protected abstract void writeState(Book book);

    protected abstract void writeArticleHeaders(Book book);

    protected abstract void writeArticlesContent(Book book);
}
