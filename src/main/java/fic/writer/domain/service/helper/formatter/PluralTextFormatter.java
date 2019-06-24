package fic.writer.domain.service.helper.formatter;

import fic.writer.domain.entity.enums.FormattingType;

public class PluralTextFormatter implements Formatter {
    @Override
    public String getOpenElement(FormattingType formatting) {
        return "";
    }

    @Override
    public String getCloseElement(FormattingType formatting) {
        return "";
    }
}