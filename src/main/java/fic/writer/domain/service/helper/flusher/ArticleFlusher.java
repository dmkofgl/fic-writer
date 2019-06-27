package fic.writer.domain.service.helper.flusher;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.dto.ArticleDto;

public class ArticleFlusher {
    public static void flushArticleDtoToArticle(Article article, ArticleDto articleDto) {
        if (articleDto.getTitle() != null) {
            article.setTitle(articleDto.getTitle());
        }
        if (articleDto.getAnnotation() != null) {
            article.setAnnotation(articleDto.getAnnotation());
        }
        if (articleDto.getContent() != null) {
            article.setContent(articleDto.getContent());
        }
        if (articleDto.getFormattings() != null) {
            article.setFormattings(articleDto.getFormattings());
        }
        if (articleDto.getSequenceNumber() != null) {
            article.setSequenceNumber(articleDto.getSequenceNumber());
        }
    }
}
