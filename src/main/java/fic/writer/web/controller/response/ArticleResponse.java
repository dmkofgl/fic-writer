package fic.writer.web.controller.response;

import fic.writer.domain.entity.Article;
import fic.writer.domain.service.helper.formatter.ArticleFormatter;
import fic.writer.domain.service.helper.formatter.FormatExtension;
import fic.writer.web.controller.ArticleController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleResponse extends ResourceSupport {
    private Long articleId;
    private String title;
    private Date created;
    private String content;
    private String annotation;
    private Long pageCount;

    public ArticleResponse(Article article, FormatExtension formatExtension) {
        articleId = article.getId();
        title = article.getTitle();
        created = article.getCreated();
        annotation = article.getAnnotation();
        pageCount = article.getPageCount();
        formattingContent(article, formatExtension);
        addSelfLink(article.getBook().getId(), articleId);
    }

    private void formattingContent(Article article, FormatExtension formatExtension) {
        ArticleFormatter formatter = new ArticleFormatter(formatExtension);
        String source = article.getContent();

        source = formatter.applyFormatting(source, article.getFormattings());

        content = source;
    }

    private void addSelfLink(Long bookId, Long articleId) {
        add(linkTo(methodOn(ArticleController.class, bookId, articleId).getOneArticle(bookId, articleId)).withSelfRel());
    }
}
