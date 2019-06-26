package fic.writer.web.controller.response;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Genre;
import fic.writer.domain.entity.enums.FileExtension;
import fic.writer.domain.entity.enums.Size;
import fic.writer.domain.entity.enums.State;
import fic.writer.web.controller.ArticleController;
import fic.writer.web.controller.BookController;
import fic.writer.web.controller.FileController;
import fic.writer.web.controller.ProfileController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookResponse extends ResourceSupport {
    private Long bookId;
    private String title;
    private Link author;
    private Set<Link> subAuthors;
    private Set<Link> source;
    private String description;
    private Size size;
    private State state;
    private Set<Genre> genres;
    private Set<Link> actors;
    private Link articles;
    private Long pageCount;

    public BookResponse(Book book) {
        this.bookId = book.getId();
        title = book.getTitle();
        pageCount = book.getPageCount();
        if (book.getAuthor() != null) {
            Long authorId = book.getAuthor().getId();
            author = linkTo(methodOn(ProfileController.class, authorId).getUserById(authorId)).withRel("author");
        }
        subAuthors = book.getCoauthors().stream().map(author ->
                linkTo(methodOn(ProfileController.class, author.getId()).getUserById(author.getId()))
                        .withRel("subauthor")).collect(Collectors.toSet());
        source = book.getSource().stream().map(BookResponse::new).map(ResourceSupport::getId).collect(Collectors.toSet());
        description = book.getDescription();
        size = book.getSize();
        state = book.getState();
        genres = book.getGenres();
        actors = book.getActors().stream().map(ActorResponse::new).map(ResourceSupport::getId).collect(Collectors.toSet());
        articles = linkTo(methodOn(ArticleController.class, bookId).getAllArticles(bookId)).withRel("articles");
        addSelfLink(bookId);

        addDownloadLink(bookId, FileExtension.TXT);
        addDownloadLink(bookId, FileExtension.FB2);


    }

    private void addSelfLink(Long id) {
        add(linkTo(methodOn(BookController.class, id).getBookById(id)).withSelfRel());
    }

    private void addDownloadLink(Long id, FileExtension extension) {
        try {
            add(linkTo(methodOn(FileController.class, id).getBookAsByteArray(id, extension))
                    .withRel("download_" + extension.toString().toLowerCase()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
