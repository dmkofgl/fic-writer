package fic.writer.domain.service.helper.formatter;

import fic.writer.domain.entity.Formatting;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleFormatter {
    private Formatter formatter;

    public ArticleFormatter(FormatExtension kind) {
        formatter = FormatterFactory.getFormatter(kind);
    }

    public String applyFormatting(String source, Collection<Formatting> formattings) {
        MultiValueMap<Long, String> mapIndexElement = new LinkedMultiValueMap<>();
        formattings.forEach(f -> addFormattingInMap(mapIndexElement, f));
        AtomicLong offset = new AtomicLong();
        StringBuffer buffer = new StringBuffer(source);
        mapIndexElement.entrySet().stream()
                .sorted(Comparator.comparingLong(Map.Entry::getKey))
                .forEach(e -> {
                    Long key = e.getKey();
                    List<String> value = e.getValue();
                    value.forEach(element ->
                            {
                                buffer.insert(Long.valueOf(key + offset.get()).intValue(), element);
                                offset.addAndGet(element.length());
                            }
                    );
                });

        return buffer.toString();
    }

    private void addFormattingInMap(MultiValueMap<Long, String> map, Formatting formatting) {
        map.add(formatting.getStartIndex(), formatter.getOpenElement(formatting.getType()));
        map.add(formatting.getEndIndex(), formatter.getCloseElement(formatting.getType()));
    }
}
