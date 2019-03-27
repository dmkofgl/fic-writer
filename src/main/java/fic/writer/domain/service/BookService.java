package fic.writer.domain.service;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.dto.BookSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BookService extends CrudService<Book, UUID> {
    Page<Book> findPage(BookSearchDto dto, Pageable pageable);
}
