package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.dto.ArticleDto;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.domain.repository.BookRepository;
import fic.writer.domain.service.BookService;
import fic.writer.domain.service.helper.ArticleFlusher;
import fic.writer.domain.service.helper.BookFlusher;
import fic.writer.domain.service.helper.BookStringConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityListeners;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@EntityListeners(AuditingEntityListener.class)
@Transactional
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> findPage(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book create(BookDto bookDto) {
        Book book = Book.builder().build();
        BookFlusher.flushBookDtoToBook(book, bookDto);
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, BookDto bookDto) {
        Book book = bookRepository.getOne(id);
        BookFlusher.flushBookDtoToBook(book, bookDto);
        return bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }


    @Override
    public Book addArticle(Long bookId, ArticleDto articleDto) {
        Book book = bookRepository.getOne(bookId);
        Article article = Article.builder().build();
        ArticleFlusher.flushArticleDtoToArticle(article, articleDto);
        article.setBook(book);
        Set<Article> articles = new HashSet<>(book.getArticles());
        articles.add(article);
        book.setArticles(articles);
        bookRepository.save(book);
        return book;
    }

    @Override
    public Book removeArticle(Long bookId, Long articleId) {
        Book book = bookRepository.getOne(bookId);
        book.getArticles().removeIf(article -> article.getId().equals(articleId));
        bookRepository.save(book);
        return book;
    }

    @Override
    public byte[] getBookAsByteArray(Long bookId) throws IOException {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        BookStringConstructor bookStringConstructor = new BookStringConstructor();
        String bookAsString = bookStringConstructor.convertBookToSimpleText(book);
        return bookAsString.getBytes();
    }


}
