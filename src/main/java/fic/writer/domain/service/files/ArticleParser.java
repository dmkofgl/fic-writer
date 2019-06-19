package fic.writer.domain.service.files;

import fic.writer.domain.entity.Article;

public interface ArticleParser {
    String getTitle();

    String getAnnotation();

    String getContent();

    default Article parse() {
        return Article.builder()
                .title(this.getTitle())
                .annotation(this.getAnnotation())
                .content(this.getContent())
                .build();
    }
}
