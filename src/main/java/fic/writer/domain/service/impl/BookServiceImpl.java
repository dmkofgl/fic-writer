package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.dto.ArticleDto;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.domain.repository.BookRepository;
import fic.writer.domain.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
        flushBookDtoToBook(book, bookDto);
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, BookDto bookDto) {
        Book book = bookRepository.getOne(id);
        flushBookDtoToBook(book, bookDto);
        return bookRepository.save(book);
    }

    @Override
    public Book addArticle(Long bookId, ArticleDto articleDto) {
        Book book = bookRepository.getOne(bookId);
        Article article = Article.builder().build();
        flushArticleDtoToArticle(article, articleDto);
        book.getArticles().add(article);
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

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    private void flushBookDtoToBook(Book book, BookDto bookDto) {
        if (bookDto.getTitle() != null) {
            book.setTitle(bookDto.getTitle());
        }
        if (bookDto.getDescription() != null) {
            book.setDescription(bookDto.getDescription());
        }
        if (bookDto.getSize() != null) {
            bookDto.setSize(bookDto.getSize());
        }
        if (bookDto.getState() != null) {
            bookDto.setState(bookDto.getState());
        }
    }
}
