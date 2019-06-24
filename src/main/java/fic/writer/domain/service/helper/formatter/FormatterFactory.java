package fic.writer.domain.service.helper.formatter;

import fic.writer.exception.FormatterNotFoundException;

public class FormatterFactory {
    public static Formatter getFormatter(FormattingKind kind) {
        Formatter formatter;
        switch (kind) {
            case MARKDOWN: {
                formatter = new MarkdownFormatter();
            }
            break;
            case HTML: {
                formatter = new HTMLFormatter();
            }
            break;
            case PLURAL: {
                formatter = new PluralTextFormatter();
            }
            break;
            case XML: {
                formatter = new XMLFormatter();
            }
            break;
            default:
                throw new FormatterNotFoundException();
        }
        return formatter;
    }
}
