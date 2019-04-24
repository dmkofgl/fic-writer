package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.service.FileService;
import fic.writer.domain.service.files.ArticleParser;
import fic.writer.domain.service.files.BookParser;
import fic.writer.domain.service.files.TextParser;
import fic.writer.domain.service.files.impl.*;
import fic.writer.domain.utils.FileExtention;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String parseText(MultipartFile file) {
        FileExtention extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        TextParser parser = selectTextParser(extension);
        return parser.parseFile(file);
    }

    public Article parseArticle(MultipartFile file) {
        FileExtention extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        ArticleParser parser = selectArticleParser(extension, file);
        return Article.builder()
                .title(parser.getTitle())
                .annotation(parser.getAnnotation())
                .content(parser.getContent())
                .build();
    }

    public Book parseBook(MultipartFile file) {
        FileExtention extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        BookParser parser = selectBookParser(extension, file);
        return Book.builder()
                .title(parser.getTitle())
                .description(parser.getDescription())
                .articles(parser.getArticles())
                .build();
    }

    private BookParser selectBookParser(FileExtention fileExtention, MultipartFile file) {
        BookParser parser;
        switch (fileExtention) {
            case FB2:
                parser = new XmlBookParser(file);
                break;
            default:
                throw new EnumConstantNotPresentException(FileExtention.class, fileExtention.toString());
        }
        return parser;
    }

    private ArticleParser selectArticleParser(FileExtention fileExtention, MultipartFile file) {
        ArticleParser parser;
        switch (fileExtention) {
            case XML:
                parser = new XmlArticleParser(file);
                break;
            case JSON:
                parser = new JsonArticleParser(file);
                break;
            case FB2:
                parser = new XmlArticleParser(file);
            default:
                throw new EnumConstantNotPresentException(FileExtention.class, fileExtention.toString());
        }
        return parser;
    }

    private TextParser selectTextParser(FileExtention fileExtention) {
        TextParser parser;
        switch (fileExtention) {
            case TXT:
                parser = new TxtTextParser();
                break;
            case DOCX:
                parser = new DocxTextParser();
                break;
            default:
                throw new EnumConstantNotPresentException(FileExtention.class, fileExtention.toString());
        }
        return parser;
    }

    private FileExtention getExtension(String fileName) {
        int pointIndex = fileName.lastIndexOf('.');
        int pointPosition = pointIndex + 1;
        return pointIndex == -1
                ? FileExtention.TXT
                : FileExtention.valueOf(fileName.substring(pointPosition).toUpperCase());
    }

}
