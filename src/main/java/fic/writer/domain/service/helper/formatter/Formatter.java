package fic.writer.domain.service.helper.formatter;

import fic.writer.domain.entity.enums.FormattingType;

public interface Formatter {
    String getOpenElement(FormattingType formatting);

    String getCloseElement(FormattingType formatting);
}
