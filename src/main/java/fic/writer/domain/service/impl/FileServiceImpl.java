package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.service.FileService;
import fic.writer.domain.service.files.ArticleParser;
import fic.writer.domain.service.files.BookParser;
import fic.writer.domain.service.files.TextParser;
import fic.writer.domain.service.helper.FileParserFactory;
import fic.writer.domain.service.helper.FileParserFactoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
    FileParserFactory fileParserFactory = new FileParserFactoryImpl();

    @Override
    public String parseText(MultipartFile file) {
        TextParser parser = fileParserFactory.getTextParser(file);
        return parser.parseFile(file);
    }

    public Article parseArticle(MultipartFile file) {
        ArticleParser parser = fileParserFactory.getArticleParser(file);
        return parser.parse();
    }

    public Book parseBook(MultipartFile file) {
        BookParser parser = fileParserFactory.getBookParser(file);
        return parser.parse();
    }
}
