package fic.writer.domain.service;

import fic.writer.domain.entity.ActorState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActorStateService extends CrudService<ActorState, UUID> {

    Page<ActorState> findAllByActor(UUID uuid, Pageable pageable);

    Optional<ActorState> findForActorByArticle(UUID actorId, UUID articleId);
}
