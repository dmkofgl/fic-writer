package fic.writer.domain.entity.dto;

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
}
