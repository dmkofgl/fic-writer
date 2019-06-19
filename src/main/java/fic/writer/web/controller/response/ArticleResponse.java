package fic.writer.web.controller.response;

import fic.writer.domain.entity.Article;
import fic.writer.web.controller.BookController;
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

    public ArticleResponse(Article article) {
        articleId = article.getId();
        title = article.getTitle();
        created = article.getCreated();
        content = article.getContent();
        annotation = article.getAnnotation();
        pageCount = article.getPageCount();
        addSelfLink(articleId);
    }

    private void addSelfLink(Long id) {
        add(linkTo(methodOn(BookController.class, id).getBookById(id)).withSelfRel());
    }
}
