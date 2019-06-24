package fic.writer.domain.service.helper.formatter;

import fic.writer.domain.entity.enums.FormattingType;
import fic.writer.exception.UnsupportedFormatTypeException;

public class HTMLFormatter implements Formatter {
    @Override
    public String getOpenElement(FormattingType formatting) {
        String result = "";
        switch (formatting) {
            case BOLD:
                result = "<b>";
                break;
            case ITALIC:
                result = "<i>";
                break;
            case UNDERLINE:
                result = "<u>";
                break;
            default:
                throw new UnsupportedFormatTypeException();
        }
        return result;
    }

    @Override
    public String getCloseElement(FormattingType formatting) {
        String result = "";
        switch (formatting) {
            case BOLD:
                result = "</b>";
                break;
            case ITALIC:
                result = "<i>";
                break;
            case UNDERLINE:
                result = "</u>";
                break;
            default:
                throw new UnsupportedFormatTypeException();
        }
        return result;
    }
}