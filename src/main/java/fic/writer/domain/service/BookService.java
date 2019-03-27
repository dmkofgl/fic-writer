package fic.writer.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BookService {
    List<BookService> findAll();

    Page<BookService> findPage(Pageable pageable);

    BookService findById(UUID uuid);
}
