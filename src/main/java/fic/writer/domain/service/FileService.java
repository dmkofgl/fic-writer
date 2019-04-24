package fic.writer.domain.service;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String parseText(MultipartFile file);

    Article parseArticle(MultipartFile file);

    Book parseBook(MultipartFile file);
}
