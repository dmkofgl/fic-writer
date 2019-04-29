package fic.writer.domain.service;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.dto.BookDto;

public interface WriterService {
    Book createBook(BookDto bookDto);

    void deleteBook(Long bookId);
}
