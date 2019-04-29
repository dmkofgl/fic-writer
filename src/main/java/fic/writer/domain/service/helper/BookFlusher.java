package fic.writer.domain.service.helper;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.dto.BookDto;

public class BookFlusher {

    public static void flushBookDtoToBook(Book book, BookDto bookDto) {
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
