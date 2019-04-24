package fic.writer.web.response;

import fic.writer.domain.entity.User;
import fic.writer.web.controller.UserController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponse extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String about;
    private String information;
    private Set<BookResponse> booksAsSubAuthor;
    private Set<BookResponse> booksAsAuthor;

    public UserResponse(User user) {
        this.userId = user.getId();
        username = user.getUsername();
        about = user.getAbout();
        information = user.getInformation();
        booksAsSubAuthor = user.getBooksAsSubAuthor().stream().map(BookResponse::new).collect(Collectors.toSet());
        booksAsAuthor = user.getBooksAsAuthor().stream().map(BookResponse::new).collect(Collectors.toSet());
        addSelfLink(userId);
    }

    private void addSelfLink(Long id) {
        add(linkTo(methodOn(UserController.class, id).getUserById(id)).withSelfRel());
    }
}
