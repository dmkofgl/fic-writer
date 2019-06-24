package fic.writer.domain.service.helper.formatter;

import fic.writer.domain.entity.enums.FormattingType;
import fic.writer.exception.UnsupportedFormatTypeException;

public class MarkdownFormatter implements Formatter {

    @Override
    public String getOpenElement(FormattingType formatting) {
        String result = "";
        switch (formatting) {
            case BOLD:
                result = "_";
                break;
            case ITALIC:
                result = "**";
                break;
            case UNDERLINE:
                result = "~~";
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
                result = "_";
                break;
            case ITALIC:
                result = "**";
                break;
            case UNDERLINE:
                result = "~~";
                break;
            default:
                throw new UnsupportedFormatTypeException();
        }
        return result;
    }
}