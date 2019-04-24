package fic.writer.domain.service;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.dto.BookDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Test
    public void createBook_shouldChangeCount() {
        final int SIZE_BEFORE = bookService.findAll().size();
        Book emptyBook = Book.builder().build();
        bookService.create(BookDto.of(emptyBook));

        assertNotEquals(SIZE_BEFORE, bookService.findAll().size());
    }

    @Test
    public void createBook_whenTitleIsCyrillic_shouldFindByGeneratedId() {
        final String TITLE = "Заголовок";
        final long CURRENT_COUNT = 1L;
        Book emptyBook = Book.builder().title(TITLE).build();
        bookService.create(BookDto.of(emptyBook));

        assertEquals(CURRENT_COUNT, bookService.findAll().stream().filter(book -> book.getTitle().equals(TITLE)).count());
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