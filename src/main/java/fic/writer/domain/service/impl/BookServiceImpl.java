package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Book;
import fic.writer.domain.repository.BookRepository;
import fic.writer.domain.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<Book> findById(UUID uuid) {
        return bookRepository.findById(uuid);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public void deleteById(UUID uuid) {
        bookRepository.deleteById(uuid);
    }
}
