package fic.writer.domain.service.helper;

import fic.writer.domain.entity.enums.FileExtension;

public class BookFormatConverterFactory {
    public static BookConverter getBookConverter(FileExtension extension) {
        BookConverter converter;
        switch (extension) {
            case FB2: {
                converter = new BookXMLFileConstructor();
            }
            break;
            case TXT: {
                converter = new BookTXTFileConstructor();
            }
            break;
            default: {
                throw new EnumConstantNotPresentException(FileExtension.class, extension.toString());
            }
        }
        return converter;
    }
}
