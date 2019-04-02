package fic.writer.domain.service;

import fic.writer.domain.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    public void createBook_shouldFindByGeneratedId() {
        Book emptyBook = Book.builder().build();
        bookService.save(emptyBook);
        assertNotNull(emptyBook.getId());
        assertTrue(bookService.findById(emptyBook.getId()).isPresent());
    }

    @Test
    public void findBook_whenContainSizeEnum_shouldConvertToNotNullSize() {
        final Long BOOK_ID = 3L;
        Book book = bookService.findById(BOOK_ID).get();

        assertNotNull(book.getSize());
    }

    @Test
    public void findBook_whenContainStateEnum_shouldConvertToNotNullState() {
        final Long BOOK_ID = 4L;
        Book book = bookService.findById(BOOK_ID).get();

        assertNotNull(book.getState());
    }

    @Test
    public void deletedBook_whenExist_shouldNotFound() {
        final Long DELETE_BOOK_ID = 333L;
        bookService.deleteById(DELETE_BOOK_ID);

        assertNotNull(bookService.findById(DELETE_BOOK_ID));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deletedBook_whenNotExist_shouldThorwException() {
        final Long DELETE_BOOK_ID = -1L;
        bookService.deleteById(DELETE_BOOK_ID);
    }

}