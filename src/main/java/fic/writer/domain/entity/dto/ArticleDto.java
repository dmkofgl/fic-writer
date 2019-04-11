package fic.writer.domain.entity.dto;

import fic.writer.domain.entity.Article;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private String title;
    private String content;
    private String annotation;

    public static ArticleDto of(Article article) {
        return builder()
                .title(article.getTitle())
                .content(article.getContent())
                .annotation(article.getAnnotation())
                .build();
    }
}
