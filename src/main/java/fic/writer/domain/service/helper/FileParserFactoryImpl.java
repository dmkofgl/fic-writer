package fic.writer.domain.service.helper;

import fic.writer.domain.entity.enums.FileExtension;
import fic.writer.domain.service.files.ArticleParser;
import fic.writer.domain.service.files.BookParser;
import fic.writer.domain.service.files.TextParser;
import fic.writer.domain.service.files.impl.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class FileParserFactoryImpl implements FileParserFactory {
    @Override
    public TextParser getTextParser(MultipartFile file) {
        FileExtension fileExtension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        TextParser parser;
        switch (fileExtension) {
            case TXT:
                parser = new TxtTextParser();
                break;
            case DOCX:
                parser = new DocxTextParser();
                break;
            default:
                throw new EnumConstantNotPresentException(FileExtension.class, fileExtension.toString());
        }
        return parser;
    }

    @Override
    public ArticleParser getArticleParser(MultipartFile file) {
        FileExtension fileExtension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        ArticleParser parser;
        switch (fileExtension) {
            case XML:
                parser = new XmlArticleParser(file);
                break;
            case JSON:
                parser = new JsonArticleParser(file);
                break;
            case FB2:
                parser = new XmlArticleParser(file);
            default:
                throw new EnumConstantNotPresentException(FileExtension.class, fileExtension.toString());
        }
        return parser;
    }

    @Override
    public BookParser getBookParser(MultipartFile file) {
        FileExtension fileExtension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
        BookParser parser;
        switch (fileExtension) {
            case FB2:
                parser = new XmlBookParser(file);
                break;
            default:
                throw new EnumConstantNotPresentException(FileExtension.class, fileExtension.toString());
        }
        return parser;
    }

    private FileExtension getExtension(String fileName) {
        int pointIndex = fileName.lastIndexOf('.');
        int pointPosition = pointIndex + 1;
        return pointIndex == -1
                ? FileExtension.TXT
                : FileExtension.valueOf(fileName.substring(pointPosition).toUpperCase());
    }
}
