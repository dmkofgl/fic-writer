package fic.writer.domain.service.helper;

import fic.writer.domain.entity.Book;

public interface BookConverter {
    byte[] convertToByteArray(Book book);
}
