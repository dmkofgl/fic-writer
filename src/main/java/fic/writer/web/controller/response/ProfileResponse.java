package fic.writer.web.controller.response;

import fic.writer.domain.entity.Profile;
import fic.writer.web.controller.ProfileController;
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
public class ProfileResponse extends ResourceSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String about;
    private String information;
    private String email;
    private String imageUrl;
    private Set<BookResponse> booksAsSubAuthor;
    private Set<BookResponse> booksAsAuthor;


    public ProfileResponse(Profile profile) {
        this.userId = profile.getId();
        username = profile.getUsername();
        about = profile.getAbout();
        information = profile.getInformation();
        booksAsSubAuthor = profile.getBooksAsCoauthor().stream().map(BookResponse::new).collect(Collectors.toSet());
        booksAsAuthor = profile.getBooksAsAuthor().stream().map(BookResponse::new).collect(Collectors.toSet());
        email = profile.getEmail();
        imageUrl = profile.getImageUrl();
        addSelfLink(userId);
    }

    private void addSelfLink(Long id) {
        add(linkTo(methodOn(ProfileController.class, id).getUserById(id)).withSelfRel());
    }
}
