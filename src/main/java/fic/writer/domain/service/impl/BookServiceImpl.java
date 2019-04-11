package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.User;
import fic.writer.domain.entity.dto.ArticleDto;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.domain.repository.BookRepository;
import fic.writer.domain.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityListeners;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
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
        article.setBook(Book.builder().id(bookId).build());
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

    @Override
    public byte[] getBookAsByteArray(Long bookId) throws IOException {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        String output = "";
        output += "Title:" + book.getTitle();
        output += " \nDescription:" + book.getDescription();
        if (book.getAuthor() != null) {
            output += " \nAuthor:" + book.getAuthor().getUsername();
        }
        String coauthors = book.getSubAuthors().stream().map(User::getUsername).reduce((b, a) -> b + "," + a).orElse("");
        output += " \nCoauthor:" + coauthors;
        if (book.getSize() != null) {
            output += " \nSize:" + book.getSize().name();
        }
        if (book.getSize() != null) {
            output += " \nState:" + book.getState().name();
        }
        int counter = 0;
        String content = " \nContent: \n";
        Set<Article> articles = book.getArticles();
        for (Article article : articles) {
            content += " \n" + ++counter + ". " + article.getTitle();
        }
        output += content;

        for (Article article : articles) {
            output += " \n" + article.getTitle();
            output += " \nAnnotation:" + article.getAnnotation();
            output += " \n" + article.getContent();
        }
        return output.getBytes();
    }

    private void flushBookDtoToBook(Book book, BookDto bookDto) {
        if (bookDto.getTitle() != null) {
            book.setTitle(bookDto.getTitle());
        }
        if (bookDto.getDescription() != null) {
            book.setDescription(bookDto.getDescription());
        }
        if (bookDto.getSize() != null) {
            book.setSize(bookDto.getSize());
        }
        if (bookDto.getState() != null) {
            book.setState(bookDto.getState());
        }
    }
}
