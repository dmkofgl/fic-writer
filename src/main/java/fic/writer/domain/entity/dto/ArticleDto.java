package fic.writer.domain.entity.dto;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Formatting;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String annotation;
    private Set<Formatting> formattings;
    private Long sequenceNumber;


    public static ArticleDto of(Article article) {
        return builder()
                .title(article.getTitle())
                .content(article.getContent())
                .annotation(article.getAnnotation())
                .formattings(article.getFormattings())
                .sequenceNumber(article.getSequenceNumber())
                .build();
    }
}
