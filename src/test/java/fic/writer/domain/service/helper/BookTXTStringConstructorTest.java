package fic.writer.domain.service.helper;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.enums.Size;
import org.assertj.core.util.Lists;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BookTXTStringConstructorTest {

    @Test
    public void convertBookToTXT() {
        BookFileConstructor bookFileConstructor = new BookTXTFileConstructor();
        String description = "it's a book",
                title = "Header";

        Book book = Book.builder()
                .id(1L)
                .description(description)
                .size(Size.MINI)
                .title(title)
                .articles(Lists.list(
                        Article.builder().content("art content1").title("art1").build(),
                        Article.builder().content("art content2").title("art2").build()))
                .build();
        String convertedBook = new String(bookFileConstructor.convertToByteArray(book));

        assertTrue(convertedBook.contains(description));
        assertTrue(convertedBook.contains(title));
        assertTrue(convertedBook.contains("art content1"));
        assertTrue(convertedBook.contains("art content2"));
        assertTrue(convertedBook.contains("art1"));
        assertTrue(convertedBook.contains("art2"));
    }
}