package fic.writer.domain.service.helper;

import fic.writer.domain.service.files.ArticleParser;
import fic.writer.domain.service.files.BookParser;
import fic.writer.domain.service.files.TextParser;
import fic.writer.domain.service.files.impl.*;
import fic.writer.domain.utils.FileExtention;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class FileParserFactoryImpl implements FileParserFactory {
    @Override
    public TextParser getTextParser(MultipartFile file) {
        FileExtention fileExtention = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
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

    @Override
    public ArticleParser getArticleParser(MultipartFile file) {
        FileExtention fileExtention = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
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

    @Override
    public BookParser getBookParser(MultipartFile file) {
        FileExtention fileExtention = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
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

    private FileExtention getExtension(String fileName) {
        int pointIndex = fileName.lastIndexOf('.');
        int pointPosition = pointIndex + 1;
        return pointIndex == -1
                ? FileExtention.TXT
                : FileExtention.valueOf(fileName.substring(pointPosition).toUpperCase());
    }
}
