package fic.writer.domain.service;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.service.impl.FileServiceImpl;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileServiceTest {
    private FileService fileService = new FileServiceImpl();

    @Test
    public void parseJSONArticle_whenJSONIsValid_shouldReturnArticle() throws IOException {
        File inputStream = new File(getClass().getClassLoader().getResource("files/ValidArticle.json").getFile());
        MultipartFile multipartFile = new MockMultipartFile("ValidArticle", "ValidArticle.json", "multipart/form-data", new FileInputStream(inputStream));
        Article article = fileService.parseArticle(multipartFile);
        assertEquals("it's content", article.getContent());
        assertEquals("descr", article.getAnnotation());
        assertEquals("article", article.getTitle());

    }

    @Test
    public void parseFB2Book_whenDataIsValid_shouldReturnBook() throws IOException {
        File inputStream = new File(getClass().getClassLoader().getResource("files/t.fb2").getFile());
        MultipartFile multipartFile = new MockMultipartFile("t", "t.fb2", "multipart/form-data", new FileInputStream(inputStream));
        Book book = fileService.parseBook(multipartFile);
    }

}