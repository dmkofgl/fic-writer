package fic.writer.domain.service.helper;

import fic.writer.domain.entity.Book;

public abstract class BookFileConstructor implements BookConverter {
    protected final void buildBook(Book book) {
        writeTitle(book);
        writeDescription(book);
        writeAuthor(book);
        writeCoauthors(book);
        writeSize(book);
        writeState(book);
        writeArticleHeaders(book);
        writeArticlesContent(book);
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
