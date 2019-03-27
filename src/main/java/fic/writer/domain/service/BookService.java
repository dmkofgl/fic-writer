package fic.writer.domain.service;

import fic.writer.domain.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {
    List<Book> findAll();

    Page<Book> findPage(Pageable pageable);

    Optional<Book> findById(UUID uuid);
}
