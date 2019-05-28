package fic.writer.domain.service.helper;

import fic.writer.domain.service.files.ArticleParser;
import fic.writer.domain.service.files.BookParser;
import fic.writer.domain.service.files.TextParser;
import org.springframework.web.multipart.MultipartFile;

public interface FileParserFactory {
    TextParser getTextParser(MultipartFile file);

    ArticleParser getArticleParser(MultipartFile file);

    BookParser getBookParser(MultipartFile file);
}
