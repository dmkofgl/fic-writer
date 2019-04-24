package fic.writer.web.response;

import fic.writer.domain.entity.Actor;
import fic.writer.web.controller.ActorController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ActorResponse extends ResourceSupport {
    private Long actorId;
    private String name;
    private String description;
    private Set<BookResponse> books;
    private Set<ActorStateResponse> actorStates;

    public ActorResponse(Actor actor) {
        actorId = actor.getId();
        name = actor.getName();
        description = actor.getDescription();
        books = actor.getBooks().stream().map(BookResponse::new).collect(Collectors.toSet());
        actorStates = actor.getActorStates().stream().map(ActorStateResponse::new).collect(Collectors.toSet());
        addSelfLink(actorId);
    }


    private void addSelfLink(Long id) {
        add(linkTo(methodOn(ActorController.class, id).getActorById(id)).withSelfRel());
    }
}
