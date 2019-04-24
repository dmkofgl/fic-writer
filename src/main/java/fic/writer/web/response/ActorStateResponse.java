package fic.writer.web.response;

import fic.writer.domain.entity.ActorState;
import org.springframework.hateoas.ResourceSupport;

public class ActorStateResponse extends ResourceSupport {
    private ActorState actorState;

    public ActorStateResponse(ActorState actorState) {
        this.actorState = actorState;
    }
}
